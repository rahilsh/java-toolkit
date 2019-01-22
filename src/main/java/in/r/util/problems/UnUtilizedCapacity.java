package in.r.util.problems;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UnUtilizedCapacity {

  public static void main(String[] args) {

//    ImmutableList<DockDetails> dockDetails =
//        ImmutableList.of(
//            new DockDetails(1, new Date(), new Date(), 183),
//            new DockDetails(1, new Date(), new Date(), 202),
//            new DockDetails(2, new Date(), new Date(), 210));
//    Map<Integer, Integer> soMap =
//        dockDetails
//            .stream()
//            .collect(
//                Collectors.toMap(
//                    DockDetails::getDocId,
//                    DockDetails::getCapacity,
//                    (oldVal, newVal) -> oldVal + newVal));
//    System.out.println(soMap);
//
//    //
//    // cardProgramWrappers.stream().collect(Collectors.groupingBy(CardProgramWrapper::getCompanyID,
//    //        Collectors.mapping((cardProgramWrapper) ->
//    //            new CardProgramSummary(ProductType.valueOf(cardProgramWrapper.getProductType()),
//    //                cardProgramWrapper.getId()), toList())));
//
//    ImmutableList<PurchaseOrder> purchaseOrders =
//        ImmutableList.of(
//            new PurchaseOrder(195718, 10039826, 180),
//            new PurchaseOrder(195718, 10039821, 140),
//            new PurchaseOrder(195716, 10001085, 163));
//    Map<Integer, Integer> poMap =
//        purchaseOrders
//            .stream()
//            .collect(
//                Collectors.toMap(
//                    PurchaseOrder::getPoId,
//                    PurchaseOrder::getQuantity,
//                    (oldVal, newVal) -> oldVal + newVal));
//    System.out.println(poMap);
//    Integer[] poValues =
//        poMap
//            .values()
//            .stream()
//            .map(a -> new Integer(a))
//            .collect(Collectors.toList())
//            .toArray(new Integer[poMap.values().size()]);
//    Arrays.sort(poValues);
//    for (Integer so : soMap.values()) {
//      System.out.println(atMostSum(poValues, poMap.values().size(), so));
//    }
  }
}
