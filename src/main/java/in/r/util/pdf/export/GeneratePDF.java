package in.r.util.pdf.export;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.FileSystemUtils;

public class GeneratePDF {
  public static void main(String[] args) {
    List<String> userFolders = new ArrayList<>();
    userFolders.add(
        "/tmp/corpben/export_docs/4/TestCorp_1_3171/New Company_9526/Petro Card_05319a16-1a08-44eb-a410-c0468879f4d7/mohammedsh@gmail.com");
    userFolders.add(
        "/tmp/corpben/export_docs/4/TestCorp_1_3171/New Company_9526/Petro Card_05319a16-1a08-44eb-a410-c0468879f4d7/priyar@gmail.com");
    userFolders.add(
        "/tmp/corpben/export_docs/4/TestCorp_1_3171/New Company_9526/Petro Card_05319a16-1a08-44eb-a410-c0468879f4d7/rahilr@gmail.com");

    userFolders.forEach(
        userFolder -> {
          System.out.println("processingUserFolder: " + userFolder);
          try {
            PdfDocument userPdf = new PdfDocument(new PdfWriter(userFolder + "/" + "claims.pdf"));
            Document doc = new Document(userPdf);
            Table table = new Table(1);
            table.setWidth(500);
            Cell userInfo = new Cell();
            Paragraph p =
                new Paragraph(
                    "This picture was taken at Java One. \\nIt shows the iText crew at Java On");
            userInfo.add(p);
            table.addCell(userInfo);
            Files.walk(Paths.get(userFolder), 1)
                .filter(Files::isDirectory)
                .filter(claimFolderPath -> !claimFolderPath.endsWith(userFolder))
                .forEach(
                    claimFolderPath -> {
                      System.out.println("processingclaimFolderPath: " + claimFolderPath);
                      table.addCell("updloaded at: " + new Date());
                      try {
                        Files.walk(claimFolderPath, 1)
                            .filter(Files::isRegularFile)
                            .filter(imageOrPdfPath -> !imageOrPdfPath.toString().endsWith(".json"))
                            .forEach(
                                imageOrPdfPath -> {
                                  System.out.println("processingimageOrPdfPath: " + imageOrPdfPath);
                                  if (imageOrPdfPath.endsWith(".pdf")) {
                                    System.out.println("isPDF");
                                    try {
                                      PdfDocument origPdf =
                                          new PdfDocument(new PdfReader(imageOrPdfPath.toString()));
                                      for (int i = 1; i <= origPdf.getNumberOfPages(); i++) {
                                        PdfPage origPage = origPdf.getPage(i);
                                        PdfDocument tempPdf =
                                            new PdfDocument(
                                                new PdfWriter(claimFolderPath + "/" + "temp.pdf"));
                                        PdfFormXObject pageCopy =
                                            origPage.copyAsFormXObject(tempPdf);
                                        Image image = new Image(pageCopy);
                                        table.addCell(getImageCell(image));
                                        tempPdf.close();
                                      }
                                    } catch (IOException e) {
                                      e.printStackTrace();
                                    }
                                  } else {
                                    Image image = null;
                                    try {
                                      image =
                                          new Image(
                                              ImageDataFactory.create(imageOrPdfPath.toString()));
                                    } catch (MalformedURLException e) {
                                      e.printStackTrace();
                                    }
                                    table.addCell(getImageCell(image));
                                  }
                                });

                      } catch (IOException e) {
                        e.printStackTrace();
                      }
                      if (!FileSystemUtils.deleteRecursively(claimFolderPath.toFile())) {
                        System.out.println("Not deleted");
                      }
                    });
            doc.add(table);
            doc.close();
            userPdf.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  private static Cell getImageCell(Image image) {
    return new Cell()
        .add(image.setHeight(400).setWidth(250).setHorizontalAlignment(HorizontalAlignment.CENTER))
        .setPadding(10)
        .setBorder(null);
  }
}
