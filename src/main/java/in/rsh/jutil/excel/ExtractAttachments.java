package in.rsh.jutil.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ddf.EscherComplexProperty;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperty;
import org.apache.poi.hpsf.ClassID;
import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.poifs.filesystem.Ole10Native;
import org.apache.poi.poifs.filesystem.Ole10NativeException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPicture;

public class ExtractAttachments {

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

  public static void saveAllEmbeddedWorkbooksV3(String source, String destinationFolder)
      throws IOException {
    File sample = new File(source);
    ImageReader ir = new ImageReader(sample);
    for (ExtractAttachments.EmbeddedData ed : ir.embeddings) {
      FileOutputStream fos = new FileOutputStream(destinationFolder + ed.filename);
      IOUtils.copy(ed.is, fos);
      fos.close();
    }
    ir.close();
  }

  public static void saveAllEmbeddedWorkbooksFromXLS(String source, String destinationFolder)
      throws IOException {
    File file = new File(source);

    POIFSFileSystem fs = new POIFSFileSystem(file);
    HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot(), true);

    for (HSSFObjectData obj : wb.getAllEmbeddedObjects()) {
      System.out.println(
          obj.getDirectory()
              + "***************HSSFObject data value************"
              + obj.getOLE2ClassName());
      String oleName = obj.getOLE2ClassName();
      System.out.println("Has directory Entry node" + obj.hasDirectoryEntry());
      DirectoryNode dn = (DirectoryNode) obj.getDirectory();
      Iterator<Entry> ab = dn.getEntries();
      System.out.println(
          "The entries in the directory node are"
              + dn.getEntries()
              + "And also the count"
              + dn.getEntryCount());

      if (oleName.contains("Worksheet")) {
        InputStream is;
        Entry entry = ab.next();
        is = dn.createDocumentInputStream(entry);
        FileOutputStream fos = new FileOutputStream(destinationFolder + entry.getName() + ".xls");
        IOUtils.copy(is, fos);
        fos.close();
      }
    }
  }

  public static void saveAllEmbeddedWorkbooks(String source, String destinationFolder)
      throws IOException, OpenXML4JException {
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(source)));
    for (PackagePart pPart : workbook.getAllEmbedds()) {
      String contentType = pPart.getContentType();
      if (contentType.equals(
          "application/vnd.ms-excel")) { // This is to read xls workbook embedded to xlsx file
        FileOutputStream fos =
            new FileOutputStream(destinationFolder + "/" + pPart.getPartName().getName() + ".xls");
        IOUtils.copy(pPart.getInputStream(), fos);
        fos.close();
      } else if (contentType.equals(
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
        /* This is to read xlsx workbook embedded to xlsx file
        "/xl/embeddings/Microsoft_Excel_Worksheet12.xlsx" - Can read an Excel from a particular sheet
         This is the worksheet from the Parent Excel-sheet-12*/
        FileOutputStream fos =
            new FileOutputStream(destinationFolder + "/" + pPart.getPartName().getName() + ".xlsx");
        IOUtils.copy(pPart.getInputStream(), fos);
        fos.close();
      }
    }
  }

  public static void saveAllEmbeddedWorkbooksV2(String source, String destinationFolder)
      throws IOException {
    Workbook workbook;
    if (source.contains(".xlsx")) {
      workbook = new XSSFWorkbook(new FileInputStream(new File(source)));
    } else {
      workbook = new HSSFWorkbook(new FileInputStream(new File(source)));
    }
    if (workbook instanceof XSSFWorkbook) {
      try {
        for (PackagePart pPart : ((XSSFWorkbook) workbook).getAllEmbedds()) {
          String contentType = pPart.getContentType();
          String extension = ".xlsx";
          if (contentType.contains("application/vnd.ms-excel")) {
            extension = ".xls";
          }
          FileOutputStream fos =
              new FileOutputStream(
                  destinationFolder + "/" + pPart.getPartName().getName() + extension);
          IOUtils.copy(pPart.getInputStream(), fos);
          fos.close();
        }
      } catch (OpenXML4JException e) {
        e.printStackTrace();
      }
    } else {
      for (HSSFObjectData obj : ((HSSFWorkbook) workbook).getAllEmbeddedObjects()) {
        DirectoryNode dn = (DirectoryNode) obj.getDirectory();
        ClassID clsId = dn.getStorageClsid();
        if ((EXCEL95.equals(clsId) || EXCEL97.equals(clsId) || dn.hasEntry("Workbook"))) {
          ExcelReader ir = new ExcelReader(obj);
          for (EmbeddedData ed : ir.embeddings) {
            FileOutputStream fos = new FileOutputStream(destinationFolder + "/" + ed.filename);
            IOUtils.copy(ed.is, fos);
            fos.close();
          }
          ir.close();
        } else {
          String oleName = obj.getOLE2ClassName();
          Iterator<Entry> ab = dn.getEntries();
          System.out.println(
              "The entries in the directory node are"
                  + dn.getEntries()
                  + "And also the count"
                  + dn.getEntryCount());
          if (oleName.contains("Worksheet")) {
            InputStream is;
            Entry entry = ab.next();
            is = dn.createDocumentInputStream(entry);
            FileOutputStream fos =
                new FileOutputStream(destinationFolder + "/" + entry.getName() + ".xlsx");
            IOUtils.copy(is, fos);
            fos.close();
          }
        }
      }
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

  static class ImageReader implements Closeable {
    EmbeddedExtractor[] extractors = {
      new Ole10Extractor(),
      new PdfExtractor(),
      new WordExtractor(),
      new ExcelExtractor(),
      new FsExtractor()
    };

    List<EmbeddedData> embeddings = new ArrayList<>();
    Workbook wb;

    public ImageReader(File excelfile) {
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
              ((EscherComplexProperty) ep).getComplexData(), StandardCharsets.UTF_16LE);
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
        byte[] pictureBytes = pd.getData();
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

  abstract static class EmbeddedExtractor {
    abstract boolean canExtract(DirectoryNode dn);

    abstract EmbeddedData extract(DirectoryNode dn) throws IOException;

    protected EmbeddedData extractFS(DirectoryNode dn, String filename) throws IOException {
      assert (canExtract(dn));
      EmbeddedData ed;
      ByteArrayOutputStream bos;
      try (POIFSFileSystem dest = new POIFSFileSystem()) {
        copyNodes(dn, dest.getRoot());
        ed = new EmbeddedData();
        ed.filename = filename;
        bos = new ByteArrayOutputStream();
        dest.writeFilesystem(bos);
      }
      bos.close();
      ed.is = new ByteArrayInputStream(bos.toByteArray());
      return ed;
    }
  }

  static class OleType {
    final String classId;

    OleType(String classId) {
      this.classId = classId;
    }
  }

  static class Ole10Extractor extends EmbeddedExtractor {
    public boolean canExtract(DirectoryNode dn) {
      ClassID clsId = dn.getStorageClsid();
      return Objects.equals(OLE10_PACKAGE.classId, clsId.toString());
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

  static class ExcelReader implements Closeable {
    EmbeddedExtractor[] extractors = {
      new Ole10Extractor(), new ExcelExtractor(), new FsExtractor()
    };
    List<EmbeddedData> embeddings = new ArrayList<>();
    Workbook wb;

    public ExcelReader(HSSFObjectData obj) throws IOException {
      String alternativeName = getAlternativeName(obj);
      if (obj.hasDirectoryEntry()) {
        DirectoryNode src = (DirectoryNode) obj.getDirectory();
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

    protected String getAlternativeName(HSSFShape shape) {
      EscherOptRecord eor = reflectEscherOptRecord(shape);
      if (eor == null) {
        return null;
      }
      for (EscherProperty ep : eor.getEscherProperties()) {
        if ("groupshape.shapename".equals(ep.getName()) && ep.isComplex()) {
          return new String(
              ((EscherComplexProperty) ep).getComplexData(), StandardCharsets.UTF_16LE);
        }
      }
      return null;
    }

    private String fileNameWithoutPath(String filename) {
      int last_index = filename.lastIndexOf("\\");
      return filename.substring(last_index + 1);
    }

    @Override
    public void close() throws IOException {
      for (EmbeddedData embedding : embeddings) {
        embedding.is.close();
      }
      if (wb != null) {
        wb.close();
      }
    }
  }
}
