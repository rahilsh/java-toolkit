import java.time.LocalDate;
import java.time.ZoneOffset;

public class StartDateAndEndDateOfMonth {
  public static void main(String[] args) {
    LocalDate today = LocalDate.now();
    System.out.println(
        today
            .withDayOfMonth(1)
            .minusMonths(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli());
    System.out.println(
        today
            .withDayOfMonth(today.lengthOfMonth())
            .minusMonths(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli());
  }
}
