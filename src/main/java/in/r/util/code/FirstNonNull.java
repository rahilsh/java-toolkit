package in.r.util.code;

public class FirstNonNull {
  private static <T> T firstNonNull(T... items) {
    for (T item : items) {
      if (item != null) return item;
    }
    return null;
  }
}
