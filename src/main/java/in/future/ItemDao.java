package in.future;

import com.google.common.collect.ImmutableList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class ItemDao {

  public CompletionStage<Object> findItemByChildCategory(int id) {
    return null;
  }

  public CompletionStage<Stream<Category>> findParentCategoriesByShop(int shopId) {
    return CompletableFuture.completedFuture(ImmutableList.of(new Category(1)).stream());
  }
}
