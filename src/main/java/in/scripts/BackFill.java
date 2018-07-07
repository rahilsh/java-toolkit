package in.scripts;

import java.util.Scanner;

public class BackFill {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    Integer corpID = s.nextInt();
    backfillCardIDForCorp(corpID);
  }

  private static void backfillCardIDForCorp(Integer corpID) {}
}
