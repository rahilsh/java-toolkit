package com.rsh.jtoolkit.digitalsign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GenerateKeysTest {

  private GenerateKeys generateKeys;

  @BeforeEach
  void setUp() throws NoSuchAlgorithmException {
    generateKeys = new GenerateKeys(1024);
    generateKeys.createKeys();
  }

  @Test
  void generatesRsaKeyPair() {
    assertNotNull(generateKeys.getPrivateKey());
    assertNotNull(generateKeys.getPublicKey());
    assertEquals("RSA", generateKeys.getPublicKey().getAlgorithm());
    assertEquals("RSA", generateKeys.getPrivateKey().getAlgorithm());
  }

  @Test
  void writesKeyToFile(@TempDir Path tempDir) throws Exception {
    Path publicKeyFile = tempDir.resolve("keys/public.key");
    assertTrue(
        generateKeys.writeToFile(
            publicKeyFile.toString(), generateKeys.getPublicKey().getEncoded()));

    assertTrue(Files.exists(publicKeyFile));
    assertTrue(Files.size(publicKeyFile) > 0);
  }
}
