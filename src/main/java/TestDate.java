import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class TestDate {

  public static void main(String[] args) {
    System.out.println(getDateinEpoch(false));
  }

  private static Long getDateinEpoch(boolean isStart) {
    Calendar calendar = Calendar.getInstance();
    if (isStart) {
      calendar.set(calendar.get(Calendar.YEAR), 3, 1, 0, 0, 0);
      return calendar.getTimeInMillis();
    } else {
      calendar.set(calendar.get(Calendar.YEAR) + 1, 2, 31, 23, 59, 59);
      return calendar.getTimeInMillis();
    }
  }
}
