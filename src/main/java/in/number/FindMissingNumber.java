package in.number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindMissingNumber {

  public static void main(String[] args) {
    int a[] = {15, 10, 13, 12, 11};
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      if (a[i + 1] - a[i] != 1) {
        System.out.println(a[i + 1] - 1);
        break;
      }
    }
    List aa = new ArrayList<>();
    aa.get(0);
  }
}
