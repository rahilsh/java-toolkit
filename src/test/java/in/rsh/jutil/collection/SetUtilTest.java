package in.rsh.jutil.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class SetUtilTest {

  @Test
  void testStringToSet() {
    Set<String> result = SetUtil.stringToSet("a,b,c", ",");
    assertEquals(3, result.size());
    assertTrue(result.contains("a"));
    assertTrue(result.contains("b"));
    assertTrue(result.contains("c"));
  }

  @Test
  void testStringToSetSingleElement() {
    Set<String> result = SetUtil.stringToSet("a", ",");
    assertEquals(1, result.size());
    assertTrue(result.contains("a"));
  }

  @Test
  void testStringToSetPipeDelimiter() {
    Set<String> result = SetUtil.stringToSet("x|y|z", "\\|");
    assertEquals(3, result.size());
  }
}
