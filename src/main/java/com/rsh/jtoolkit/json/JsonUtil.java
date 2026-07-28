package com.rsh.jtoolkit.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

public class JsonUtil {
  public static <T, U> JsonObject mapToJson(Map<T, U> map) {
    Gson gson = new Gson();
    return gson.fromJson(gson.toJson(map), JsonObject.class);
  }

  public static Map<String, List<String>> JsonToMap(JsonObject json) {
    return new Gson().fromJson(json, new TypeToken<Map<String, List<String>>>() {}.getType());
  }
}
