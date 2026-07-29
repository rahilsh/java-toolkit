package com.rsh.jtoolkit.collection;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Utility methods for working with {@link List} instances. */
public final class ListUtil {

  private ListUtil() {}

  /**
   * Returns the minimum value in the list, or {@code 0} when the list is empty.
   */
  public static Integer min(List<Integer> list) {
    Optional<Integer> optionalMin = list.stream().min(Comparator.naturalOrder());
    return optionalMin.orElse(0);
  }

  /**
   * Returns the maximum value in the list, or {@code 0} when the list is empty.
   */
  public static Integer max(List<Integer> list) {
    Optional<Integer> optionalMax = list.stream().max(Comparator.naturalOrder());
    return optionalMax.orElse(0);
  }
}
