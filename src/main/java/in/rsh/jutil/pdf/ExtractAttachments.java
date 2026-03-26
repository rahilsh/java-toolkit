package in.rsh.jutil.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExtractAttachments {

  public static void extractAttachments(String src, String dir) throws IOException {
    PDDocument document = PDDocument.load(new File(src));
    PDDocumentNameDictionary names = new PDDocumentNameDictionary(document.getDocumentCatalog());
    PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();

    if (embeddedFiles == null) {
      System.out.println("PDF DOESN'T HAVE ATTACHMENTS");
      document.close();
      return;
    }

    Map<String, PDComplexFileSpecification> files = embeddedFiles.getNames();
    if (files == null) {
      System.out.println("PDF DOESN'T HAVE ATTACHMENTS");
      document.close();
      return;
    }

    new File(dir).mkdirs();
    System.out.println("ATTACHMENTS: " + files.size());

    for (Map.Entry<String, PDComplexFileSpecification> entry : files.entrySet()) {
      PDEmbeddedFile embeddedFile = entry.getValue().getEmbeddedFile();
      if (embeddedFile != null) {
        File outFile = new File(dir, entry.getKey());
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(outFile)) {
          fos.write(embeddedFile.toByteArray());
        }
      }
    }
    document.close();
  }

  public static void main(String[] args) throws IOException {
    String src = "/Users/rahil.r/Downloads/Test.pdf";
    String dir = "/Users/rahil.r/Downloads/Attachments/";
    extractAttachments(src, dir);
  }
}
