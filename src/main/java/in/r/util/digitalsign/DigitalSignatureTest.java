package in.r.util.digitalsign;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import sun.misc.BASE64Encoder;

public class DigitalSignatureTest {

  public static void main(String[] args) throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(512);
    KeyPair keyPair = kpg.genKeyPair();
    byte[] data = "test".getBytes("UTF8");
    System.out.println("private= " + new BASE64Encoder().encode(keyPair.getPrivate().getEncoded()));
    System.out.println("public= " + new BASE64Encoder().encode(keyPair.getPublic().getEncoded()));
    Signature sig = Signature.getInstance("MD5WithRSA");
    sig.initSign(keyPair.getPrivate());
    sig.update(data);
    byte[] signatureBytes = sig.sign();
    System.out.println("Singature: " + new BASE64Encoder().encode(signatureBytes));
    sig.initVerify(keyPair.getPublic());
    sig.update(data);
    System.out.println(sig.verify(signatureBytes));
  }
}
