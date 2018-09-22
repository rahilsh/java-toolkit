package in.deepcopy;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;

public class DeepCopyTest {

  public static void main(String[] args) {
    final Map<String, List<Object>> parameters = new HashMap<>();
    parameters.put("test1",ImmutableList.of("testV"));
    ReportParametersInternal reportParametersInternal = ReportParametersInternal.builder().parameters(new HashMap<>(parameters))

        .build();

    reportParametersInternal.addAliasValue("test2",ImmutableList.of("testV2"));

    System.out.println(parameters);
  }

  @Builder
  public static class ReportParametersInternal {
    Map<String, List<Object>> parameters;

    public void addAliasValue(String aliasKey, List<Object> aliasValue) {
      parameters.put(aliasKey, aliasValue);
    }
  }
}
