package com.rsh.jtoolkit.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

/** Conversions between Java {@link Map}s and Gson {@link JsonObject}s. */
public final class JsonUtil {

  private static final Gson GSON = new Gson();

  private JsonUtil() {}

  public static <T, U> JsonObject mapToJson(Map<T, U> map) {
    return GSON.fromJson(GSON.toJson(map), JsonObject.class);
  }

  public static Map<String, List<String>> jsonToMap(JsonObject json) {
    return GSON.fromJson(json, new TypeToken<Map<String, List<String>>>() {}.getType());
  }
}
