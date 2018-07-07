package in.collection;

import com.google.common.collect.Sets;
import java.util.Set;

public class SetTest {
  public static void main(String[] args) {
    String s = "a,b";
    Set<String> set = Sets.newHashSet(s.split(","));
    System.out.println(set);
  }
}
