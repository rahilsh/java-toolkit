package in.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class TestReturnNullExceptionally {

  public static void main(String[] args) {
//    if(test()==null){
//      System.out.println("returns null");
//    }
    test().thenAccept(test->System.out.println(test));
  }

  private static CompletionStage<String> test() {
    return CompletableFuture.completedFuture(null)
        .thenApply(
            __ -> {
              if ("test" == "test1") {
                return "false";
              } else {
                System.out.println("Exception");
                throw new RuntimeException("Exception");
              }
            })
        .exceptionally(t -> "abcd");
  }
}
