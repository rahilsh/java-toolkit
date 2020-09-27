package in.rsh.jutil.migration;

import com.google.gson.Gson;
import in.rsh.jutil.migration.model.Reports;

public class JsonToQuery {
  public static void main(String[] args) {
    String reportsString = "{}";

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
