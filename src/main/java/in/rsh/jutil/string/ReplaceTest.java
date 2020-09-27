package in.rsh.jutil.string;

public class ReplaceTest {

  public static void main(String[] args) {
    String s = "{\"endDate\": [\"'1533752999000'\"], \"startDate\": [\"'1530642600000'\"], \"fundingAccountIDs\": [\"'6587097243022268681'\"], \"fundingAccountNames\": [\"'RTech Funding Account'\"]}";

    System.out.println(
    s.replace("'",""));
  }
}
