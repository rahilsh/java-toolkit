package com.rsh.jtoolkit.pdf;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.xhtmlrenderer.pdf.ITextRenderer;

/** Renders an XHTML document to a PDF file using Flying Saucer. */
public final class HTMLToPDF {

  private HTMLToPDF() {}

  /**
   * Renders the (X)HTML file at {@code inputHtmlPath} to a PDF written to {@code outputPdfPath}.
   *
   * @throws UncheckedIOException if reading the HTML or writing the PDF fails
   * @throws IllegalStateException if the document cannot be rendered
   */
  public static void generatePDF(String inputHtmlPath, String outputPdfPath) {
    try {
      String url = new File(inputHtmlPath).toURI().toURL().toString();
      ITextRenderer renderer = ITextRenderer.fromUrl(url);
      try (OutputStream out = Files.newOutputStream(Paths.get(outputPdfPath))) {
        renderer.layout();
        renderer.createPDF(out);
      }
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to generate PDF from " + inputHtmlPath, e);
    } catch (org.openpdf.text.DocumentException e) {
      throw new IllegalStateException("Failed to render PDF from " + inputHtmlPath, e);
    }
  }
}
