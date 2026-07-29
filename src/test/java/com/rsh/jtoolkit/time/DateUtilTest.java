package com.rsh.jtoolkit.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class DateUtilTest {

  private final LocalDate feb15 = LocalDate.of(2019, 2, 15); // non leap year

  @Test
  void firstAndLastDayOfMonth() {
    assertEquals(LocalDate.of(2019, 2, 1), DateUtil.firstDayOfMonth(feb15));
    assertEquals(LocalDate.of(2019, 2, 28), DateUtil.lastDayOfMonth(feb15));
  }

  @Test
  void leapYearLastDay() {
    assertEquals(LocalDate.of(2020, 2, 29), DateUtil.lastDayOfMonth(LocalDate.of(2020, 2, 10)));
  }

  @Test
  void startAndEndOfMonthUtc() {
    assertEquals(Instant.parse("2019-02-01T00:00:00Z"), DateUtil.startOfMonthUtc(feb15));
    assertEquals(Instant.parse("2019-02-28T00:00:00Z"), DateUtil.endOfMonthUtc(feb15));
  }

  @Test
  void startOfMonthHonoursZone() {
    Instant expected = LocalDate.of(2019, 2, 1).atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant();
    assertEquals(expected, DateUtil.startOfMonth(feb15, ZoneId.of("Asia/Kolkata")));
  }
}
