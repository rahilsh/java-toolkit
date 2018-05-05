import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExtractExcel {
  public static void main(String[] args) throws IOException, OpenXML4JException {
    String path = "/Users/rahil.r/Downloads/file2.xls";
    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));

    for (PackagePart pPart : workbook.getAllEmbedds()) {
      String contentType = pPart.getContentType();

      if (contentType.equals(
          "application/vnd.ms-excel")) { // This is to read xls workbook embedded to xlsx file
        HSSFWorkbook embeddedWorkbook = new HSSFWorkbook(pPart.getInputStream());
        int countOfSheetXls = embeddedWorkbook.getNumberOfSheets();
        FileOutputStream fos =
                new FileOutputStream("/Users/rahil.r/Downloads/EmbeddedWorkbookxls.xls");
        IOUtils.copy(pPart.getInputStream(), fos);
        fos.close();

      } else if (contentType.equals(
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) { // This is to read
        // xlsx workbook
        // embedded to
        // xlsx file
        // "/xl/embeddings/Microsoft_Excel_Worksheet12.xlsx" - Can read an Excel from a particular
        // sheet
        // This is the worksheet from the Parent Excel-sheet-12

        XSSFWorkbook embeddedWorkbook = new XSSFWorkbook();
        //        int countOfSheetXlsx = embeddedWorkbook.getNumberOfSheets();
        //        ArrayList<String> sheetNames = new ArrayList<String>();
        //        for (int i = 0; i < countOfSheetXlsx; i++) {
        //          String name = workbook.getSheetName(i);
        //          sheetNames.add(name);
        //        }

        FileOutputStream fos =
            new FileOutputStream("/Users/rahil.r/Downloads/EmbeddedWorkbook.xlsx");
        IOUtils.copy(pPart.getInputStream(), fos);
        fos.close();
      }
    }
  }
}
