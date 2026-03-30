package in.rsh.jutil.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JsonUtilTest {

  @Test
  void testMapToJson() {
    Map<String, String> map = Map.of("key1", "value1", "key2", "value2");
    JsonObject result = JsonUtil.mapToJson(map);
    assertNotNull(result);
    assertEquals("value1", result.get("key1").getAsString());
    assertEquals("value2", result.get("key2").getAsString());
  }

  @Test
  void testMapToJsonEmpty() {
    Map<String, String> map = Map.of();
    JsonObject result = JsonUtil.mapToJson(map);
    assertNotNull(result);
  }

  @Test
  void testJsonToMap() {
    // JsonToMap expects arrays as values, not strings
    com.google.gson.Gson gson = new com.google.gson.Gson();
    JsonObject json = new JsonObject();
    json.add("a", gson.toJsonTree(Arrays.asList("1")));
    json.add("b", gson.toJsonTree(Arrays.asList("2")));
    Map<String, List<String>> result = JsonUtil.JsonToMap(json);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("1", result.get("a").get(0));
  }

  @Test
  void testJsonToMapEmpty() {
    JsonObject json = new JsonObject();
    Map<String, List<String>> result = JsonUtil.JsonToMap(json);
    assertNotNull(result);
    assertEquals(0, result.size());
  }
}
