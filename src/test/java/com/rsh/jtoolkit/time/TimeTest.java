package com.rsh.jtoolkit.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

class TimeTest {

  @Test
  void convertsMicrosToTimestampMillis() {
    Timestamp timestamp = Time.getTimestampFromMicros(1_499_012_017_901_000L);
    assertEquals(1_499_012_017_901L, timestamp.getTime());
  }

  @Test
  void setsSubMillisecondNanos() {
    // 1 second + 500123 micros => nanos component 500123 * 1000
    Timestamp timestamp = Time.getTimestampFromMicros(1_500_123L);
    assertEquals(500_123_000, timestamp.getNanos());
  }
}
