package in.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessListFaster {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    int noOfThreads = 10;

    ExecutorService exec = Executors.newFixedThreadPool(noOfThreads);
    List<Item> items = getItems();
    long starTime = System.currentTimeMillis();
   // items.parallelStream().forEach(i -> System.out.println(i.name));

    int minItemsPerThread = items.size() / noOfThreads;
    int maxItemsPerThread = minItemsPerThread + 1;
    int threadsWithMaxItems = items.size() - noOfThreads * minItemsPerThread;
    int start = 0;
    List<CompletionStage<?>> futures = new ArrayList<CompletionStage<?>>(items.size());
    for (int i = 0; i < noOfThreads; i++) {
      int itemsCount = (i < threadsWithMaxItems ? maxItemsPerThread : minItemsPerThread);
      int end = start + itemsCount;
      int finalStart = start;

      int finalI = i;
      CompletableFuture.runAsync(
              () -> {
                for (int j = 0; j < 100000; j++) {
                  for (int k = 0; k < 100000; k++) {
                    for (int l = 0; l < 10; l++) {
                      // System.out.println(item.name);
                    }
                  }
                }
                System.out.println("i=" + finalI);
              },
              exec)
          .toCompletableFuture()
          .join();

//      futures.add(
//          CompletableFuture.runAsync(
//              () -> {
//                for (int j = 0; j < 100000; j++) {
//                  for (int k = 0; k < 100000; k++) {
//                    for (int l = 0; l < 10; l++) {
//                      // System.out.println(item.name);
//                    }
//                  }
//                }
//                System.out.println("i=" + finalI);
//              },
//              exec));
      start = end;
    }
//    for (CompletionStage<?> f : futures) {
//      f.toCompletableFuture().join();
//    }
    System.out.println("all items processed in " + (System.currentTimeMillis() - starTime));
    exec.shutdown();
  }

  private static List<Item> getItems() {
    List<Item> items = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
      items.add(new Item("name_" + i));
    }
    return items;
  }
}
// uneccesary