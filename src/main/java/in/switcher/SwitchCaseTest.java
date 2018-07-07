package in.switcher;

public class SwitchCaseTest {
  public static void main(String[] args) {
    System.out.println(getString());
  }

  private static String getString() {
    switch ("cccd") {
      case "aaa":
      case "bbb":
        return "aaabbb";
      case "ccc":
        return "ccc";
      default:
        return "default";
    }
  }
}
