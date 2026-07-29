package com.rsh.jtoolkit.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ExtractAttachmentsTest {

  @Test
  void returnsEmptyListWhenPdfHasNoAttachments(@TempDir Path tempDir) throws IOException {
    Path pdf = tempDir.resolve("plain.pdf");
    try (PDDocument document = new PDDocument()) {
      document.addPage(new PDPage());
      document.save(pdf.toFile());
    }
    Path outDir = tempDir.resolve("out");

    List<String> extracted = ExtractAttachments.extractAttachments(pdf.toString(), outDir.toString());

    assertTrue(extracted.isEmpty());
    assertTrue(Files.exists(outDir) || extracted.isEmpty());
  }

  @Test
  void extractsEmbeddedFiles(@TempDir Path tempDir) throws IOException {
    Path pdf = tempDir.resolve("with-attachment.pdf");
    try (PDDocument document = new PDDocument()) {
      document.addPage(new PDPage());

      PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
      fileSpec.setFile("hello.txt");
      PDEmbeddedFile embeddedFile =
          new PDEmbeddedFile(
              document, new ByteArrayInputStream("hi there".getBytes(StandardCharsets.UTF_8)));
      fileSpec.setEmbeddedFile(embeddedFile);

      Map<String, PDComplexFileSpecification> names = new HashMap<>();
      names.put("hello.txt", fileSpec);
      PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();
      efTree.setNames(names);

      PDDocumentNameDictionary nameDict = new PDDocumentNameDictionary(document.getDocumentCatalog());
      nameDict.setEmbeddedFiles(efTree);
      document.getDocumentCatalog().setNames(nameDict);

      document.save(pdf.toFile());
    }

    Path outDir = tempDir.resolve("extracted");
    List<String> extracted = ExtractAttachments.extractAttachments(pdf.toString(), outDir.toString());

    assertEquals(List.of("hello.txt"), extracted);
    assertEquals("hi there", Files.readString(outDir.resolve("hello.txt")));
  }
}
