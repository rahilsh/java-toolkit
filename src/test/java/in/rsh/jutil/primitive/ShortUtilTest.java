package in.rsh.jutil.primitive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShortUtilTest {

  @Test
  void testGetReversedBytes() {
    short value = (short) 0x1234;
    short result = ShortUtil.getReversedBytes(value);
    assertEquals((short) 0x3412, result);
  }

  @Test
  void testGetReversedBytesZero() {
    short value = 0;
    assertEquals((short) 0, ShortUtil.getReversedBytes(value));
  }

  @Test
  void testGetReversedBytesMax() {
    short value = Short.MAX_VALUE;
    short result = ShortUtil.getReversedBytes(value);
    assertEquals(Short.reverseBytes(value), result);
  }
}
