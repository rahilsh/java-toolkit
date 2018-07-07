package in.email;

public class EmailValidator {
  public static void main(String[] args) {
    System.out.println(
        org.apache.commons.validator.routines.EmailValidator.getInstance()
            .isValid("senthilkumarR@cholams.murugappa.com"));
  }
}
