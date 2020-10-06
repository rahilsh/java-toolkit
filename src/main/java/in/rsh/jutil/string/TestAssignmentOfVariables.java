package in.rsh.jutil.string;

import org.apache.commons.lang3.StringUtils;

public class TestAssignmentOfVariables {
  public static void main(String[] args) {

    String remarks = "Towards Payout 3790574 of Order 11043";
    String payoutID = remarks.split(" ")[2];
    if (remarks != null
        && remarks.length() > 0
        && remarks.contains("Towards Payout")
        && StringUtils.isNumeric(payoutID)) {
      System.out.println("payoutID=" + payoutID);
    } else {
      payoutID = "12345";
      if (payoutID != null && StringUtils.isNumeric(payoutID)) {
        System.out.println(Long.parseLong(payoutID));
      }
    }
  }
}
