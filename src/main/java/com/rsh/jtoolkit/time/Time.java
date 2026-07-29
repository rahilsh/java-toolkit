package com.rsh.jtoolkit.time;

import java.sql.Timestamp;

public class Time {

  private Time() {}

  public static Timestamp getTimestampFromMicros(final Long timestampInMicros) {
    long timestampInMillis = timestampInMicros / 1000;
    Timestamp timestamp = new Timestamp(timestampInMillis);
    int nanos = (int) (timestampInMicros % 1000000) * 1000;
    timestamp.setNanos(nanos);

    return timestamp;
  }
}
