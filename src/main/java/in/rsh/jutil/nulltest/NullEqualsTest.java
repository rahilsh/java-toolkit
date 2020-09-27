package in.rsh.jutil.nulltest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NullEqualsTest {
  public static void main(String[] args) {
    System.out.println("test".equals(null));

    String s=null;
    Map<String,String> map= new HashMap();
    Cache<String, String> cache =CacheBuilder
        .newBuilder().maximumSize(1000).expireAfterAccess(15, TimeUnit.MINUTES).build();
    System.out.println(
    cache.getIfPresent(s));
  }
}
