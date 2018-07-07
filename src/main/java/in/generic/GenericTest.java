package in.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GenericTest {
  public static void main(String[] args) {
    test(
        (list) -> {
          list.add(new Integer(1));
        });
      test(
              (list) -> {
                  list.add(new Long(2L));
              });
  }

  private static void test(Consumer<List<Number>> consumer) {
    List<Number> list = new ArrayList<>();
    consumer.accept(list);
  }
}
