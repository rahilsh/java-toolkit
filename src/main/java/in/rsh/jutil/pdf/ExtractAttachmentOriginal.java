package in.rsh.jutil.pdf;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExtractAttachmentOriginal {

  /*
  package in.pdf;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExtractAttachmentOriginal {

  public ExtractAttachmentOriginal(String src, String dir) throws IOException {
    File folder = new File(dir);
    folder.mkdirs();
    PdfReader reader = new PdfReader(src);
    PdfDictionary root = reader.getCatalog();
    System.out.println("Is Dictionary: " + root.isDictionary());
    PdfDictionary names = root.getAsDict(PdfName.NAMES);
    PdfDictionary embedded = null;
    try {
      embedded = names.getAsDict(PdfName.EMBEDDEDFILES);
      System.out.println("EmbeddedFiles: " + embedded);
      PdfArray fileSpecs = embedded.getAsArray(PdfName.NAMES);
      if (fileSpecs == null) {
        fileSpecs = embedded.getAsArray(PdfName.KIDS);
        if (fileSpecs != null) {
          fileSpecs = fileSpecs.getAsDict(0).getAsArray(PdfName.NAMES);
        }
      }
      if (fileSpecs != null) {
        System.out.println("ATTACHMENTS: " + getCountOfAttachments(fileSpecs));
        for (int i = 0; i < fileSpecs.size(); ) {
          extractAttachment(reader, folder, fileSpecs.getAsString(i++), fileSpecs.getAsDict(i++));
        }
      } else {
        System.out.println("PDF DOESN'T HAVE ATTACHMENTS");
      }
    } catch (Exception e) {
      System.out.println("Error while extracting pdf: " + e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException {
    String src = "/Users/rahil.r/Downloads/Test.pdf";
    String dir = "/Users/rahil.r/Downloads/Attachments/";
    ExtractAttachmentOriginal e = new ExtractAttachmentOriginal(src, dir);
  }

  protected static void extractAttachment(
      PdfReader reader, File dir, PdfString name, PdfDictionary filespec) throws IOException {
    PRStream stream;
    FileOutputStream fos;
    String filename;
    PdfDictionary refs = filespec.getAsDict(PdfName.EF);
    for (PdfName key : refs.getKeys()) {
      stream = (PRStream) PdfReader.getPdfObject(refs.getAsIndirectObject(key));
      filename = filespec.getAsString(key).toString();
      fos = new FileOutputStream(new File(dir, filename));
      fos.write(PdfReader.getStreamBytes(stream));
      fos.flush();
      fos.close();
    }
  }

  private int getCountOfAttachments(PdfArray fileSpecs) {
    int count = 0;
    for (PdfObject p : fileSpecs) {
      if (p instanceof PdfString) {
        count++;
      }
    }
    return count;
  }
}

   */

  public ExtractAttachmentOriginal(String src, String dir) throws IOException {
    File folder = new File(dir);
    folder.mkdirs();
    PdfReader reader = new PdfReader(src);
    PdfDictionary root = reader.getCatalog();
    System.out.println("Is Dictionary: " + root.isDictionary());
    PdfDictionary names = root.getAsDict(PdfName.NAMES);
    PdfDictionary embedded = null;
    try {
      embedded = names.getAsDict(PdfName.EMBEDDEDFILES);
      System.out.println("EmbeddedFiles: " + embedded);
      PdfArray fileSpecs = embedded.getAsArray(PdfName.NAMES);
      if (fileSpecs == null) {
        fileSpecs = embedded.getAsArray(PdfName.KIDS);
        if (fileSpecs != null) {
          fileSpecs = fileSpecs.getAsDict(0).getAsArray(PdfName.NAMES);
        }
      }
      if (fileSpecs != null) {
        System.out.println("ATTACHMENTS: " + getCountOfAttachments(fileSpecs));
        for (int i = 0; i < fileSpecs.size(); ) {
          extractAttachment(reader, folder, fileSpecs.getAsString(i++), fileSpecs.getAsDict(i++));
        }
      } else {
        System.out.println("PDF DOESN'T HAVE ATTACHMENTS");
      }
    } catch (Exception e) {
      System.out.println("Error while extracting pdf: " + e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException {
    String src = "/Users/rahil.r/Downloads/Test.pdf";
    String dir = "/Users/rahil.r/Downloads/Attachments/";
    ExtractAttachmentOriginal e = new ExtractAttachmentOriginal(src, dir);
  }

  protected static void extractAttachment(
      PdfReader reader, File dir, PdfString name, PdfDictionary filespec) throws IOException {
    PRStream stream;
    FileOutputStream fos;
    String filename;
    PdfDictionary refs = filespec.getAsDict(PdfName.EF);
    for (PdfName key : refs.getKeys()) {
      stream = (PRStream) PdfReader.getPdfObject(refs.getAsIndirectObject(key));
      filename = filespec.getAsString(key).toString();
      fos = new FileOutputStream(new File(dir, filename));
      fos.write(PdfReader.getStreamBytes(stream));
      fos.flush();
      fos.close();
    }
  }

  private int getCountOfAttachments(PdfArray fileSpecs) {
    int count = 0;
    for (PdfObject p : fileSpecs) {
      if (p instanceof PdfString) {
        count++;
      }
    }
    return count;
  }
}
