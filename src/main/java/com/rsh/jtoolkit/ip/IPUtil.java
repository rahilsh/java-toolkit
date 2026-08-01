package com.rsh.jtoolkit.ip;

import com.google.common.base.Strings;
import java.util.stream.Stream;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

public class IPUtil {

  private IPUtil() {}

  /**
   * Returns {@code true} only if every entry in {@code ips} is a non-empty address that falls
   * within {@code cidrRange}. A {@code null} or empty entry is treated as out of range.
   */
  public static boolean areIPsInRange(String cidrRange, String[] ips) {
    SubnetUtils.SubnetInfo info = new SubnetUtils(cidrRange).getInfo();
    return Stream.of(ips).noneMatch(ip -> Strings.isNullOrEmpty(ip) || !info.isInRange(ip.trim()));
  }

  private static boolean isValidIP(String ipV4Address) {
    if (Strings.isNullOrEmpty(ipV4Address)) {
      return false;
    }
    InetAddressValidator validator = InetAddressValidator.getInstance();
    return validator.isValidInet4Address(ipV4Address.trim());
  }

  public static boolean areIPsValid(String[] ips) {
    for (String ip : ips) {
      if (!isValidIP(ip)) {
        return false;
      }
    }
    return true;
  }
}
