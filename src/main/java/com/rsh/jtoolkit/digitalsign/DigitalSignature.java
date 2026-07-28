package com.rsh.jtoolkit.digitalsign;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class DigitalSignature {

  public static void main(String[] args) throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("key1", "value1");
    jsonObject.put("key2", "value2");
    jsonObject.put("key3", "value3");
    System.out.println();
    Signature rsa = Signature.getInstance("SHA1withRSA");

    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    KeyPair pair = keyGen.generateKeyPair();
    System.out.println("Private=" + pair.getPrivate());
    System.out.println("Public=" + pair.getPublic());

    byte[] keyBytes = pair.getPrivate().getEncoded();
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    rsa.initSign(kf.generatePrivate(spec));
    rsa.update(jsonObject.toString().getBytes());
    byte[] data = rsa.sign();

    String str = new String(data, StandardCharsets.UTF_8);
    System.out.print("String= ");
    System.out.println(str);
    String readableBytes = Hex.encodeHexString(data); // readable bytes
    System.out.println("readableBytes=" + readableBytes);

    byte[] b = new byte[readableBytes.length() / 2];
    for (int i = 0; i < b.length; i++) {
      int index = i * 2;
      int v = Integer.parseInt(readableBytes.substring(index, index + 2), 16);
      b[i] = (byte) v;
    }
    String str1 = new String(b, StandardCharsets.UTF_8);
    System.out.print("HEX=");
    System.out.println(str1);

    verify(pair.getPublic(), jsonObject.toString().getBytes(), data);
  }

  private static void verify(PublicKey aPublic, byte[] data, byte[] bytes)
      throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
    Signature sig = Signature.getInstance("SHA1withRSA");
    sig.initVerify(aPublic);
    sig.update(data);
    System.out.println("Verified: " + sig.verify(bytes));
  }
}
