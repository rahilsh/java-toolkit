package com.rsh.jtoolkit.email;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmailUtilTest {

  @Test
  void testValidEmail() {
    assertTrue(EmailUtil.isEmailValid());
  }

  @Test
  void testEmailValidatorWithValidEmails() {
    org.apache.commons.validator.routines.EmailValidator validator = 
        org.apache.commons.validator.routines.EmailValidator.getInstance();
    assertTrue(validator.isValid("test@example.com"));
    assertTrue(validator.isValid("user.name@domain.org"));
    assertTrue(validator.isValid("user+tag@domain.co.uk"));
  }

  @Test
  void testEmailValidatorWithInvalidEmails() {
    org.apache.commons.validator.routines.EmailValidator validator = 
        org.apache.commons.validator.routines.EmailValidator.getInstance();
    assertFalse(validator.isValid("invalid"));
    assertFalse(validator.isValid("@domain.com"));
    assertFalse(validator.isValid("user@"));
    assertFalse(validator.isValid(""));
  }
}
