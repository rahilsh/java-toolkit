package in.rsh.jutil.migration.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class Ticket {
  @CsvBindByName
  private String corpId;
  @CsvBindByName
  private long ticketId;
}
