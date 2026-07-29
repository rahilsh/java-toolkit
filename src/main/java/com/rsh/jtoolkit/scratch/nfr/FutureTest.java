package com.rsh.jtoolkit.scratch.nfr;

public class FutureTest {

  /*

  @GetMapping(value = "/tests")
  public DeferredResult<String> tests() {
    return getDeferredResult(test());
  }

  @GetMapping(value = "/tests1")
  public String tests1() {
    System.out.println("tests1:" + Thread.currentThread().getName());
    for (int i = 1; i < 1000000000; i++) {
      for (int j = 1; j < 1000000000; j++) {
        for (int k = 1; k < 1000000000; k++) {}
      }
    }
    return "a";
  }

  @GetMapping(value = "/tests2")
  public String tests2() {
    System.out.println("tests2:" + Thread.currentThread().getName());
    return "c";
  }

    private CompletableFuture<String> test() {
    System.out.println("1:" + Thread.currentThread().getName());
    return CompletableFuture.supplyAsync(
            () -> {
              for (int i = 1; i < 1000000000; i++) {
                for (int j = 1; j < 4; j++) {}
              }
              System.out.println("2:" + Thread.currentThread().getName());
              return "test";
            })
        .thenAccept(__ -> System.out.println("3:" + Thread.currentThread().getName()))
        .thenApply(
            __ -> {
              System.out.println("4:" + Thread.currentThread().getName());
              return "test";
            });
  }
   */

}
