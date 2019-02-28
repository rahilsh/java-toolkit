package in.r.util.ip;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class ValidateIP {

  public static void main(String[] args) {
    String[] s = {"0.0.0.0"};

    System.out.println(IPUtil.isIPInRange("183.82.19.162/30", s));
    // System.out.println(s);

    String ss = null;
    // System.out.println("null".equals(new Gson().toJson(ss)));

    //    List<String> o = new Gson().fromJson(ss, new TypeToken<List<String>>() {
    //    }.getType());
    //    System.out.println(o ==null);

    Map<String, String> map = new HashMap<>();
    map.put("a", "b");
    System.out.println(new Gson().toJson(null));
  }
}
