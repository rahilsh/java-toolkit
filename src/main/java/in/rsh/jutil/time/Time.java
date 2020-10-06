package in.rsh.jutil.time;

import java.sql.Timestamp;

public class Time {

  public static void main(String[] args) {
    System.out.println(getTimestampFromMicros(1499012017901L));
  }

  public static Timestamp getTimestampFromMicros(final Long timestampInMicros) {
    long timestampInMillis = timestampInMicros / 1000;
    Timestamp timestamp = new Timestamp(timestampInMillis);
    int nanos = (int) (timestampInMicros % 1000000) * 1000;
    timestamp.setNanos(nanos);

    return timestamp;
  }
}
