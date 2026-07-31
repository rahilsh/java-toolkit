package com.rsh.jtoolkit.digitalsign;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * RSA digital-signature helpers.
 *
 * <p>Uses {@code SHA256withRSA}. Pairs naturally with {@link GenerateKeys} for producing key
 * material.
 */
public final class SignatureUtil {

  private static final String ALGORITHM = "SHA256withRSA";

  private SignatureUtil() {}

  /**
   * Signs {@code data} with {@code privateKey}.
   *
   * @return the signature bytes
   */
  public static byte[] sign(PrivateKey privateKey, byte[] data) throws GeneralSecurityException {
    Signature signature = Signature.getInstance(ALGORITHM);
    signature.initSign(privateKey);
    signature.update(data);
    return signature.sign();
  }

  /**
   * Verifies that {@code signatureBytes} is a valid signature of {@code data} for {@code
   * publicKey}.
   *
   * @return {@code true} if the signature is valid
   */
  public static boolean verify(PublicKey publicKey, byte[] data, byte[] signatureBytes)
      throws GeneralSecurityException {
    Signature signature = Signature.getInstance(ALGORITHM);
    signature.initVerify(publicKey);
    signature.update(data);
    return signature.verify(signatureBytes);
  }
}
