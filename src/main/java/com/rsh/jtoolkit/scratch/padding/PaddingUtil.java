package com.rsh.jtoolkit.scratch.padding;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class PaddingUtil {

  public static void main(String[] args) {
    String uniqueID = "10715";
    checkArgument(uniqueID.length() < 8);
    for (int i = 0; i < 20; i++) {
      System.out.println(
          55
              + StringUtils.leftPad(uniqueID, 8, '0')
              + StringUtils.leftPad(
                  String.valueOf(RandomUtils.nextInt()), String.valueOf(99).length(), '0'));
    }
  }
}
