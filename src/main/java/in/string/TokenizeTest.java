package in.string;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TokenizeTest {

  public static void main(String[] args) throws IOException {
    List<String> list =
        Arrays.stream(ReportParamKey.values())
            .map(v -> v.value)
            .sorted()
            .collect(Collectors.toList());
    Set<String> set = new TreeSet<>();
    BufferedReader TSVFile =
        new BufferedReader(new FileReader("/Users/rahil.r/Documents/report_archive_prod.tsv"));
    String dataRow = TSVFile.readLine(); // Read first line.
    while (dataRow != null) {
      JsonObject jsonObject = new JsonObject();
      String[] line = dataRow.split("\t");
      String s = line[1];
      for (int i = 0; i < list.size(); i++) {
        // System.out.println("Processing: " + list.get(i));
        String fs = "";
        if (s.indexOf(list.get(i)) >= 0) {
          set.add(list.get(i));
          int nextKey = getNextKey(s.indexOf(list.get(i)), s, list, i);
          // System.out.println(
          //  "indexOflistget: " + s.indexOf(list.get(i)) + ". Next key: " + nextKey);
          if (nextKey != 0) {
            fs = s.substring(s.indexOf(list.get(i)), nextKey);
          } else {
            fs = s.substring(s.indexOf(list.get(i)), s.length());
          }
          // System.out.println(fs);
          if (ImmutableList.of("cardProgramIDToCompanyNameMap").contains(list.get(i))) {
            jsonObject.add(getReportKey(list, i), getJsonObject(fs));
          } else if (ImmutableList.of("employeeIDToUserIDMap", "userIDToEmployeeIDMap")
              .contains(list.get(i))) {
            jsonObject.add(getReportKey(list, i), getJsonObjectofLong(fs));
          } else if (ImmutableList.of(
                      "cardProgramIDs", "closedCardProgramIDs", "fundingAccountNames", "endDate")
                  .contains(list.get(i))
              || ImmutableList.of("startDate").contains(list.get(i))) {
            jsonObject.add(getReportKey(list, i), getJsonArray(fs));
          } else if (ImmutableList.of("cardIDs", "companyIDs", "employeeIDs", "programIDs")
                  .contains(list.get(i))
              || ImmutableList.builder()
                  .add("closeCardOrderID")
                  .add("companyID")
                  .add("corporateID")
                  .add("orderID")
                  .add("fundingAccountIDs")
                  .build()
                  .contains(list.get(i))) {
            // System.out.println(fs);
            jsonObject.add(getReportKey(list, i), getJsonArrayInt(fs));
          } else if (list.get(i).equalsIgnoreCase("ledgerIDs")) {

          } else {
            jsonObject.addProperty(
                getReportKey(list, i),
                fs.substring(fs.indexOf(":") + 1).replace("'", "").replace(",", "").trim());
          }
        }
      }
      System.out.println(
          "update report_archive set user_parameters='"
              + jsonObject.toString()
              + "' where id = "
              + line[0]
              + ";");
      dataRow = TSVFile.readLine();
    }
    System.out.println(set);
  }

  private static String getReportKey(List<String> list, int i) {
    return ReportParamKey.getByValue(list.get(i)).toString();
  }

  private static JsonElement getJsonObjectofLong(String fs) {
    String[] tokens = fs.substring(fs.indexOf(":") + 1).trim().split(",");
    JsonObject jsonObject = new JsonObject();
    for (int i = 0; i < tokens.length; i += 2) {
      if (tokens.length > 1) {
        String value = sanitize(tokens[i + 1]);
        Long valuel;
        try {
          valuel = Long.parseLong(value);
          jsonObject.addProperty(sanitize(tokens[i]), valuel);
        } catch (Exception e) {
          jsonObject.addProperty(sanitize(tokens[i]), value);
        }
      }
    }
    return jsonObject;
  }

  private static JsonElement getJsonArrayInt(String fs) {
    JsonArray array = new JsonArray();

    StringTokenizer st =
        new StringTokenizer(fs.substring(fs.indexOf(":") + 1).replace("'", "").trim(), ",");
    while (st.hasMoreTokens()) {
      String token = st.nextToken().trim();
      try {
        Long valuel = Long.parseLong(token);
        array.add(valuel);

      } catch (Exception e) {
        array.add(token);
      }
    }
    return array;
  }

  private static JsonArray getJsonArray(String fs) {
    JsonArray array = new JsonArray();
    StringTokenizer st =
        new StringTokenizer(fs.substring(fs.indexOf(":") + 1).replace("'", "").trim(), ",");
    while (st.hasMoreTokens()) {
      array.add(st.nextToken().trim());
    }
    return array;
  }

  private static JsonElement getJsonObject(String fs) {
    String[] tokens = fs.substring(fs.indexOf(":") + 1).trim().split(",");
    JsonObject jsonObject = new JsonObject();
    for (int i = 0; i < tokens.length; i += 2) {
      if (tokens.length > 1) {
        jsonObject.addProperty(sanitize(tokens[i]), sanitize(tokens[i + 1]));
      }
    }
    return jsonObject;
  }

  private static String sanitize(String s) {
    return s.replace("(", "").replace(")", "").replace("'", "").replace("::text", "").trim();
  }

  private static int getNextKey(int k, String s, List<String> list, int p) {
    for (int i = p; i < list.size(); i++) {
      if (s.indexOf(list.get(i)) > k) {
        return s.indexOf(list.get(i));
      }
    }
    return 0;
  }

  public enum ReportParamKey {
    CORP_ID("corporateID"),
    EMPLOYEE_IDS("employeeIDs"),
    FUNDING_ACCOUNT_IDS("fundingAccountIDs"),
    CARD_PROGRAM_IDS("cardProgramIDs"),
    CLOSED_CARD_PROGRAM_IDS("closedCardProgramIDs"),
    PROGRAM_IDS("programIDs"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    ORDER_ID("orderID"),
    FUNDING_ACCOUNT_LEDGER_IDS("ledgerIDs"),
    FUNDING_ACCOUNT_NAMES("fundingAccountNames"),
    COMPANY_ID("companyID"),
    CARD_IDS("cardIDs"),
    CLOSE_CARD_ORDER_ID("closeCardOrderID"),
    USER_ID_TO_EMPLOYEE_ID_MAP("userIDToEmployeeIDMap"),
    EMPLOYEE_ID_TO_USER_ID_MAP("employeeIDToUserIDMap"),
    CORPORATE_NAME("corporateName"),
    CARD_PROGRAM_ID_TO_COMPANY_NAME_MAP("cardProgramIDToCompanyNameMap"),
    ZETA_USER_IDS("zetaUserIDs"),
    USER_ID("userIDs"),
    COMPANY_IDS("companyIDs"),
    PRODUCT_TYPE("productType"),
    FUNDING_ACCOUNT_TYPE("fundingAccountType"),
    IFI_ID("ifiID"),
    MOBILE_NUMBER("mobileNumber");

    private final String value;

    ReportParamKey(String value) {
      this.value = value;
    }

    private static final Map<String, ReportParamKey> MY_MAP = new HashMap<>();

    static {
      for (ReportParamKey myEnum : values()) {
        MY_MAP.put(myEnum.getValue(), myEnum);
      }
    }

    public String getValue() {
      return value;
    }

    public static ReportParamKey getByValue(String value) {
      return MY_MAP.get(value);
    }
  }
}
