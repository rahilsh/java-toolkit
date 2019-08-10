package in.r.util.time;

import java.time.LocalDate;

public class ParseLocalDate {
  public static void main(String[] args) {
    System.out.println(LocalDate.parse("2018-09-10"));
    System.out.println(LocalDate.parse("0-0-0"));
    System.out.println(LocalDate.parse("13-14-2018"));
  }
}
