package com.rsh.jtoolkit.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListUtilTest {

  @Test
  void testMin() {
    List<Integer> list = Arrays.asList(5, 3, 1, 4, 2);
    assertEquals(1, ListUtil.min(list));
  }

  @Test
  void testMinEmpty() {
    List<Integer> list = Arrays.asList();
    assertEquals(0, ListUtil.min(list));
  }

  @Test
  void testMinSingleElement() {
    List<Integer> list = Arrays.asList(42);
    assertEquals(42, ListUtil.min(list));
  }

  @Test
  void testMinNegative() {
    List<Integer> list = Arrays.asList(-5, -3, -1, -4, -2);
    assertEquals(-5, ListUtil.min(list));
  }

  @Test
  void testMax() {
    List<Integer> list = Arrays.asList(5, 3, 1, 4, 2);
    assertEquals(5, ListUtil.max(list));
  }

  @Test
  void testMaxEmpty() {
    List<Integer> list = Arrays.asList();
    assertEquals(0, ListUtil.max(list));
  }

  @Test
  void testMaxSingleElement() {
    List<Integer> list = Arrays.asList(42);
    assertEquals(42, ListUtil.max(list));
  }

  @Test
  void testMaxNegative() {
    List<Integer> list = Arrays.asList(-5, -3, -1, -4, -2);
    assertEquals(-1, ListUtil.max(list));
  }
}
