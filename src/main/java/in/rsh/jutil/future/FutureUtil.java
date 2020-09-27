package in.rsh.jutil.future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class FutureUtil {

  public static <T> CompletableFuture<List<T>> resultsOfAllFutures(
      List<CompletionStage<T>> completionStages) {
    return CompletableFuture.completedFuture(null)
        .thenApply(
            __ ->
                completionStages
                    .stream()
                    .map(future -> future.toCompletableFuture().join())
                    .collect(Collectors.toList()));
  }
}
