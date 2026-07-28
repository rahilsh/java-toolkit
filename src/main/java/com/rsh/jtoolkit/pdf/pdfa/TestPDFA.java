package com.rsh.jtoolkit.pdf.pdfa;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import java.io.FileInputStream;
import java.io.IOException;

public class TestPDFA {
  public static void main(String[] args) {
    PdfReader reader = null;
    try {
      reader = new PdfReader(new FileInputStream("/Users/rahil.r/Downloads/whitepaper-pdfa.pdf"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PdfDocument pdfDocument = new PdfDocument(reader);
    if (pdfDocument.getXmpMetadata() != null) {
      System.out.println("PDF/A");
    } else {
      System.out.println("Not PDF/A");
    }
  }
}
