package in.future;

import java.util.concurrent.CompletableFuture;

public class HandleException {

  public static void main(String[] args) {
    CompletableFuture.completedFuture(null)
        .thenApply(__ -> callToRestAPI())
        .thenApply(
            output1 -> {
              callToDatabase(output1);
              return output1;
            })
        .thenAccept(output1 -> callToSendEmail(output1))
        .exceptionally(
            t -> {
              System.out.println("Exception occurred: " + t.getMessage());
              return null;
            });
  }

  private static void callToSendEmail(Object output1) {}

  private static void callToDatabase(Object output1) {}

  private static Object callToRestAPI() {
    return null;
  }
}
