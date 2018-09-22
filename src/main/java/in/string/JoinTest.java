package in.string;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class JoinTest {

  public static void main(String[] args) {
    List<String> a =ImmutableList.of();
    System.out.println(String.join(",",a));
  }
}
