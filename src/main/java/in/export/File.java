package in.export;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class File {
  public static void main(String[] args) {
    String actualPath =
        "/tmp/corpben/export_docs/6/TestCorp_1_3171/New Company_9526/Petro Card_05319a16-1a08-44eb-a410-c0468879f4d7/priyar@zeta.tech";
    try {
      Files.walk(Paths.get(actualPath), 4)
          .filter(Files::isDirectory)
          .filter(path -> !path.endsWith(actualPath))
          .forEach(path -> System.out.println(path));

      List<String> text = new ArrayList<>();
      text.add("         Episode V         ");
      text.add("  THE EMPIRE STRIKES BACK  ");
      text.add("It is a dark time for the");
      text.add("Rebellion. Although the Death");

      PdfDocument pdf = new PdfDocument(new PdfWriter("/Users/rahil.r/Documents/claims.pdf"));
      String[] IMAGES = {
        "/Users/rahil.r/Desktop/screen2.png", "/Users/rahil.r/Desktop/screen1.png"
      };
      Document doc = new Document(pdf);
      //      Image image = new Image(ImageDataFactory.create(IMAGES[0]));
      //      canvas.concatMatrix(1, 0, 0, 1, 0, ps.getHeight());
      //      canvas
      //          .beginText()
      //          .setFontAndSize(PdfFontFactory.createFont(FontConstants.COURIER_BOLD), 14)
      //          .setLeading(14 * 1.2f)
      //          .moveText(70, -40);
      //      for (String s : text) {
      //        // Add text and move to the next line
      //        canvas.newlineShowText(s);
      //      }
      //      canvas.endText();
      //
      //
      //      for (int i = 0; i < IMAGES.length; i++) {
      //        image = new Image(ImageDataFactory.create(IMAGES[i]));
      //        pdf.addNewPage(new PageSize(image.getImageWidth(), image.getImageHeight()));
      //        // Notice that now it is not necessary to set image position,
      //        // because images are not overlapped while adding.
      //        image.setFixedPosition(i + 1, 0, 0);
      //        doc.add(image);
      //      }
      //      Table tableImage = new Table(new float[] {1, 1});
      //      Image img = new Image(ImageDataFactory.create(IMAGES[0]));
      //      Cell cell = new Cell().add(img.setAutoScale(true));
      //      cell.setBorder(null);
      //      tableImage.addCell(cell);

      //      Table tableText = new Table(new float[] {2, 1});
      //      tableText.setWidth(600);
      //        Image img = new Image(ImageDataFactory.create(IMAGES[0]));
      //        Cell cell = new Cell().add(img.setAutoScale(true));
      //        cell.setBorder(null);
      //        tableText.addCell(cell);
      //      Cell cell1 = new Cell();
      //      Paragraph p =
      //          new Paragraph(
      //              "This picture was taken at Java One. \\nIt shows the iText crew at Java One in
      // 2013.");
      //      p.setTextAlignment(TextAlignment.CENTER);
      //      cell1.add(p).setVerticalAlignment(VerticalAlignment.BOTTOM);
      //      cell1.setBorder(Border.NO_BORDER);
      //      tableText.addCell(cell1);
      //      doc.add(p);

      Table table = new Table(1);
      table.setWidth(500);
      Image img1 = new Image(ImageDataFactory.create(IMAGES[1]));
      Cell c = new Cell().add(img1.setAutoScale(true));
      c.setPadding(10);
      c.setBorder(null);
        Image img = new Image(ImageDataFactory.create(IMAGES[0]));
      Cell c2 = new Cell().add(img.setAutoScale(true));
      c2.setPadding(10).setBorder(null);

      table.addCell("A light bulb icon").addCell(c).addCell(c2);

      doc.add(table);

      doc.close();
      // Draw the axes
      pdf.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static class OverlappingImageTableRenderer extends TableRenderer {
    private ImageData image;

    public OverlappingImageTableRenderer(
        Table modelElement, Table.RowRange rowRange, ImageData img) {
      super(modelElement, rowRange);
      this.image = img;
    }

    public OverlappingImageTableRenderer(Table modelElement, ImageData img) {
      super(modelElement);
      this.image = img;
    }

    @Override
    public void drawChildren(DrawContext drawContext) {
      super.drawChildren(drawContext);
      float x =
          Math.max(
              this.getOccupiedAreaBBox().getX()
                  + this.getOccupiedAreaBBox().getWidth() / 3
                  - image.getWidth(),
              0);
      float y =
          Math.max(
              this.getOccupiedAreaBBox().getY()
                  + this.getOccupiedAreaBBox().getHeight() / 3
                  - image.getHeight(),
              0);
      drawContext.getCanvas().addImage(image, x, y, false);
    }

    @Override
    public OverlappingImageTableRenderer getNextRenderer() {
      return new OverlappingImageTableRenderer((Table) modelElement, image);
    }
  }
}
