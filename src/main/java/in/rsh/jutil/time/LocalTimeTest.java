package in.rsh.jutil.time;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;

public class LocalTimeTest {
static String a;
  public static int test(int a) {
    return a + 4;
  }

  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {{a= "a"}, {a="b"}, {a="c"}};
    return Arrays.asList(data);
  }

  public static void main(String[] args) {
    System.out.println(data());
    data().forEach(System.out::println);
    System.out.println(LocalTime.ofSecondOfDay(120));
    System.out.println(LocalTime.of(0, 2));
  }
}
