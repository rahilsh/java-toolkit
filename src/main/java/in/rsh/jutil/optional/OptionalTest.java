package in.rsh.jutil.optional;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

public class OptionalTest {
  public static void main(String[] args) {

    Map<String, String> map1 = new HashMap<>();
    map1.put("name", "a");
    TestModel testModel = new TestModel(null, null, null);
    System.out.println(
        Optional.ofNullable(testModel)
            .map(
                testModel1 -> {
                  System.out.println("testModel1=" + testModel1);
                  return testModel1.getMap();
                })
            .map(
                map -> {
                  System.out.println("map=" + map);
                  return map.get("name");
                })
            .filter(name -> !name.isEmpty())
            .orElseGet(() -> "ord"));

    List<TestModel> list = new ArrayList<>();

    list.add(new TestModel("a", "k", null));
    list.add(new TestModel("b", "k", null));
    list.add(new TestModel("c", "k", null));
    Map<String, String> map =
        list.stream().collect(Collectors.toMap(TestModel::getName, TestModel::getEmail));
    System.out.println(new Gson().toJson(map));
  }
}

class TestModel {
  private final String name;
  @Getter private final String email;
  private final Map<String, String> map;

  public TestModel(String name, String email, Map<String, String> map) {
    this.name = name;
    this.email = email;
    this.map = map;
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getMap() {
    return map;
  }
}
