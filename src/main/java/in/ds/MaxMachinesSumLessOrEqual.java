package in.ds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MaxMachinesSumLessOrEqual {

  public static void main(String[] args) throws IOException {
    //

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine()); // Reading input from STDIN
    // System.out.println("Hi, " + n + "."); // Writing output to STDOUT
    int a[] = new int[n];
    String array = br.readLine();
    String array1[] = array.split(" ");
    for (int i = 0; i < n; i++) {
      a[i] = Integer.parseInt(array1[i]);
    }
    int nq = Integer.parseInt(br.readLine());
    Arrays.sort(a);
    for (int i = 0; i < a.length - 1; i++) {
      a[i + 1] = a[i] + a[i + 1];
      System.out.println();
    }
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    for (int j = 0; j < nq; j++) {
      int q = Integer.parseInt(br.readLine());
      System.out.println(search(a, 0, a.length - 1, q)+1);
    }
  }

  // 1 2 3  4 7  8 9

    private static int search(int[] array, int start_idx, int end_idx, int search_val) {

        if( start_idx == end_idx )
            return array[start_idx] <= search_val ? start_idx : -1;

        int mid_idx = start_idx + (end_idx - start_idx) / 2;

        if( search_val < array[mid_idx] )
            return search( array, start_idx, mid_idx, search_val );

        int ret = search( array, mid_idx+1, end_idx, search_val );
        return ret == -1 ? mid_idx : ret;
    }
}
