package com.rsh.jtoolkit.scratch.digitalsign;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignatureTest {

  public static void main(String[] args) throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(512);
    KeyPair keyPair = kpg.genKeyPair();
    byte[] data = "test".getBytes(StandardCharsets.UTF_8);
    System.out.println("private= " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
    System.out.println("public= " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
    Signature sig = Signature.getInstance("MD5WithRSA");
    sig.initSign(keyPair.getPrivate());
    sig.update(data);
    byte[] signatureBytes = sig.sign();
    System.out.println("Singature: " + Base64.getEncoder().encodeToString(signatureBytes));
    sig.initVerify(keyPair.getPublic());
    sig.update(data);
    System.out.println(sig.verify(signatureBytes));
  }
}
