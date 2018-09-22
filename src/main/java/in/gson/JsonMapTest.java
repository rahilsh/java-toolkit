package in.gson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class JsonMapTest {
  public static void main(String[] args) {
    Map<String, String> map = new HashMap<>();
    map.put("a", "bb");
    map.put("c", "dd");
    Gson gson = new Gson();
    System.out.println(gson.toJson(map));
    System.out.println(map);

    JsonObject json = gson.fromJson(gson.toJson(map), JsonObject.class);
    json.remove("k");
    System.out.println(json);
    System.out.println(map);
  }
}
