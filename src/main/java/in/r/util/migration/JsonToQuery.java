package in.r.util.migration;

import com.google.gson.Gson;
import in.r.util.migration.model.Reports;

public class JsonToQuery {
  public static void main(String[] args) {
    String reportsString =
        "{\n"
            + "  \"reports\": [\n"
            + "    {\n"
            + "      \"reportID\": \"FUNDING_ACCOUNT_FUND_MOVEMENT_REPORT\",\n"
            + "      \"description\": \"Details of all debits and credits to a funding account\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Fund%20Movement%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"FUND_ADDITION_REQUEST_REPORT\",\n"
            + "      \"description\": \"Details of fund addition requests raised on a funding account\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Fund%20Addition%20Request%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"FUNDING_ACCOUNT_TRANSFER_DETAIL_REPORT\",\n"
            + "      \"description\": \"Details of payouts effected from all benefit programs linked to a funding account\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Transfer%20Detail%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"EMPLOYEE_REIMBURSEMENT_MASTER_REPORT\",\n"
            + "      \"description\": \"Summary of claims submitted, approved & rejected for a benefit program\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Reimbursement%20Master%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"BENEFICIARY_SPENDS_REPORT\",\n"
            + "      \"description\": \"Details of spends made by beneficiaries of a benefit program\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Beneficiary%20Spends%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"YEAR_END_UN_REGISTERED_USERS\",\n"
            + "      \"description\": \"Summary of disbursals to and revokes from beneficiaries who had not registered on zeta\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Year%20End%20Unregistered%20Users%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"BENEFICIARY_SPENDS_REPORT_SUMMARY\",\n"
            + "      \"description\": \"Summary of disbursed, spent, and revoked amounts for all beneficiaries\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Spends%20Summary%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"PAYOUT_REPORT\",\n"
            + "      \"description\": \"Details, including the status, of payouts made to all beneficiaries\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Payout%20Report%20Sample.xlsx\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"reportID\": \"EMPLOYEE_REIMBURSEMENT_YTD_REPORT\",\n"
            + "      \"description\": \"YTD Details of claims submitted, approved, and rejected along with reimbursed and carry forward amounts for all beneficiaries\",\n"
            + "      \"sampleReportURL\": \"https://card-program-files.s3.amazonaws.com/Report%20Center/Report%20Formats%20-%20With%20Description%20%26%20Sample%20Data/Reimbursement%20YTD%20Report%20Sample.xlsx\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    Gson gson = new Gson();
    Reports reports = gson.fromJson(reportsString, Reports.class);
    for (Reports.Report report : reports.getReports()) {
      System.out.println(
          "update report_configuration set description='"
              + report.getDescription()
              + "', sample_report_url='"
              + report.getSampleReportURL()
              + "' where report_id='"
              + report.getReportID()
              + "';");
    }
  }
}
