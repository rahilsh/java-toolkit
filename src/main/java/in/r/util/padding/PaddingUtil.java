package in.r.util.padding;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class PaddingUtil {

  public static void main(String[] args) {
    String uniqueID = "10715";
    checkArgument(uniqueID.length() < 8);
    for (int i = 0; i < 20; i++) {
      System.out.println(
          55
              + StringUtils.leftPad(uniqueID, 8, '0')
              + StringUtils.leftPad(
                  String.valueOf(RandomUtils.nextInt(99)), String.valueOf(99).length(), '0'));
    }
  }
}
