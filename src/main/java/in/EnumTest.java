package in;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EnumTest {

  public enum CorpProductType {
    OPTIMA,
    SPOTLIGHT,
    EXPENSE,
    EXPRESS
  }

  public static void main(String[] args) {
    System.out.println(CorpProductType.EXPENSE.equals(CorpProductType.EXPENSE));
    System.out.println(
        new Gson()
            .fromJson(
                "{\\\"yearlyEligibility\\\":60000,\\\"companySpecificData\\\":{},\\\"pan\\\":\\\"BBBAB8634B\\\"}",
                JsonObject.class));
  }
}
