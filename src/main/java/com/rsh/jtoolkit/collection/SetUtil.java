package com.rsh.jtoolkit.collection;

import com.google.common.collect.Sets;
import java.util.Set;

public class SetUtil {

  private SetUtil() {}

  public static Set<String> stringToSet(String input, String delimiter) {
    return Sets.newHashSet(input.split(delimiter));
  }
}
