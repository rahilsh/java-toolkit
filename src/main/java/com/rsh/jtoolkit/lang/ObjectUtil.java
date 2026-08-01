package com.rsh.jtoolkit.lang;

/** Small helpers for working with possibly-null references. */
public final class ObjectUtil {

  private ObjectUtil() {}

  /**
   * Returns the first non-{@code null} argument, or {@code null} if all arguments are {@code null}
   * (or none are supplied).
   */
  @SafeVarargs
  public static <T> T firstNonNull(T... items) {
    if (items == null) {
      return null;
    }
    for (T item : items) {
      if (item != null) {
        return item;
      }
    }
    return null;
  }
}
