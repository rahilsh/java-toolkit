package in.regex;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
  public static void main(String[] args) {
    String id = "MV-32920-2973859-escrow-0";
    // String id = "MV-32920-2973859";

    //
    //        String[] ids=id.split("-");
    //        if()
    //        String.format("",)
    // if (Pattern.matches("MV\\-[0-9]+\\-[0-9]+",id)) {
    if (Pattern.matches(".*[M]*[V]\\-[0-9]+\\-[0-9]+-?.*", id)) {
      System.out.println("Matches");
    }

    String stringToSearch = "MV-26863-1604721-f";

    Pattern p = Pattern.compile("MV\\-[0-9]+\\-[0-9]+"); // the pattern to search for
    Matcher m = p.matcher(stringToSearch);

    // if we find a match, get the group
    if (m.find()) {
      System.out.println("pmatch");
      // we're only looking for one group, so get it
      String theGroup = m.group(0);

      // print the group out for verification
      System.out.println(theGroup);
      System.out.println(Long.parseLong(theGroup.split("-")[2])==1604721);
    }
      stringToSearch = "Four score and seven years ago our fathers ...";

      p = Pattern.compile(" (\\S+or\\S+) ");   // the pattern to search for
      m = p.matcher(stringToSearch);

      // if we find a match, get the group
      if (m.find())
      {
          // we're only looking for one group, so get it
          String theGroup = m.group(0);

          // print the group out for verification
          System.out.format("'%s'\n", theGroup);
      }
    System.out.println(exceptionTest());
      BigInteger b= new BigInteger("9223372036854775808");
      if (!(b.compareTo( new BigInteger("9223372036854775807"))==1)){
          System.out.println(
                  Long.valueOf("9223372036854775808"));
      }


    System.out.println(Long.MAX_VALUE);
    System.out.println("9223372036854775809".length());
    System.out.println(
    StringUtils.isNumeric("9223372036854775808"));

  }

  private static boolean exceptionTest() {
    try {
      String s = "s";
      return Long.parseLong(s) == 1;
    } catch (Exception e) {
      System.out.println("in catch");
    }
    System.out.println("aftertrycatch");
    return true;
  }
}
