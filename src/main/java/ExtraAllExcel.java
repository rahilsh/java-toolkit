import org.apache.poi.ddf.EscherComplexProperty;
import org.apache.poi.ddf.EscherOptRecord;
import org.apache.poi.ddf.EscherProperty;
import org.apache.poi.hpsf.ClassID;
import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExtraAllExcel {
  public static void main(String[] args) throws IOException {
    String path = "/Users/rahil.r/Downloads/Inputxlsinxls.xls";
    Workbook workbook;
    if (path.contains(".xlsx")) {
      workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
    } else {
      workbook = new HSSFWorkbook(new FileInputStream(new File(path)));
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
              new FileOutputStream("/Users/rahil.r/Downloads/xlsxinXlsx" + extension);
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
            FileOutputStream fos =
                new FileOutputStream(System.getProperty("user.home") + "/Documents/" + ed.filename);
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
            FileOutputStream fos = new FileOutputStream("/Users/rahil.r/Downloads/xlsxinxls.xlsx");
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

  static class EmbeddedData {
    String filename;
    InputStream is;
    String source;
  }

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

  public static final EmbeddedReader.OleType OLE10_PACKAGE =
      new EmbeddedReader.OleType("{0003000C-0000-0000-C000-000000000046}");
  public static final OleType EXCEL97 = new OleType("{00020820-0000-0000-C000-000000000046}");
  public static final OleType EXCEL95 = new OleType("{00020810-0000-0000-C000-000000000046}");

  static class ExcelExtractor extends EmbeddedExtractor {
    public boolean canExtract(DirectoryNode dn) {
      ClassID clsId = dn.getStorageClsid();
      return (EXCEL95.equals(clsId) || EXCEL97.equals(clsId) || dn.hasEntry("Workbook") /* ... */);
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      return extractFS(dn, dn.getName() + ".xls");
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

  static class FsExtractor extends EmbeddedExtractor {

    public boolean canExtract(DirectoryNode dn) {
      return true;
    }

    public EmbeddedData extract(DirectoryNode dn) throws IOException {
      return extractFS(dn, dn.getName() + ".dat");
    }
  }

  static class ExcelReader implements Closeable {
    EmbeddedExtractor extractors[] = {
      new Ole10Extractor(), new ExcelExtractor(), new FsExtractor()
    };
    List<EmbeddedData> embeddings = new ArrayList<EmbeddedData>();
    Workbook wb;

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
              ((EscherComplexProperty) ep).getComplexData(), Charset.forName("UTF-16LE"));
        }
      }
      return null;
    }

    private String fileNameWithoutPath(String filename) {
      int last_index = filename.lastIndexOf("\\");
      return filename.substring(last_index + 1);
    }

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

    @Override
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
}
