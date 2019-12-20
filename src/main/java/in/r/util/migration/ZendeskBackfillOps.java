package in.r.util.migration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import in.r.util.clients.HttpClient;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class ZendeskBackfillOps {
  public static void main(String[] args) throws IOException {
    backFillCorpIDs();
  }

  private static void backFillCorpIDs() throws IOException {

    int count = 0;
    Gson gson = new Gson();
    HttpClient httpClient = new HttpClient();
    try (Reader reader =
            Files.newBufferedReader(Paths.get("/Users/rahil.r/Documents/zendesk.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        BufferedWriter writer =
            Files.newBufferedWriter(Paths.get("/Users/rahil.r/Documents/zendesk_o.csv"));
        CSVPrinter csvPrinter =
            new CSVPrinter(
                writer,
                CSVFormat.DEFAULT.withHeader(
                    "ticketId",
                    "Date (Ticket Created)",
                    "Date (Ticket Last Updated)",
                    "Date (Ticket Solved)",
                    "Type",
                    "Category",
                    "Sub Category",
                    "Ticket Group",
                    "Ticket Status",
                    "corpId",
                    "Ticket Requester Domain",
                    "Z Channel",
                    "# SLA tickets",
                    "# SLAs Achieved",
                    "# SLAs Breached (Total)",
                    "% Achieved",
                    "% Breached",
                    "requester_id",
                    "email",
                    "phone")); ) {

      for (CSVRecord csvRecord : csvParser) {
        if (csvRecord.get(9).equals("-") || csvRecord.get(9).isEmpty()) {
          count++;
          Response response =
              httpClient.get(
                  "https://mydomain.zendesk.com/api/v2/requests/" + csvRecord.get(0) + ".json",
                  Headers.of(
                      "Authorization", "Basic cmFoaWxyQHpldGEudGVjaDpaZXRhWmVuZGVza0AxNDUw"));
          JsonObject ticket = gson.fromJson(response.body().string(), JsonObject.class);
          // System.out.println(gson.toJson(json));
          System.out.println(ticket.get("request").getAsJsonObject().get("requester_id"));
          long userId = ticket.get("request").getAsJsonObject().get("requester_id").getAsLong();

          // System.out.println("userId=" + userId);
          JsonObject user =
              gson.fromJson(
                  httpClient
                      .get(
                          "https://mydomain.zendesk.com/api/v2/users/" + userId + ".json",
                          Headers.of(
                              "Authorization",
                              "Basic cmFoaWxyQHpldGEudGVjaDpaZXRhWmVuZGVza0AxNDUw"))
                      .body()
                      .string(),
                  JsonObject.class);

          // System.out.println(user.get("user").getAsJsonObject().get("email"));
          String email =
              !user.get("user").getAsJsonObject().get("email").isJsonNull()
                  ? user.get("user").getAsJsonObject().get("email").getAsString()
                  : null;
          // System.out.println(user.get("user").getAsJsonObject().get("phone"));
          String phone =
              !user.get("user").getAsJsonObject().get("phone").isJsonNull()
                  ? user.get("user").getAsJsonObject().get("phone").getAsString()
                  : null;

          csvPrinter.printRecord(
              csvRecord.get(0),
              csvRecord.get(1),
              csvRecord.get(2),
              csvRecord.get(3),
              csvRecord.get(4),
              csvRecord.get(5),
              csvRecord.get(6),
              csvRecord.get(7),
              csvRecord.get(8),
              csvRecord.get(9),
              csvRecord.get(10),
              csvRecord.get(11),
              csvRecord.get(12),
              csvRecord.get(13),
              csvRecord.get(14),
              csvRecord.get(15),
              csvRecord.get(16),
              userId,
              email,
              phone);
        }
        //        if (count > 3) {
        //          break;
        //        }
      }
    }
    System.out.println(count);
  }
}
