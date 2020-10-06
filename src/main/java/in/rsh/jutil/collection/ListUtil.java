package in.rsh.jutil.collection;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ListUtil {
  public static void main(String[] args) {
    addNumberToList(list -> list.add(1), Lists.newArrayList(3L));
    addNumberToList(list -> list.add(2L), Lists.newArrayList(4L));
  }

  private static void addNumberToList(Consumer<List<Number>> consumer, List<Number> list) {
    consumer.accept(list);
  }

  public static Integer min(List<Integer> list) {
    Optional<Integer> optionalMin = list.stream().min(Comparator.comparing(Integer::valueOf));
    return optionalMin.orElse(0);
  }

  public static Integer max(List<Integer> list) {
    Optional<Integer> optionalMax = list.stream().max(Comparator.comparing(Integer::valueOf));
    return optionalMax.orElse(0);
  }
}
