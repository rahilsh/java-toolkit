package in.future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class LoopingFutures {

  public static ItemDao itemDao = null;
  public static ItemDao parentCategoryDao = null;

  public static void main(String[] args) {
    retrieveItems(1);
  }

  public static CompletionStage<List<Object>> retrieveItems(final int shopId) {
    return parentCategoryDao
        .findParentCategoriesByShop(shopId)
        .thenComposeAsync(
            stream ->
                resultsOfAllFutures(
                    stream
                        .map(
                            parentCategory ->
                                itemDao
                                    .findItemByChildCategory(parentCategory.getId())
                                    .thenApplyAsync(
                                        itemStream -> {
                                          // Do operations on forming a list of items
                                          return null;
                                        }))
                        .collect(Collectors.toList())));
  }

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

  //  public CompletionStage<List<ProcessClass>> retrieveItems(final int shopId) {
  //
  //
  //    parentCategoryDao.findParentCategoriesByShop(shopId).thenApplyAsync(parentCategoryStream ->{
  //
  //      ParentCategoryJson parentCategoryJson = new ParentCategoryJson();
  //
  //      for(ParentCategory parentCategory : parentCategoryStream.collect(Collectors.toList())) {
  //
  //        processClassJson.setProcessClassId(parentCategory.getId());
  //        processClassJson.setProcessClassName(processClass.getProcessClass());
  //
  //        itemDao.findItemByChildCategory(parentCategory.getId()).thenApplyAsync(itemStream ->{
  //          // Do operations on forming a list of items
  //
  //        }, ec.current());
  //
  //
  //        //then maybe after is something like
  //        processClassJson.setItemList(itemList);
  //
  //      }
  //
  //
  //    },ec.current())
  //
  //  }
}
