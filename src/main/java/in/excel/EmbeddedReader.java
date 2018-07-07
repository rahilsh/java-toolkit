package in.excel;

import org.apache.poi.ddf.EscherComplexProperty;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperty;
import org.apache.poi.hpsf.ClassID;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPicture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmbeddedReader {

  public static final OleType OLE10_PACKAGE = new OleType("{0003000C-0000-0000-C000-000000000046}");
  public static final OleType PPT_SHOW = new OleType("{64818D10-4F9B-11CF-86EA-00AA00B929E8}");
  public static final OleType XLS_WORKBOOK = new OleType("{00020841-0000-0000-C000-000000000046}");
  public static final OleType TXT_ONLY = new OleType("{5e941d80-bf96-11cd-b579-08002b30bfeb}");
  public static final OleType EXCEL97 = new OleType("{00020820-0000-0000-C000-000000000046}");
  public static final OleType EXCEL95 = new OleType("{00020810-0000-0000-C000-000000000046}");
  public static final OleType WORD97 = new OleType("{00020906-0000-0000-C000-000000000046}");
  public static final OleType WORD95 = new OleType("{00020900-0000-0000-C000-000000000046}");
  public static final OleType POWERPOINT97 = new OleType("{64818D10-4F9B-11CF-86EA-00AA00B929E8}");
  public static final OleType POWERPOINT95 = new OleType("{EA7BAE70-FB3B-11CD-A903-00AA00510EA3}");
  public static final OleType EQUATION30 = new OleType("{0002CE02-0000-0000-C000-000000000046}");
  public static final OleType PdfClassID = new OleType("{B801CA65-A1FC-11D0-85AD-444553540000}");

  private File excel_file;
  private ImageReader image_reader;

  static class OleType {
    final String classId;

    OleType(String classId) {
      this.classId = classId;
    }

    ClassID getClassID() {
      ClassID cls = new ClassID();
      byte clsBytes[] = cls.getBytes();
      String clsStr = classId.replaceAll("[{}-]", "");
      for (int i = 0; i < clsStr.length(); i += 2) {
        clsBytes[i / 2] = (byte) Integer.parseInt(clsStr.substring(i, i + 2), 16);
      }
      return cls;
    }
  }

  public static void main(String[] args) throws Exception {
    File sample = new File("/Users/rahil.r/Downloads/file2.xls");
    ImageReader ir = new ImageReader(sample);
    for (EmbeddedData ed : ir.embeddings) {
      FileOutputStream fos =
          new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + ed.filename);
      IOUtils.copy(ed.is, fos);
      fos.close();
    }
    ir.close();
  }

  static byte[] getSamplePng() throws IOException {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL imgUrl = cl.getResource("javax/swing/plaf/metal/icons/ocean/directory.gif");
    BufferedImage img = ImageIO.read(imgUrl);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(img, "PNG", bos);
    return bos.toByteArray();
  }

  public EmbeddedReader(String excel_path) throws IOException {
    excel_file = new File(excel_path);
    image_reader = new ImageReader(excel_file);
  }

  public String[] get_file_names() {
    ArrayList<String> file_names = new ArrayList<String>();
    for (EmbeddedData ed : image_reader.embeddings) {
      file_names.add(ed.filename);
    }
    return file_names.toArray(new String[file_names.size()]);
  }

  public InputStream get_stream(String file_name) {
    InputStream input_stream = null;
    for (EmbeddedData ed : image_reader.embeddings) {
      if (file_name.equals(ed.filename)) {
        input_stream = ed.is;
        break;
      }
    }
    return input_stream;
  }

  static class ImageReader implements Closeable {
    EmbeddedExtractor extractors[] = {
      new Ole10Extractor(),
      new PdfExtractor(),
      new WordExtractor(),
      new ExcelExtractor(),
      new FsExtractor()
    };

    List<EmbeddedData> embeddings = new ArrayList<EmbeddedData>();
    Workbook wb;

    public ImageReader(File excelfile) throws IOException {
      try {
        wb = WorkbookFactory.create(excelfile);
        Sheet receiptImages = wb.getSheet("Receipt images");
        if (wb instanceof XSSFWorkbook) {
          addSheetPicsAndEmbedds((XSSFSheet) receiptImages);
        } else {
          addAllEmbedds((HSSFWorkbook) wb);
          addSheetPics((HSSFSheet) receiptImages);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    protected void addSheetPicsAndEmbedds(XSSFSheet sheet) throws IOException {
      if (sheet == null) return;
      XSSFDrawing draw = sheet.createDrawingPatriarch();
      for (XSSFShape shape : draw.getShapes()) {
        if (!(shape instanceof XSSFPicture)) continue;
        XSSFPicture picture = (XSSFPicture) shape;
        XSSFPictureData pd = picture.getPictureData();
        PackagePart pp = pd.getPackagePart();
        CTPicture ctPic = picture.getCTPicture();
        String filename = null;
        try {
          filename = ctPic.getNvPicPr().getCNvPr().getName();
        } catch (Exception e) {
        }
        if (filename == null || "".equals(filename)) {
          filename = new File(pp.getPartName().toString()).getName();
        }
        EmbeddedData ed = new EmbeddedData();
        ed.filename = fileNameWithoutPath(filename);
        ed.is = pp.getInputStream();
        embeddings.add(ed);
      }
    }

    protected void addAllEmbedds(HSSFWorkbook hwb) throws IOException {
      for (HSSFObjectData od : hwb.getAllEmbeddedObjects()) {
        String alternativeName = getAlternativeName(od);
        if (od.hasDirectoryEntry()) {
          DirectoryNode src = (DirectoryNode) od.getDirectory();
          for (EmbeddedExtractor ee : extractors) {
            if (ee.canExtract(src)) {
              EmbeddedData ed = ee.extract(src);

              if (ed.filename == null || ed.filename.startsWith("MBD") || alternativeName != null) {
                if (alternativeName != null) {
                  ed.filename = alternativeName;
                }
              }
              ed.filename = fileNameWithoutPath(ed.filename);
              ed.source = "object";
              embeddings.add(ed);
              break;
            }
          }
        }
      }
    }

    protected String getAlternativeName(HSSFShape shape) {
      EscherOptRecord eor = reflectEscherOptRecord(shape);
      if (eor == null) {
        return null;
      }
      for (EscherProperty ep : eor.getEscherProperties()) {
        if ("groupshape.shapename".equals(ep.getName()) && ep.isComplex()) {
          return new String(
              ((EscherComplexProperty) ep).getComplexData(), Charset.forName("UTF-16LE"));
        }
      }
      return null;
    }

    protected void addSheetPics(HSSFSheet sheet) {
      if (sheet == null) return;
      int picIdx = 0;
      int emfIdx = 0;
      HSSFPatriarch patriarch = sheet.getDrawingPatriarch();
      if (patriarch == null) return;
      // Loop through the objects
      for (HSSFShape shape : patriarch.getChildren()) {
        if (!(shape instanceof HSSFPicture)) {
          continue;
        }
        HSSFPicture picture = (HSSFPicture) shape;
        if (picture.getShapeType() != HSSFSimpleShape.OBJECT_TYPE_PICTURE) continue;
        HSSFPictureData pd = picture.getPictureData();
        byte pictureBytes[] = pd.getData();
        int pictureBytesOffset = 0;
        int pictureBytesLen = pictureBytes.length;
        String filename = picture.getFileName();
        // try to find an alternative name
        if (filename == null || "".equals(filename)) {
          filename = getAlternativeName(picture);
        }
        // default to dummy name
        if (filename == null || "".equals(filename)) {
          filename = "picture" + (picIdx++);
        }
        filename = filename.trim();

        // check for emf+ embedded pdf (poor mans style :( )
        // Mac Excel 2011 embeds pdf files with this method.
        boolean validFile = true;
        if (pd.getFormat() == Workbook.PICTURE_TYPE_EMF) {
          validFile = false;
          int idxStart = indexOf(pictureBytes, 0, "%PDF-".getBytes());
          if (idxStart != -1) {
            int idxEnd = indexOf(pictureBytes, idxStart, "%%EOF".getBytes());
            if (idxEnd != -1) {
              pictureBytesOffset = idxStart;
              pictureBytesLen = idxEnd - idxStart + 6;
              validFile = true;
            }
          } else {
            // This shape was not a Mac Excel 2011 embedded pdf  file.
            // So this is a shape related to a regular embedded object
            // Lets update the object filename with the shapes filename
            // if the object filename is of format ARGF1234.pdf
            EmbeddedData ed_obj = embeddings.get(emfIdx);
            Pattern pattern = Pattern.compile("^[A-Z0-9]{8}\\.[pdfPDF]{3}$");
            Matcher matcher = pattern.matcher(ed_obj.filename);
            if (matcher.matches()) {
              ed_obj.filename = filename;
            }
            emfIdx += 1;
          }
        }

        EmbeddedData ed = new EmbeddedData();
        ed.filename = fileNameWithoutPath(filename);
        ed.is = new ByteArrayInputStream(pictureBytes, pictureBytesOffset, pictureBytesLen);
        if (fileNotInEmbeddings(ed.filename) && validFile) {
          embeddings.add(ed);
        }
      }
    }

    private static EscherOptRecord reflectEscherOptRecord(HSSFShape shape) {
      try {
        Method m = HSSFShape.class.getDeclaredMethod("getOptRecord");
        m.setAccessible(true);
        return (EscherOptRecord) m.invoke(shape);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    private String fileNameWithoutPath(String filename) {
      int last_index = filename.lastIndexOf("\\");
      return filename.substring(last_index + 1);
    }

    private boolean fileNotInEmbeddings(String filename) {
      boolean exists = true;
      for (EmbeddedData ed : embeddings) {
        if (ed.filename.equals(filename)) {
          exists = false;
        }
      }
      return exists;
    }

    public void close() throws IOException {
      Iterator<EmbeddedData> ed = embeddings.iterator();
      while (ed.hasNext()) {
        ed.next().is.close();
      }
      if (wb != null) {
        wb.close();
      }
    }
  }

  static class EmbeddedData {
    String filename;
    InputStream is;
    String source;
  }

  abstract static class EmbeddedExtractor {
    abstract boolean canExtract(DirectoryNode dn);

    abstract EmbeddedData extract(DirectoryNode dn) throws IOException;

    protected EmbeddedData extractFS(DirectoryNode dn, String filename) throws IOException {
      assert (canExtract(dn));
      POIFSFileSystem dest = new POIFSFileSystem();
      copyNodes(dn, dest.getRoot());
      EmbeddedData ed = new EmbeddedData();
      ed.filename = filename;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      dest.writeFilesystem(bos);
      bos.close();
      ed.is = new ByteArrayInputStream(bos.toByteArray());
      return ed;
    }
  }

  static class Ole10Extractor extends EmbeddedExtractor {
    public boolean canExtract(DirectoryNode dn) {
      ClassID clsId = dn.getStorageClsid();
      return OLE10_PACKAGE.equals(clsId);
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      try {
        Ole10Native ole10 = Ole10Native.createFromEmbeddedOleObject(dn);
        EmbeddedData ed = new EmbeddedData();
        ed.filename = new File(ole10.getFileName()).getName();
        ed.is = new ByteArrayInputStream(ole10.getDataBuffer());
        return ed;
      } catch (Ole10NativeException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
  }

  static class PdfExtractor extends EmbeddedExtractor {
    public boolean canExtract(DirectoryNode dn) {
      ClassID clsId = dn.getStorageClsid();
      return (PdfClassID.equals(clsId) || dn.hasEntry("CONTENTS"));
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      EmbeddedData ed = new EmbeddedData();
      ed.is = dn.createDocumentInputStream("CONTENTS");
      ed.filename = dn.getName() + ".pdf";
      return ed;
    }
  }

  static class WordExtractor extends EmbeddedExtractor {
    public boolean canExtract(DirectoryNode dn) {
      ClassID clsId = dn.getStorageClsid();
      return (WORD95.equals(clsId) || WORD97.equals(clsId) || dn.hasEntry("WordDocument"));
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      return extractFS(dn, dn.getName() + ".doc");
    }
  }

  static class ExcelExtractor extends EmbeddedExtractor {
    public boolean canExtract(DirectoryNode dn) {
      ClassID clsId = dn.getStorageClsid();
      return (EXCEL95.equals(clsId) || EXCEL97.equals(clsId) || dn.hasEntry("Workbook") /* ... */);
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      return extractFS(dn, dn.getName() + ".xls");
    }
  }

  static class FsExtractor extends EmbeddedExtractor {

    public boolean canExtract(DirectoryNode dn) {
      return true;
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      return extractFS(dn, dn.getName() + ".dat");
    }
  }

  private static void copyNodes(DirectoryNode src, DirectoryNode dest) throws IOException {
    for (Entry e : src) {
      if (e instanceof DirectoryNode) {
        DirectoryNode srcDir = (DirectoryNode) e;
        DirectoryNode destDir = (DirectoryNode) dest.createDirectory(srcDir.getName());
        destDir.setStorageClsid(srcDir.getStorageClsid());
        copyNodes(srcDir, destDir);
      } else {
        InputStream is = src.createDocumentInputStream(e);
        dest.createDocument(e.getName(), is);
        is.close();
      }
    }
  }

  /**
   * Knuth-Morris-Pratt Algorithm for Pattern Matching Finds the first occurrence of the pattern in
   * the text.
   */
  private static int indexOf(byte[] data, int offset, byte[] pattern) {
    int[] failure = computeFailure(pattern);

    int j = 0;
    if (data.length == 0) return -1;

    for (int i = offset; i < data.length; i++) {
      while (j > 0 && pattern[j] != data[i]) {
        j = failure[j - 1];
      }
      if (pattern[j] == data[i]) {
        j++;
      }
      if (j == pattern.length) {
        return i - pattern.length + 1;
      }
    }
    return -1;
  }

  /**
   * Computes the failure function using a boot-strapping process, where the pattern is matched
   * against itself.
   */
  private static int[] computeFailure(byte[] pattern) {
    int[] failure = new int[pattern.length];

    int j = 0;
    for (int i = 1; i < pattern.length; i++) {
      while (j > 0 && pattern[j] != pattern[i]) {
        j = failure[j - 1];
      }
      if (pattern[j] == pattern[i]) {
        j++;
      }
      failure[i] = j;
    }

    return failure;
  }
}
