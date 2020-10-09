package in.rsh.jutil.pdf.sign;

import java.io.InputStream;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;

/*
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
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
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.util.Matrix;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
*/
// TODO: Check if this is needed
public class CreateSignature implements SignatureInterface {

  @Override
  public byte[] sign(InputStream inputStream) {
    return new byte[0];
  }
  // static boolean der = true;

  /*
  static PrivateKey privateKey;
  static Certificate certificate;

  private PDRectangle createSignatureRectangle(PDDocument doc, Rectangle2D humanRect) {
    float x = (float) humanRect.getX();
    float y = (float) humanRect.getY();
    float width = (float) humanRect.getWidth();
    float height = (float) humanRect.getHeight();
    PDPage page = doc.getPage(0);
    PDRectangle pageRect = page.getCropBox();
    PDRectangle rect = new PDRectangle();
    // signing should be at the same position regardless of page rotation.
    switch (page.getRotation()) {
      case 90:
        rect.setLowerLeftY(x);
        rect.setUpperRightY(x + width);
        rect.setLowerLeftX(y);
        rect.setUpperRightX(y + height);
        break;
      case 180:
        rect.setUpperRightX(pageRect.getWidth() - x);
        rect.setLowerLeftX(pageRect.getWidth() - x - width);
        rect.setLowerLeftY(y);
        rect.setUpperRightY(y + height);
        break;
      case 270:
        rect.setLowerLeftY(pageRect.getHeight() - x - width);
        rect.setUpperRightY(pageRect.getHeight() - x);
        rect.setLowerLeftX(pageRect.getWidth() - y - height);
        rect.setUpperRightX(pageRect.getWidth() - y);
        break;
      case 0:
      default:
        rect.setLowerLeftX(x);
        rect.setUpperRightX(x + width);
        rect.setLowerLeftY(pageRect.getHeight() - y - height);
        rect.setUpperRightY(pageRect.getHeight() - y);
        break;
    }
    return rect;
  }

  boolean signPdf(File pdfFile, File signedPdfFile) {

    try (FileInputStream fis = new FileInputStream(pdfFile);
        FileOutputStream fos = new FileOutputStream(signedPdfFile);
        //FileInputStream fis = new FileInputStream(fixed ? signedPdfFile : pdfFile);
        PDDocument doc = PDDocument.load(pdfFile)) {
      int readCount;
      byte[] buffer = new byte[8 * 1024];
      while ((readCount = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, readCount);
      }
      Rectangle2D humanRect = new Rectangle2D.Float(100, 200, 150, 50);
      PDRectangle rect = createSignatureRectangle(doc, humanRect);

      PDSignature signature = new PDSignature();
      signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
      signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
      signature.setName("NAME");
      signature.setLocation("LOCATION");
      signature.setReason("REASON");
      signature.setSignDate(Calendar.getInstance());
      //     PDVisibleSigProperties pdVisibleSigProperties = new PDVisibleSigProperties();
      //      pdVisibleSigProperties
      //          .signerName("name")
      //          .signerLocation("location")
      //          .signatureReason("Security")
      //          .page(1)
      //          .visualSignEnabled(true)
      //          .preferredSize(100)
      //          .setPdVisibleSignature(
      //              new PDVisibleSignDesigner(
      //                      doc, new FileInputStream("/Users/rahil.r/Pictures/test.jpg"), 1)
      //                  .xAxis(0)
      //                  .yAxis(0)
      //                  .zoom(-70)
      //                  .signatureFieldName("signature"));

      SignatureOptions signatureOptions = new SignatureOptions();
      signatureOptions.setVisualSignature(createVisualSignatureTemplate(doc, 0, rect));
      //signatureOptions.setVisualSignature(pdVisibleSigProperties);
      doc.addSignature(signature, this, signatureOptions);
      doc.saveIncremental(fos);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private InputStream createVisualSignatureTemplate(
      PDDocument srcDoc, int pageNum, PDRectangle rect) throws IOException {
    try (PDDocument doc = new PDDocument()) {
      PDPage page = new PDPage(srcDoc.getPage(pageNum).getMediaBox());
      doc.addPage(page);
      PDAcroForm acroForm = new PDAcroForm(doc);
      doc.getDocumentCatalog().setAcroForm(acroForm);
      PDSignatureField signatureField = new PDSignatureField(acroForm);
      PDAnnotationWidget widget = signatureField.getWidgets().get(0);
      List<PDField> acroFormFields = acroForm.getFields();
      acroForm.setSignaturesExist(true);
      acroForm.setAppendOnly(true);
      acroForm.getCOSObject().setDirect(true);
      acroFormFields.add(signatureField);

      widget.setRectangle(rect);

      // from PDVisualSigBuilder.createHolderForm()
      PDStream stream = new PDStream(doc);
      PDFormXObject form = new PDFormXObject(stream);
      PDResources res = new PDResources();
      form.setResources(res);
      form.setFormType(1);
      PDRectangle bbox = new PDRectangle(rect.getWidth(), rect.getHeight());
      float height = bbox.getHeight();
      Matrix initialScale = null;
      switch (srcDoc.getPage(pageNum).getRotation()) {
        case 90:
          form.setMatrix(AffineTransform.getQuadrantRotateInstance(1));
          initialScale =
              Matrix.getScaleInstance(
                  bbox.getWidth() / bbox.getHeight(), bbox.getHeight() / bbox.getWidth());
          height = bbox.getWidth();
          break;
        case 180:
          form.setMatrix(AffineTransform.getQuadrantRotateInstance(2));
          break;
        case 270:
          form.setMatrix(AffineTransform.getQuadrantRotateInstance(3));
          initialScale =
              Matrix.getScaleInstance(
                  bbox.getWidth() / bbox.getHeight(), bbox.getHeight() / bbox.getWidth());
          height = bbox.getWidth();
          break;
        case 0:
        default:
          break;
      }
      form.setBBox(bbox);
      PDFont font = PDType1Font.HELVETICA_BOLD;

      // from PDVisualSigBuilder.createAppearanceDictionary()
      PDAppearanceDictionary appearance = new PDAppearanceDictionary();
      appearance.getCOSObject().setDirect(true);
      PDAppearanceStream appearanceStream = new PDAppearanceStream(form.getCOSObject());
      appearance.setNormalAppearance(appearanceStream);
      widget.setAppearance(appearance);

      try (PDPageContentStream cs = new PDPageContentStream(doc, appearanceStream)) {
        // for 90Ã‚Â° and 270Ã‚Â° scale ratio of width / height
        // not really sure about this
        // why does scale have no effect when done in the form matrix???
        if (initialScale != null) {
          cs.transform(initialScale);
        }

        */
  /* // show background (just for debugging, to see the rect size + position)
  cs.setNonStrokingColor(Color.white);
  cs.addRect(-5000, -5000, 10000, 10000);
  cs.fill();*/
  /*


          // show background image
          // save and restore graphics if the image is too large and needs to be scaled
          cs.saveGraphicsState();
          cs.transform(Matrix.getScaleInstance(0.25f, 0.25f));
          // PDImageXObject img = PDImageXObject.createFromFileByExtension(imageFile, doc);
          // cs.drawImage(img, 0, 0);
          cs.restoreGraphicsState();

          // show text
          float fontSize = 10;
          float leading = fontSize * 1.5f;
          cs.beginText();
          cs.setFont(font, fontSize);
          cs.setNonStrokingColor(Color.black);
          cs.newLineAtOffset(fontSize, height - leading);
          cs.setLeading(leading);
          cs.showText("Digitally Signed By rsh");
          cs.endText();
        }

        // no need to set annotations and /P entry

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        return new ByteArrayInputStream(baos.toByteArray());
      }
    }

    @Override
    public byte[] sign(InputStream is) {
      try {
        BouncyCastleProvider BC = new BouncyCastleProvider();
        Store<?> certStore = new JcaCertStore(Collections.singletonList(certificate));

        CMSTypedDataInputStream input = new CMSTypedDataInputStream(is);
        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
        ContentSigner sha512Signer =
            new JcaContentSignerBuilder("SHA256WithRSA").setProvider(BC).build(privateKey);

        gen.addSignerInfoGenerator(
            new JcaSignerInfoGeneratorBuilder(
                    new JcaDigestCalculatorProviderBuilder().setProvider(BC).build())
                .build(sha512Signer, new X509CertificateHolder(certificate.getEncoded())));
        gen.addCertificates(certStore);
        CMSSignedData signedData = gen.generate(input, false);

        //if (der) {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          DEROutputStream dos = new DEROutputStream(baos);
          dos.writeObject(signedData.toASN1Structure());
          return baos.toByteArray();
        //} else return signedData.getEncoded();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
      char[] password = "123456".toCharArray();

      KeyStore keystore = KeyStore.getInstance("PKCS12");
      keystore.load(new FileInputStream("/Users/rahil.r/Documents/test.p12"), password);

      Enumeration<String> aliases = keystore.aliases();
      String alias;
      if (aliases.hasMoreElements()) {
        alias = aliases.nextElement();
      } else {
        throw new KeyStoreException("Keystore is empty");
      }
      privateKey = (PrivateKey) keystore.getKey(alias, password);
      Certificate[] certificateChain = keystore.getCertificateChain(alias);
      certificate = certificateChain[0];

      File inFile = new File("/Users/rahil.r/Documents/test.pdf");
      File outFile = new File("/Users/rahil.r/Documents/test_signed.pdf");
      new CreateSignature().signPdf(inFile, outFile);
    }
  }

  class CMSTypedDataInputStream implements CMSTypedData {
    InputStream in;

    public CMSTypedDataInputStream(InputStream is) {
      in = is;
    }

    @Override
    public ASN1ObjectIdentifier getContentType() {
      return PKCSObjectIdentifiers.data;
    }

    @Override
    public Object getContent() {
      return in;
    }

    @Override
    public void write(OutputStream out) throws IOException {
      byte[] buffer = new byte[8 * 1024];
      int read;
      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }
      in.close();
    }
    */
}
