package com.rsh.jtoolkit.email;

import org.apache.commons.validator.routines.EmailValidator;

/** Utility methods for validating email addresses. */
public final class EmailUtil {

  private EmailUtil() {}

  /**
   * Returns {@code true} if the supplied value is a syntactically valid email address.
   *
   * @param email the address to validate; may be {@code null}
   */
  public static boolean isValid(String email) {
    return email != null && EmailValidator.getInstance().isValid(email);
  }
}
