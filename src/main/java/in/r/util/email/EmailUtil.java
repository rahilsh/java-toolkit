package in.r.util.email;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailUtil {
  public static boolean isEmailValid() {
    return EmailValidator.getInstance().isValid("senthilkumarR@cholams.murugappa.com");
  }
}
