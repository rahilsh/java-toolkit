package in.matrix;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

public class MinMax {

  public static void main(String[] args) {
    List<Integer> list = ImmutableList.of(8, 4, 4);
    Integer min = list.stream().min(Comparator.comparing(Integer::valueOf)).get();
    Integer max = list.stream().max(Comparator.comparing(Integer::valueOf)).get();
    System.out.println(min);
    System.out.println(max);

    Map<String, List<Object>> parameters =
        ImmutableMap.of("k1", ImmutableList.of("v1","v1_1"), "k2", ImmutableList.of("v2","v2_1"));
    List<ReportField> reportFields =
        parameters
            .keySet()
            .stream()
            .map(
                reportParamKey -> {
                  return new ReportField(
                      reportParamKey,
                      ImmutableList.of(
                          parameters
                              .get(reportParamKey)
                              .stream()
                              .map(o -> o.toString())
                              .collect(Collectors.joining("','", "'", "'"))));
                })
            .collect(Collectors.toList());
    System.out.println(new Gson().toJson(reportFields));
  }

  @AllArgsConstructor
  private static class ReportField {
    private String key;
    private List<String> valueList;
  }
}
