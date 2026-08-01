package com.rsh.jtoolkit.email;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailUtilTest {

  @ParameterizedTest
  @ValueSource(strings = {"test@example.com", "user.name@domain.org", "user+tag@domain.co.uk"})
  void returnsTrueForValidEmails(String email) {
    assertTrue(EmailUtil.isValid(email));
  }

  @ParameterizedTest
  @ValueSource(strings = {"invalid", "@domain.com", "user@", ""})
  void returnsFalseForInvalidEmails(String email) {
    assertFalse(EmailUtil.isValid(email));
  }

  @Test
  void returnsFalseForNull() {
    assertFalse(EmailUtil.isValid(null));
  }
}
