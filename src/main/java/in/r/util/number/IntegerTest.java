package in.r.util.number;

public class IntegerTest {
  public static void main(String[] args) {
    Integer i = new Integer(13);
    System.out.println(
    i.equals(new Long(13).intValue()));
  }
}
