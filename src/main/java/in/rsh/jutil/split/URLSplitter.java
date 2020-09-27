package in.rsh.jutil.split;

public class URLSplitter {
  public static void main(String[] args) {
    String url = "https://bucket.s3.amazonaws.com/88714d0e-67f2-464a-a5b9-afd20ed769d5/";
    System.out.println(getKey(url));
  }

  private static String getKey(String url) {
    String[] urlElements = url.split("/");
    return urlElements[urlElements.length - 1];
  }
}
