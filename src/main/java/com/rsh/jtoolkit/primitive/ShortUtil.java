package com.rsh.jtoolkit.primitive;

public final class ShortUtil {

  private ShortUtil() {}

  public static short getReversedBytes(short value) {
    return Short.reverseBytes(value);
  }
}
