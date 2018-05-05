import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestMapNPE {
  public static void main(String[] args) {
    Map<String, String> map = new HashMap<>();

    Optional<String> key = map.keySet().stream().findFirst();
    if (key.isPresent()) {
      System.out.println("present");
    } else {
      System.out.println("not present");
    }

    if("test".equals(null)){
      System.out.println("test nukk");
    }else{
      System.out.println("else");
    }

    if(TestEnum.TEST2 == TestEnum.TEST2){
      System.out.println("Equal");
    }else{
      System.out.println("not eq");
    }

  }
}
