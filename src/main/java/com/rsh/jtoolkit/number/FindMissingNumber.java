package com.rsh.jtoolkit.number;

import java.util.Arrays;

public class FindMissingNumber {

  public static void main(String[] args) {
    // TODO: find using min and max in O(n)
    int[] a = {15, 10, 13, 12, 11};
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      if (a[i + 1] - a[i] != 1) {
        System.out.println(a[i + 1] - 1);
        break;
      }
    }
  }
}
