package in.optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalTest {
  public static void main(String[] args) {
    Map<String, String> map1 = new HashMap<>();
    map1.put("name", "a");
    TestModel testModel = null;
    // TestModel testModel = new TestModel(null,null );
    System.out.println(
        Optional.ofNullable(testModel)
            .map(testModel1 -> testModel1.getMap())
            .map(map -> map.get("name"))
            .filter(name -> !name.isEmpty())
            .orElseGet(() -> "ord"));
  }
}

class TestModel {
  private final String name;
  private final Map<String, String> map;

  public TestModel(String name, Map<String, String> map) {
    this.name = name;
    this.map = map;
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getMap() {
    return map;
  }
}
