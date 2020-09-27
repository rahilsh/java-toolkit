package in.rsh.jutil.pdf;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HTMLToPDF {
  public static void main(String[] args) {
    String inputFile = "/Users/rahil.r/Documents/statement/html/26375.html";
    String outputFile = "/Users/rahil.r/Documents/statement/pdf/test3.pdf";

    generatePDF(inputFile, outputFile);

    System.out.println("Done!");
  }

  public static void generatePDF(String inputHtmlPath, String outputPdfPath) {
    try {
      String url = new File(inputHtmlPath).toURI().toURL().toString();
      System.out.println("URL: " + url);

      OutputStream out = new FileOutputStream(outputPdfPath);

      // Flying Saucer part
      ITextRenderer renderer = new ITextRenderer();

      renderer.setDocument(url);
      renderer.layout();
      renderer.createPDF(out);

      out.close();
    } catch (com.lowagie.text.DocumentException | IOException e) {

      e.printStackTrace();
    }
  }
}
