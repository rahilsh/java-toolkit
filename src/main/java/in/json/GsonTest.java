package in.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GsonTest {
  public static void main(String[] args) {
    String amcList = "{\"default\":[\"5047\"]}";
    Map<String, List<String>> amcMap =
        new Gson().fromJson(amcList, new TypeToken<Map<String, List<String>>>() {}.getType());
    System.out.println(amcMap.values().stream().flatMap(List::stream).collect(Collectors.toList()));

    System.out.println(new Gson().toJson(null).equals("null"));
  }
}
