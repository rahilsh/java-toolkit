package in.r.util.string;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathManipulator {

  public static void main(String[] args) {
    String s = "/00/11/22/";

    Path p=Paths.get(s);
    System.out.println(
    p.getParent().toString());
  }
}
