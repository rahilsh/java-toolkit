package in.time;

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

    //    Date input = new Date();
    //    input.setDate(5);
    //    input.setMonth(5);
    //    LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    //    System.out.println(
    //        date.withDayOfMonth(1).minusMonths(1).atStartOfDay(ZoneOffset.UTC).toInstant());
  }
}
