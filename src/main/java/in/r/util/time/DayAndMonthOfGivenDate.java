package in.r.util.time;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayAndMonthOfGivenDate {
  public static void main(String[] args) throws ParseException {
    String lastBillUploadDate = "2019-08-05T01:00:00.000Z";
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Date date = df1.parse(lastBillUploadDate);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int day = cal.get(Calendar.DATE);
    int month = cal.get(Calendar.MONTH) + 1;
    JsonObject period = new JsonObject();
    period.addProperty("periodStartDDMM", day + "-" + month);
    System.out.println(period);
  }
}
