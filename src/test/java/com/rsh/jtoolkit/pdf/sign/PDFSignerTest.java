package com.rsh.jtoolkit.pdf.sign;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PDFSignerTest {

  private static final char[] PIN = "changeit".toCharArray();

  private KeyStore keyStore;

  @BeforeEach
  void setUp() throws Exception {
    keyStore = selfSignedPkcs12();
  }

  @Test
  void signsPdfAndAddsSignatureDictionary() throws Exception {
    byte[] source = onePagePdf();
    PDFSigner signer = new PDFSigner(keyStore, PIN);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (ByteArrayInputStream in = new ByteArrayInputStream(source)) {
      signer.signPDF(in, out, new PDRectangle(50, 50, 200, 50));
    }

    byte[] signed = out.toByteArray();
    assertTrue(signed.length > source.length, "signed PDF should be larger than the source");
    try (PDDocument doc = PDDocument.load(signed)) {
      assertFalse(doc.getSignatureDictionaries().isEmpty(), "expected a signature dictionary");
    }
  }

  @Test
  void constructorFailsWhenKeystoreHasNoCertificate() throws Exception {
    KeyStore empty = KeyStore.getInstance("PKCS12");
    empty.load(null, null);
    assertThrows(IOException.class, () -> new PDFSigner(empty, PIN));
  }

  private static byte[] onePagePdf() throws IOException {
    try (PDDocument doc = new PDDocument()) {
      doc.addPage(new PDPage());
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      doc.save(baos);
      return baos.toByteArray();
    }
  }

  private static KeyStore selfSignedPkcs12() throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(2048);
    KeyPair keyPair = kpg.generateKeyPair();

    X500Name subject = new X500Name("CN=java-toolkit-test");
    Instant now = Instant.now();
    Date notBefore = Date.from(now.minus(1, ChronoUnit.DAYS));
    Date notAfter = Date.from(now.plus(365, ChronoUnit.DAYS));
    BigInteger serial = BigInteger.valueOf(now.toEpochMilli());

    JcaX509v3CertificateBuilder certBuilder =
        new JcaX509v3CertificateBuilder(
            subject, serial, notBefore, notAfter, subject, keyPair.getPublic());
    ContentSigner contentSigner =
        new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());
    X509Certificate certificate =
        new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner));

    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    keyStore.load(null, null);
    keyStore.setKeyEntry("alias", keyPair.getPrivate(), PIN, new Certificate[] {certificate});
    return keyStore;
  }
}
