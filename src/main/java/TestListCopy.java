import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TestListCopy {
  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(6);

    List list1 = list.stream().filter(num -> num < 4).collect(Collectors.toList());
    list.removeAll(list1);
    System.out.println(list);

    List<Integer> list2 = new ArrayList<>();

    System.out.println("test"+
    testListReturn());
  }

  private static List<JSONObject> testListReturn() {
    List<Integer> list2 = new ArrayList<>();
    return list2
        .stream()
        .map(
            txn -> {
              return new JSONObject("{\"test\",\"test\"}");
            })
        .collect(toList());
  }
}
