package in.r.util.collection;

import com.google.common.collect.Sets;
import java.util.Set;

public class SetUtil {

  public static Set<String> stringToSet(String input, String delimiter) {
    return Sets.newHashSet(input.split(delimiter));
  }
}
