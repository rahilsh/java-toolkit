package com.rsh.jtoolkit.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

/** Extracts files embedded (attached) inside a PDF document. */
public final class ExtractAttachments {

  private ExtractAttachments() {}

  /**
   * Extracts every embedded file from the PDF at {@code src} into directory {@code dir}.
   *
   * @param src path to the source PDF
   * @param dir destination directory (created if it does not exist)
   * @return the names of the files that were written (empty if the PDF has no attachments)
   * @throws IOException if the PDF cannot be read or an attachment cannot be written
   */
  public static List<String> extractAttachments(String src, String dir) throws IOException {
    try (PDDocument document = Loader.loadPDF(new File(src))) {
      PDDocumentNameDictionary names = new PDDocumentNameDictionary(document.getDocumentCatalog());
      PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
      if (embeddedFiles == null) {
        return Collections.emptyList();
      }

      Map<String, PDComplexFileSpecification> files = embeddedFiles.getNames();
      if (files == null) {
        return Collections.emptyList();
      }

      File destination = new File(dir);
      if (!destination.exists() && !destination.mkdirs()) {
        throw new IOException("Could not create destination directory: " + dir);
      }

      List<String> extracted = new ArrayList<>();
      for (Map.Entry<String, PDComplexFileSpecification> entry : files.entrySet()) {
        PDEmbeddedFile embeddedFile = entry.getValue().getEmbeddedFile();
        if (embeddedFile != null) {
          File outFile = new File(destination, entry.getKey());
          try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(embeddedFile.toByteArray());
          }
          extracted.add(entry.getKey());
        }
      }
      return extracted;
    }
  }
}
