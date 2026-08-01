package com.rsh.jtoolkit.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

/** Utility methods for common calendar-date calculations. */
public final class DateUtil {

  private DateUtil() {}

  /** Returns the first day of the month that {@code date} falls in. */
  public static LocalDate firstDayOfMonth(LocalDate date) {
    return date.withDayOfMonth(1);
  }

  /** Returns the last day of the month that {@code date} falls in. */
  public static LocalDate lastDayOfMonth(LocalDate date) {
    return date.withDayOfMonth(date.lengthOfMonth());
  }

  /** Returns the instant at the start of the first day of {@code date}'s month, in {@code zone}. */
  public static Instant startOfMonth(LocalDate date, ZoneId zone) {
    return firstDayOfMonth(date).atStartOfDay(zone).toInstant();
  }

  /** Returns the instant at the start of the last day of {@code date}'s month, in {@code zone}. */
  public static Instant endOfMonth(LocalDate date, ZoneId zone) {
    return lastDayOfMonth(date).atStartOfDay(zone).toInstant();
  }

  /** {@link #startOfMonth(LocalDate, ZoneId)} using UTC. */
  public static Instant startOfMonthUtc(LocalDate date) {
    return startOfMonth(date, ZoneOffset.UTC);
  }

  /** {@link #endOfMonth(LocalDate, ZoneId)} using UTC. */
  public static Instant endOfMonthUtc(LocalDate date) {
    return endOfMonth(date, ZoneOffset.UTC);
  }
}
