package com.rsh.jtoolkit.pdf.sign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class PDFSigner implements SignatureInterface {

  private static final PDFont FONT = PDType1Font.HELVETICA_BOLD;
  private static final float FONT_SIZE = 10;
  private static final float LEADING = FONT_SIZE * 1.5f;
  private PrivateKey privateKey;
  private Certificate[] certificateChain;

  public PDFSigner(KeyStore keystore, char[] pin)
      throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException,
          CertificateException {
    Enumeration<String> aliases = keystore.aliases();
    String alias;
    Certificate cert = null;
    while (aliases.hasMoreElements()) {
      alias = aliases.nextElement();
      setPrivateKey((PrivateKey) keystore.getKey(alias, pin));
      Certificate[] certChain = keystore.getCertificateChain(alias);
      if (certChain == null) {
        continue;
      }
      setCertificateChain(certChain);
      cert = certChain[0];
      if (cert instanceof X509Certificate) {
        ((X509Certificate) cert).checkValidity();
      }
      break;
    }
    if (cert == null) {
      throw new IOException("Could not find certificate");
    }
  }

  public final void setPrivateKey(PrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  public final void setCertificateChain(final Certificate[] certificateChain) {
    this.certificateChain = certificateChain;
  }

  @Override
  public byte[] sign(InputStream content) throws IOException {
    try {
      CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
      X509Certificate cert = (X509Certificate) certificateChain[0];
      ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").build(privateKey);
      gen.addSignerInfoGenerator(
          new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build())
              .build(sha1Signer, cert));
      gen.addCertificates(new JcaCertStore(Arrays.asList(certificateChain)));
      CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
      CMSSignedData signedData = gen.generate(msg, false);
      return signedData.getEncoded();
    } catch (GeneralSecurityException | CMSException | OperatorCreationException e) {
      throw new IOException(e);
    }
  }

  public void signPDF(InputStream inputFile, ByteArrayOutputStream fos, PDRectangle rect)
      throws IOException {
    try (PDDocument doc = PDDocument.load(inputFile)) {
      PDSignature signature = new PDSignature();
      signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
      signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
      signature.setSignDate(Calendar.getInstance());
      SignatureOptions signatureOptions = new SignatureOptions();
      int pageNum = doc.getNumberOfPages() - 1;
      signatureOptions.setVisualSignature(createVisualSignatureTemplate(doc, pageNum, rect));
      signatureOptions.setPage(pageNum);
      doc.addSignature(signature, this, signatureOptions);
      doc.saveIncremental(fos);
      IOUtils.closeQuietly(signatureOptions);
    }
  }

  private InputStream createVisualSignatureTemplate(
      PDDocument srcDoc, int pageNum, PDRectangle rect) throws IOException {
    try (PDDocument doc = new PDDocument()) {
      doc.addPage(new PDPage(srcDoc.getPage(pageNum).getMediaBox()));
      PDFormXObject form = getForm(doc, rect);
      PDAppearanceStream appearanceStream = new PDAppearanceStream(form.getCOSObject());
      PDAppearanceDictionary appearance = getAppearance(appearanceStream);
      addSignatureFieldToAcroForm(doc, rect, appearance);
      try (PDPageContentStream cs = new PDPageContentStream(doc, appearanceStream)) {
        cs.beginText();
        cs.setFont(FONT, FONT_SIZE);
        cs.newLineAtOffset(FONT_SIZE, form.getBBox().getHeight() - LEADING);
        cs.showText("Digitally Signed By rsh");
        cs.endText();
      }
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      doc.save(baos);
      return new ByteArrayInputStream(baos.toByteArray());
    }
  }

  private PDAppearanceDictionary getAppearance(PDAppearanceStream appearanceStream) {
    PDAppearanceDictionary appearance = new PDAppearanceDictionary();
    appearance.getCOSObject().setDirect(true);
    appearance.setNormalAppearance(appearanceStream);
    return appearance;
  }

  private PDFormXObject getForm(PDDocument doc, PDRectangle rect) {
    PDStream stream = new PDStream(doc);
    PDFormXObject form = new PDFormXObject(stream);
    PDResources res = new PDResources();
    form.setResources(res);
    form.setFormType(1);
    PDRectangle bbox = new PDRectangle(rect.getWidth(), rect.getHeight());
    form.setBBox(bbox);
    return form;
  }

  private void addSignatureFieldToAcroForm(
      PDDocument doc, PDRectangle rect, PDAppearanceDictionary appearance) throws IOException {
    PDAcroForm acroForm = new PDAcroForm(doc);
    doc.getDocumentCatalog().setAcroForm(acroForm);
    PDSignatureField signatureField = new PDSignatureField(acroForm);
    updateWidget(rect, appearance, signatureField);
    acroForm.getFields().add(signatureField);
  }

  private void updateWidget(
      PDRectangle rect, PDAppearanceDictionary appearance, PDSignatureField signatureField) {
    PDAnnotationWidget widget = signatureField.getWidgets().get(0);
    widget.setRectangle(rect);
    widget.setAppearance(appearance);
  }

  private static class CMSProcessableInputStream implements CMSTypedData {
    private final ASN1ObjectIdentifier contentType;
    private final InputStream in;

    CMSProcessableInputStream(InputStream is) {
      this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), is);
    }

    CMSProcessableInputStream(ASN1ObjectIdentifier type, InputStream is) {
      contentType = type;
      in = is;
    }

    @Override
    public Object getContent() {
      return in;
    }

    @Override
    public void write(OutputStream out) throws IOException {
      IOUtils.copy(in, out);
      in.close();
    }

    @Override
    public ASN1ObjectIdentifier getContentType() {
      return contentType;
    }
  }
}
