package in.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FutureTest {

  public static void main(String[] args) {
    //    //    CompletableFuture.completedFuture(null)
    //    //        .thenCompose(__ -> throwException())
    //    throwException()
    //        .exceptionally(
    //            e -> {
    //              System.out.println("Exceptionally");
    //              return null;
    //            });
    //  }
    //
    //  private static CompletionStage<Object> throwException() {
    //    if ("test".equals("test1")) {
    //      throw new RuntimeException("thrown");
    //    }
    //    return CompletableFuture.completedFuture(null)
    //        .thenCompose(
    //            __ -> {
    //              if ("test".equals("test")) {
    //                throw new RuntimeException("thrown");
    //              }
    //              return null;
    //            });
    //  }

  }

  CompletionStage<String> callingAsyncFunction(int developerId) {
    return getManagerIdByDeveloperId(developerId)
        .thenCompose(id -> getManagerById(id, mandatoryComputationToGetManager()));
  }

  private CompletionStage<String> getManagerById(
      Integer id, CompletionStage<String> stringCompletionStage) {
    return stringCompletionStage.thenApply(__ -> "test");
  }

  private CompletionStage<String> mandatoryComputationToGetManager() {
    return CompletableFuture.completedFuture("test");
  }

  private CompletionStage<Integer> getManagerIdByDeveloperId(int developerId) {
    return CompletableFuture.completedFuture(1);
  }




}
