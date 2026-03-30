package in.rsh.jutil.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;

class PhoneNumberUtilTest {

  @Test
  void testValidPhoneNumber() throws NumberParseException {
    assertTrue(PhoneNumberUtil.isValid("+639818167419"));
  }

  @Test
  void testInvalidPhoneNumber() throws NumberParseException {
    assertFalse(PhoneNumberUtil.isValid("+123"));
  }

  @Test
  void testValidUSPhoneNumber() throws NumberParseException {
    assertTrue(PhoneNumberUtil.isValid("+12025551234"));
  }

  @Test
  void testValidUKPhoneNumber() throws NumberParseException {
    assertTrue(PhoneNumberUtil.isValid("+447911123456"));
  }
}
