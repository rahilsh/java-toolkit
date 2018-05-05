import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
  public static void main(String[] args) {
    Map<String, String> postingAttributes = new HashMap<>();
    postingAttributes.put("a",null);
    postingAttributes.put("b","a");
    if (postingAttributes.get("c")!=null && postingAttributes.get("b").equals("a")){
      System.out.println("if");
    }else {
      System.out.println("else");
    }
//
//    String test = postingAttributes.get("asas");
//    System.out.println(postingAttributes.get("asds"));
//    if (test == null) {
//      System.out.println("Not found");
//    }
//    System.out.println(getPayoutID("Towards Payout", new HashMap<>()) == null);
//    System.out.println(getPayoutID("Towards Payout 555", new HashMap<>()));
//    System.out.println(getPayoutID("Towards Payout abc", new HashMap<>()));
//    System.out.println(
//        getPayoutID("Towards Payout 12345", ImmutableMap.of("corpben.payoutID", "99999")));
//    System.out.println(
//        getPayoutID("Towards Payout 11111", ImmutableMap.of("corpben.payoutID", "asb")));
//    System.out.println(getPayoutID("dummy", ImmutableMap.of("corpben.payoutID", "asb")) == null);
//    System.out.println(getPayoutID("dummy", ImmutableMap.of("corpben.payoutID", "66666")));
  }

  private static Long getPayoutID(String remarks, Map<String, String> postingAttributes) {
    String payoutID;
    if (!remarks.isEmpty()) {
      // Getting PayoutID from remarks for now as it is incorrect in ledger attrs
      if ((remarks.contains("Towards Payout") || remarks.contains("Revoked payout"))) {
        String[] remarksArray = remarks.split(" ");
        if (remarksArray.length > 2 && StringUtils.isNumeric(payoutID = remarksArray[2])) {
          return Long.parseLong(payoutID);
        }
      }
    }
    payoutID =
        firstNonNull(
            postingAttributes.get("corpben.payoutID"), postingAttributes.get("corpben.payout-id"));
    if (payoutID != null && !payoutID.isEmpty() && StringUtils.isNumeric(payoutID)) {
      return Long.parseLong(payoutID);
    }
    return null;
  }

  private static <T> T firstNonNull(T... items) {
    for (T item : items) {
      if (item != null) return item;
    }
    return null;
  }
}
