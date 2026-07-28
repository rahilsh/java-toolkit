package com.rsh.jtoolkit.onlinetest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UsingScanner {

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    try (BufferedWriter bufferedWriter =
        new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")))) {

      int arrayCount = scanner.nextInt();
      scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

      int[] array = new int[arrayCount];

      for (int arrayItr = 0; arrayItr < arrayCount; arrayItr++) {
        int arrayItem = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");
        array[arrayItr] = arrayItem;
      }

      //    int[] res = delta_encode(array);
      //
      //    for (int resItr = 0; resItr < res.length; resItr++) {
      //      bufferedWriter.write(String.valueOf(res[resItr]));
      //
      //      if (resItr != res.length - 1) {
      //        bufferedWriter.write("\n");
      //      }
      //    }

      bufferedWriter.newLine();
    }

    scanner.close();
  }
}
