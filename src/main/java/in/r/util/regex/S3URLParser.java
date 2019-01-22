package in.r.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3URLParser {

  public static void main(String[] args) {
        String S3_URL_REGEX = "https://(.+).s3(?:(.+))?.amazonaws.com/(.+)";
        String s1 =

     "https://zeta-s3-bucket-permanent-staging-report.s3.amazonaws.com/FUND_ADDITION_REQUEST_REPORT_16_September_2018_1537116669831.csv?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20180916T165110Z&X-Amz-SignedHeaders=host&X-Amz-Expires=899&X-Amz-Credential=AKIAJ6MLDWHTOEERZBTQ%2F20180916%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=e20fa3f1086f36396c00d9e84925fa96d670d64d647e8d6a7485d8b5a74a1e9f";
        String s2 =

     "https://zeta-s3-bucket-permanent-staging-report.s3-ap-southeast-1.amazonaws.com/FUND_ADDITION_REQUEST_REPORT_16_September_2018_1537116669831.csv?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20180916T165110Z&X-Amz-SignedHeaders=host&X-Amz-Expires=899&X-Amz-Credential=AKIAJ6MLDWHTOEERZBTQ%2F20180916%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=e20fa3f1086f36396c00d9e84925fa96d670d64d647e8d6a7485d8b5a74a1e9f";
        String s3 =

     "https://zeta-s3-bucket-temporary-staging-report.s3.amazonaws.com/c3140657-bf1b-40ce-96a2-151d3edd8184";

    String s4 =

        "https://zeta-s3-bucket-temporary-staging-report.s3-ap-southeast-1.amazonaws.com/c3140657-bf1b-40ce-96a2-151d3edd8184";
        Pattern pattern = Pattern.compile(S3_URL_REGEX);
        Matcher matcher = pattern.matcher(s4);
        if (matcher.find()) {
          System.out.println(matcher.group(0));
          System.out.println(matcher.group(1));
          System.out.println(matcher.group(2));
          System.out.println(matcher.group(3));
        }
    if (matcher.group(3) != null && matcher.group(3).contains("?")) {
      System.out.println(matcher.group(3).split("\\?")[0]);
    }

//    final String regex = "/https://(.+).s3(.+)?.amazonaws.com/(?:(.*))?(?:(\\?)(.*))?$//gU";
//    final String string = "https://zeta-s3-bucket-permanent-staging-report.s3.amazonaws.com/FUND_ADDITION_REQUEST_REPORT_16_September_2018_1537116669831.csv?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20180916T165110Z&X-Amz-SignedHeaders=host&X-Amz-Expires=899&X-Amz-Credential=AKIAJ6MLDWHTOEERZBTQ%2F20180916%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=e20fa3f1086f36396c00d9e84925fa96d670d64d647e8d6a7485d8b5a74a1e9f";
//
//    final Pattern pattern = Pattern.compile(regex);
//    final Matcher matcher = pattern.matcher(string);
//
//    while (matcher.find()) {
//      System.out.println("Full match: " + matcher.group(0));
//      for (int i = 1; i <= matcher.groupCount(); i++) {
//        System.out.println("Group " + i + ": " + matcher.group(i));
//      }
//    }
  }
}
