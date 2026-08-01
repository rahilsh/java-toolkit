package com.rsh.jtoolkit.digitalsign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class KeyUtilTest {

  private KeyPair keyPair;

  @BeforeEach
  void setUp() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    keyPair = generator.generateKeyPair();
  }

  @Test
  void loadsKeysFromEncodedBytes() throws Exception {
    PublicKey publicKey = KeyUtil.loadPublicKey(keyPair.getPublic().getEncoded());
    PrivateKey privateKey = KeyUtil.loadPrivateKey(keyPair.getPrivate().getEncoded());

    assertEquals(keyPair.getPublic(), publicKey);
    assertEquals(keyPair.getPrivate(), privateKey);
  }

  @Test
  void loadsKeysFromFilesAndRoundTripsWithSignatureUtil(@TempDir Path tempDir) throws Exception {
    Path publicKeyFile = tempDir.resolve("public.key");
    Path privateKeyFile = tempDir.resolve("private.key");
    Files.write(publicKeyFile, keyPair.getPublic().getEncoded());
    Files.write(privateKeyFile, keyPair.getPrivate().getEncoded());

    PublicKey publicKey = KeyUtil.loadPublicKey(publicKeyFile);
    PrivateKey privateKey = KeyUtil.loadPrivateKey(privateKeyFile);

    byte[] data = "payload".getBytes(StandardCharsets.UTF_8);
    byte[] signature = SignatureUtil.sign(privateKey, data);
    assertTrue(SignatureUtil.verify(publicKey, data, signature));
  }

  @Test
  void rejectsMalformedKeyBytes() {
    byte[] garbage = "not-a-key".getBytes(StandardCharsets.UTF_8);
    assertThrows(GeneralSecurityException.class, () -> KeyUtil.loadPublicKey(garbage));
    assertThrows(GeneralSecurityException.class, () -> KeyUtil.loadPrivateKey(garbage));
  }
}
