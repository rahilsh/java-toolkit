package in.excel;

import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

public class FromXls {
  public static void main(String[] args) throws Exception {
    String path = "/Users/rahil.r/Downloads/file2.xls";
    File file = new File(path);

    NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
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
        FileOutputStream fos = new FileOutputStream("/Users/rahil.r/Downloads/file2.xls");
        IOUtils.copy(is, fos);
        fos.close();
      }
    }
  }
}
