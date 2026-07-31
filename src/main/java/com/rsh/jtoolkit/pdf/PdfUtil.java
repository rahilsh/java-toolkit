package com.rsh.jtoolkit.pdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDMetadata;

/** Small PDF inspection helpers built on PDFBox. */
public final class PdfUtil {

  private PdfUtil() {}

  /**
   * Returns {@code true} if the PDF at {@code filePath} declares PDF/A conformance in its XMP
   * metadata.
   */
  public static boolean isPdfA(String filePath) throws IOException {
    try (PDDocument document = PDDocument.load(new File(filePath))) {
      return isPdfA(document);
    }
  }

  /** Returns {@code true} if the PDF read from {@code inputStream} declares PDF/A conformance. */
  public static boolean isPdfA(InputStream inputStream) throws IOException {
    try (PDDocument document = PDDocument.load(inputStream)) {
      return isPdfA(document);
    }
  }

  private static boolean isPdfA(PDDocument document) throws IOException {
    PDMetadata metadata = document.getDocumentCatalog().getMetadata();
    if (metadata == null) {
      return false;
    }
    String xmp = new String(metadata.toByteArray(), StandardCharsets.UTF_8);
    // The PDF/A identification schema uses the "pdfaid" namespace (pdfaid:part /
    // pdfaid:conformance).
    return xmp.contains("pdfaid");
  }
}
