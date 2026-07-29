package com.rsh.jtoolkit.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PdfUtilTest {

  @Test
  void plainPdfIsNotPdfA(@TempDir Path tempDir) throws IOException {
    Path pdf = tempDir.resolve("plain.pdf");
    try (PDDocument document = new PDDocument()) {
      document.addPage(new PDPage());
      document.save(pdf.toFile());
    }
    assertFalse(PdfUtil.isPdfA(pdf.toString()));
  }

  @Test
  void plainPdfIsNotPdfAViaStream(@TempDir Path tempDir) throws IOException {
    Path pdf = tempDir.resolve("plain2.pdf");
    try (PDDocument document = new PDDocument()) {
      document.addPage(new PDPage());
      document.save(pdf.toFile());
    }
    try (var in = java.nio.file.Files.newInputStream(pdf)) {
      assertFalse(PdfUtil.isPdfA(in));
    }
  }
}
