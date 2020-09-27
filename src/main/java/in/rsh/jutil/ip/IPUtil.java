package in.rsh.jutil.ip;

import com.google.common.base.Strings;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

public class IPUtil {

  public static boolean isIPInRange(String cidrRange, String[] ips) {
    SubnetUtils utils = new SubnetUtils(cidrRange);
    Optional<String> any =
        Stream.of(ips)
            .filter(
                ip -> {
                  if (ip != null && !ip.isEmpty()) {
                    return utils.getInfo().isInRange(ip.trim());
                  }
                  return false;
                })
            .findAny();
    return any.isPresent();
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
      if (!IPUtil.isValidIP(ip)) {
        return false;
      }
    }
    return true;
  }
}
