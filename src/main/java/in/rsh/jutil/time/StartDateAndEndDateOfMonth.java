package in.rsh.jutil.time;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class StartDateAndEndDateOfMonth {
  public static void main(String[] args) {
    System.out.println(LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneOffset.UTC).toInstant());
    System.out.println(
        LocalDate.now()
            .withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
  }
}
