package in.rsh.jutil.string;

import com.google.common.collect.ImmutableList;

public class TestString {
  public static void main(String[] args) throws Exception {

    //    String s = "/Users/rahil.r/Documents/foo/reports/resources/"+"cust";
    //    System.out.println(
    //    s.substring(0,s.lastIndexOf("/")));
    //    System.out.println(s.split("/", -1));
    // for (String ss : s.split("/", -1)) {
    //      System.out.println(ss);
    // }

    //
    //    String[] createJobFolder = {
    //      "/bin/sh", "-c", "find /Users/rahil.r/Documents/bar -type d -execdir rename 's/_files//'
    // '{}' \\;"
    //    };
    //    Process proc = null;
    //    try {
    //      proc = Runtime.getRuntime().exec(createJobFolder);
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //    int exitVal = 0;
    //    try {
    //      exitVal = proc.waitFor();
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
    //    if (exitVal != 0) {
    //      throw new Exception("Exit not 0 for reading xml");
    //    }
    //    System.out.println("Done");

    String s=String.join(",", ImmutableList.of(""));
    System.out.println(s);
  }
}
