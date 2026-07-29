package com.rsh.jtoolkit.digitalsign;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignatureUtilTest {

  private KeyPair keyPair;

  @BeforeEach
  void setUp() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    keyPair = generator.generateKeyPair();
  }

  @Test
  void signedDataVerifies() throws Exception {
    byte[] data = "payload".getBytes(StandardCharsets.UTF_8);
    byte[] signature = SignatureUtil.sign(keyPair.getPrivate(), data);
    assertTrue(SignatureUtil.verify(keyPair.getPublic(), data, signature));
  }

  @Test
  void tamperedDataFailsVerification() throws Exception {
    byte[] data = "payload".getBytes(StandardCharsets.UTF_8);
    byte[] signature = SignatureUtil.sign(keyPair.getPrivate(), data);
    byte[] tampered = "payloaD".getBytes(StandardCharsets.UTF_8);
    assertFalse(SignatureUtil.verify(keyPair.getPublic(), tampered, signature));
  }

  @Test
  void tamperedSignatureFailsVerification() throws Exception {
    byte[] data = "payload".getBytes(StandardCharsets.UTF_8);
    byte[] signature = SignatureUtil.sign(keyPair.getPrivate(), data);
    signature[0] ^= 0x01;
    assertFalse(SignatureUtil.verify(keyPair.getPublic(), data, signature));
  }
}
