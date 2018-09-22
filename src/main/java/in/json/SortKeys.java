package in.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SortKeys {

  public static void main(String[] args) {
    try {
      Test t = new Test();
      t.setName("anmea");
      t.setId(1);

      t.setMap(ImmutableMap.of("testKe","testVa"));
      System.out.println(new Gson().toJson(t).hashCode());
      Set<String> MyTreeSet = new TreeSet<>();
      MyTreeSet.add("SPAIN");
      MyTreeSet.add("ZTAIWNA");
      MyTreeSet.add("INDIA");
      MyTreeSet.add("JAPAN");

      System.out.println(MyTreeSet);
      Iterator<String> it = MyTreeSet.iterator();
      JsonObject gsonObj = new JsonObject();
      JSONObject jsonObj = new JSONObject();
      while (it.hasNext()) {
        String country = it.next();
        System.out.println("----country" + country);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(country);
        jsonArray.put("this");

        jsonObj.put(country, jsonArray);

        JsonArray gsonArray = new JsonArray();

        gsonArray.add(new JsonPrimitive(country));
        gsonArray.add(new JsonPrimitive("aaaaa"));
        gsonArray.add(new JsonPrimitive("this"));

        gsonObj.add(country, gsonArray);
      }
      System.out.println(gsonObj.hashCode());
      System.out.println(jsonObj.hashCode());


      List<String> mylist = new ArrayList<>();
      mylist.add("SPAIN");
      mylist.add("ZTAIWNA");
      mylist.add("INDIA");
      mylist.add("JAPAN");

      System.out.println(mylist);
      Iterator<String> it1 = mylist.iterator();
      JsonObject jsonObject1 = new JsonObject();
      JSONObject jsonObj1 = new JSONObject();
      while (it1.hasNext()) {
        String country = it1.next();
        System.out.println("----country" + country);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(country);
        jsonArray.put("this");

        jsonObj1.put(country, jsonArray);

        JsonArray gsonArray = new JsonArray();

        gsonArray.add(new JsonPrimitive(country));
        gsonArray.add(new JsonPrimitive("aaaaa"));
        gsonArray.add(new JsonPrimitive("this"));

        jsonObject1.add(country, gsonArray);
      }
      System.out.println(jsonObject1.hashCode());
      System.out.println(jsonObj1.hashCode());

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Getter
  @Setter
  public static class Test {
    private String name;
    private int id;
    private Map<String,String> map;
  }
}
