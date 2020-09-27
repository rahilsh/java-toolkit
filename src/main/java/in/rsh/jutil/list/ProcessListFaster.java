package in.rsh.jutil.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.AllArgsConstructor;

public class ProcessListFaster {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    int noOfThreads = 10;

    List<Item> items = getItems();
    ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
    long starTime = System.currentTimeMillis();
    // items.parallelStream().forEach(i -> System.out.println(i.name));

    Worker[] workers = new Worker[noOfThreads];

    int range = items.size() / 10;

    for (int index = 0; index < 10; index++) {
      int startAt = index * range;
      int endAt = startAt + range;
      workers[index] = new Worker(items.subList(startAt, endAt));
    }

    try {
      List<Future<Object>> futures = executor.invokeAll(Arrays.asList(workers));
      for (Future<Object> future : futures) {
        future.get();
      }
    } catch (InterruptedException | ExecutionException ex) {
      ex.printStackTrace();
    }

    System.out.println("all items processed in " + (System.currentTimeMillis() - starTime));
    executor.shutdown();
  }

  private static List<Item> getItems() {
    List<Item> items = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
      items.add(new Item("name_" + i));
    }
    return items;
  }

  public static class Worker implements Callable<Object> {

    private List<Item> list;

    public Worker(List<Item> list) {
      this.list = list;
    }

    @Override
    public Object call() throws Exception {
      for (Item l : list) {
        System.out.println(l.name);
      }
      return null;
    }
  }

  @AllArgsConstructor
  private static class Item {
    String name;
  }
}
