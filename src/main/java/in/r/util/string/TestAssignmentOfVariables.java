package in.r.util.string;

import org.apache.commons.lang3.StringUtils;

public class TestAssignmentOfVariables {
  public static void main(String[] args) {

    String payoutID;
    String remarks = "Towards Payout 3790574 of Order 11043";
    if (remarks != null
        && remarks.length() > 0
        && remarks.contains("Towards Payout")
        && StringUtils.isNumeric(payoutID = remarks.split(" ")[2])) {
      System.out.println("payoutID="+payoutID);
    } else {
      payoutID = "12345";
      if (payoutID != null && StringUtils.isNumeric(payoutID)) {
        System.out.println(Long.parseLong(payoutID));
      }
    }
  }
}
