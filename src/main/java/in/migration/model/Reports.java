package in.migration.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reports {
  private Report[] reports;

  @Getter
  @Setter
  public static class Report {
    private String reportID;
    private String description;
    private String sampleReportURL;
  }
}
