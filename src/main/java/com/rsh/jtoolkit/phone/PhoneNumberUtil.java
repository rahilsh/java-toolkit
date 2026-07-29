package com.rsh.jtoolkit.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberUtil {

  private PhoneNumberUtil() {}

  public static boolean isValid(String number) throws NumberParseException {
    com.google.i18n.phonenumbers.PhoneNumberUtil util =
        com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance();
    PhoneNumber phoneNumber = util.parse(number, null);
    return util.isValidNumber(phoneNumber);
  }
}
