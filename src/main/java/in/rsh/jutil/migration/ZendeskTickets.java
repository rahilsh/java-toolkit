package in.rsh.jutil.migration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import in.rsh.jutil.clients.HttpClient;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ZendeskTickets {
  public static void main(String[] args) throws IOException {
    int count = 1;
    Gson gson = new Gson();
    HttpClient httpClient = new HttpClient();
    Map<Long, Long> map = new HashMap<>();
    try (Reader reader =
            Files.newBufferedReader(Paths.get("/Users/rahil.r/Documents/zendesk.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
      for (CSVRecord csvRecord : csvParser) {
        try {
          if (csvRecord.get(9).equals("-") || csvRecord.get(9).isEmpty()) {
            System.out.printf("Processing %s no. %s ", csvRecord.get(0), count);
            Response response =
                httpClient.get(
                    "https://mydomain.zendesk.com/api/v2/requests/" + csvRecord.get(0) + ".json",
                    Headers.of(
                        "Authorization", "Basic cmFoaWxyQHpldGEudGVjaDpaZXRhWmVuZGVza0AxNDUw"));
            JsonObject ticket = gson.fromJson(response.body().string(), JsonObject.class);
            long userId = ticket.get("request").getAsJsonObject().get("requester_id").getAsLong();
            long ticketId = ticket.get("request").getAsJsonObject().get("id").getAsLong();
            System.out.println("UserId: " + userId);
            if (map.containsKey(userId)) {
              System.out.println("Already Exists");
            } else {
              map.put(userId, ticketId);
            }
            count++;
          }
        } catch (Exception e) {
          System.out.println("Error while processing ticket: " + csvRecord.get(0));
          e.printStackTrace();
        }
      }
    }
    System.out.println(gson.toJson(map));
  }
}
