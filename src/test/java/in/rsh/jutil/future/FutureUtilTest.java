package in.rsh.jutil.future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.junit.jupiter.api.Test;

class FutureUtilTest {

  @Test
  void testResultsOfAllFutures() {
    CompletionStage<String> future1 = CompletableFuture.completedFuture("a");
    CompletionStage<String> future2 = CompletableFuture.completedFuture("b");
    CompletionStage<String> future3 = CompletableFuture.completedFuture("c");

    List<CompletionStage<String>> completionStages = Arrays.asList(future1, future2, future3);

    CompletableFuture<List<String>> resultFuture = FutureUtil.resultsOfAllFutures(completionStages);
    List<String> result = resultFuture.join();

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(Arrays.asList("a", "b", "c"), result);
  }

  @Test
  void testResultsOfAllFuturesEmpty() {
    List<CompletionStage<String>> completionStages = Arrays.asList();
    CompletableFuture<List<String>> resultFuture = FutureUtil.resultsOfAllFutures(completionStages);
    List<String> result = resultFuture.join();
    assertNotNull(result);
    assertEquals(0, result.size());
  }
}
