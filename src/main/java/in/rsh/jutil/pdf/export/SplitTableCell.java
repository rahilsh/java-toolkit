package in.rsh.jutil.pdf.export;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.File;
import java.io.IOException;

public class SplitTableCell {
  public static final String DEST = "/Users/rahil.r/Documents/splitting_and_rowspan.pdf";

  public static void main(String[] args) throws IOException {
    File file = new File(DEST);
    file.getParentFile().mkdirs();
    new SplitTableCell().createPdf(DEST);
  }

  public void createPdf(String dest) throws IOException {

    PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest + ".pdf"));
    Document document = new Document(pdfDocument);
    Table table = new Table(2);
    Cell cell = new Cell().add(new Paragraph(" 1,1 "));
    table.addCell(cell);
    cell = new Cell().add(new Paragraph(" 1,2 "));
    table.addCell(cell);
    Cell cell23 = new Cell(2, 2).add(new Paragraph("multi 1,3 and 1,4"));
    table.addCell(cell23);
    cell = new Cell().add(new Paragraph(" 2,1 "));
    table.addCell(cell);
    cell = new Cell().add(new Paragraph(" 2,2 "));
    table.addCell(cell);
    document.add(table);

    document.close();
  }
}
