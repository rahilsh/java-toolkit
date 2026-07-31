package com.rsh.jtoolkit.pdf;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HTMLToPDFTest {

  private static final String XHTML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          + "<html><body><h1>Title</h1><p>Hello PDF</p></body></html>";

  @Test
  void generatesPdfFromXhtml(@TempDir Path tempDir) throws IOException {
    Path html = tempDir.resolve("in.html");
    Files.writeString(html, XHTML);
    Path pdf = tempDir.resolve("out.pdf");

    HTMLToPDF.generatePDF(html.toString(), pdf.toString());

    assertTrue(Files.exists(pdf));
    byte[] bytes = Files.readAllBytes(pdf);
    assertTrue(bytes.length > 0);
    assertTrue(new String(bytes, 0, 5, StandardCharsets.US_ASCII).startsWith("%PDF-"));
  }

  @Test
  void wrapsIoFailureAsUnchecked(@TempDir Path tempDir) throws IOException {
    Path html = tempDir.resolve("in.html");
    Files.writeString(html, XHTML);
    // Output directory does not exist -> the output stream cannot be opened.
    Path pdf = tempDir.resolve("missing-dir").resolve("out.pdf");

    assertThrows(
        UncheckedIOException.class, () -> HTMLToPDF.generatePDF(html.toString(), pdf.toString()));
  }
}
