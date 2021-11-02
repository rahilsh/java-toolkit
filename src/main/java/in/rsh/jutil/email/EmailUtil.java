package in.rsh.jutil.email;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailUtil {
  public static boolean isEmailValid() {
    return EmailValidator.getInstance().isValid("test@test.com");
  }
}
