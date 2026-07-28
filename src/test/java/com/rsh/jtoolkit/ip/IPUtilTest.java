package com.rsh.jtoolkit.ip;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IPUtilTest {

  @Test
  void testIPsNotInRange() {
    assertFalse(IPUtil.areIPsInRange("183.82.19.162/30", new String[] {"0.0.0.0"}));
  }

  @Test
  void testIPsInRange() {
    // First IP	183.82.19.160 exclusive
    // Last IP	183.82.19.163 exclusive
    assertTrue(IPUtil.areIPsInRange("183.82.19.162/30", new String[] {"183.82.19.161"}));
  }

  @Test
  void testValidIPs() {
    assertTrue(IPUtil.areIPsValid(new String[] {"183.82.19.161"}));
  }

  @Test
  void testInvalidIPs() {
    assertFalse(IPUtil.areIPsValid(new String[] {"283.82.19.161"}));
  }
}
