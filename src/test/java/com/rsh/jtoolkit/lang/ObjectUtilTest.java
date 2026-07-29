package com.rsh.jtoolkit.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ObjectUtilTest {

  @Test
  void returnsFirstNonNull() {
    assertEquals("b", ObjectUtil.firstNonNull(null, "b", "c"));
    assertEquals("a", ObjectUtil.firstNonNull("a", null));
  }

  @Test
  void returnsNullWhenAllNull() {
    assertNull(ObjectUtil.firstNonNull((Object) null, null));
  }

  @Test
  void returnsNullForNullArray() {
    assertNull(ObjectUtil.firstNonNull((Object[]) null));
  }
}
