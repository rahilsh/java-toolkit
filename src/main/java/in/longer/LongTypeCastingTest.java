package in.longer;

public class LongTypeCastingTest {

  public static void main(String[] args) {
    long l = 0;
    Long a = 1L;
    System.out.println(testLong(a,l));
  }

  private static boolean testLong(Long cardID, long userID) {
    return ((cardID == null || cardID == 0L )&& userID == 0);
  }
}
