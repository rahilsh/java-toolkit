package in.future;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FireAndForget {

  public static void main(String[] args) throws InterruptedException {
    CompletableFuture.completedFuture(null).thenAccept(__ -> someMethod());
    System.out.println("Thread: " + Thread.currentThread().getName() + ". Done !!!");
    //Thread.sleep(10000);
  }

  private static CompletionStage<String> someMethod() {

    return CompletableFuture.completedFuture(null)
        .thenAccept(
            __ -> {
              try {
                System.out.println("Thread: " + Thread.currentThread().getName() + ". Calling API");
                HttpResponse<String> json =
                    Unirest.post("https://google.co.in")
                        .header("Content-Type", "application/json")
                        .body("{\"text\":\"Test message\"}")
                        .asString();
                for (int i = 0; i < 1000000; i++) {
                  Headers headers = json.getHeaders();
                  //System.out.println(i);
                }
                System.out.println("Content Type: " + json.getHeaders().get("Content-Type"));
              } catch (UnirestException e) {
                e.printStackTrace();
              }

              System.out.println(
                  "Thread: " + Thread.currentThread().getName() + ". Inside someMethod !!!");
            })
        .thenApply(__ -> "test");
  }
}
