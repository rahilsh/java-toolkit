package com.rsh.jtoolkit.digitalsign;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Helpers for loading RSA keys from their encoded byte representations.
 *
 * <p>Public keys are expected in X.509 ({@code SubjectPublicKeyInfo}) form and private keys in
 * PKCS#8 form, which is the encoding produced by {@link java.security.Key#getEncoded()} and written
 * by {@link GenerateKeys#writeToFile(String, byte[])}. This completes the round-trip with {@link
 * GenerateKeys} and {@link SignatureUtil}.
 */
public final class KeyUtil {

  private static final String ALGORITHM = "RSA";

  private KeyUtil() {}

  /**
   * Reconstructs an RSA {@link PublicKey} from its X.509-encoded bytes.
   *
   * @return the decoded public key
   */
  public static PublicKey loadPublicKey(byte[] encoded) throws GeneralSecurityException {
    X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
    return KeyFactory.getInstance(ALGORITHM).generatePublic(spec);
  }

  /**
   * Reconstructs an RSA {@link PrivateKey} from its PKCS#8-encoded bytes.
   *
   * @return the decoded private key
   */
  public static PrivateKey loadPrivateKey(byte[] encoded) throws GeneralSecurityException {
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
    return KeyFactory.getInstance(ALGORITHM).generatePrivate(spec);
  }

  /**
   * Reads an X.509-encoded RSA {@link PublicKey} from {@code path}.
   *
   * @return the decoded public key
   */
  public static PublicKey loadPublicKey(Path path) throws GeneralSecurityException, IOException {
    return loadPublicKey(Files.readAllBytes(path));
  }

  /**
   * Reads a PKCS#8-encoded RSA {@link PrivateKey} from {@code path}.
   *
   * @return the decoded private key
   */
  public static PrivateKey loadPrivateKey(Path path) throws GeneralSecurityException, IOException {
    return loadPrivateKey(Files.readAllBytes(path));
  }
}
