package in.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.io.FileUtils;

public class ReadJsonFile {
  public static void main(String[] args) {
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader("/Users/rahil.r/Documents/metadata.json"));
      JsonParser parser = new JsonParser();
      Gson gson = new Gson();
      JsonObject o = parser.parse(reader).getAsJsonObject();
      JsonArray array = o.get("docGroupsInfos").getAsJsonArray();
      array.forEach(
          a ->
              a.getAsJsonObject().get("attrs").getAsJsonObject()
                  .get("billUrls")
                  .getAsJsonArray()
                  .forEach(
                      bill -> {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request =
                            new okhttp3.Request.Builder().url(bill.getAsString()).get().build();
                        try {
                          String name =System.currentTimeMillis()+".jpg";
                          System.out.println("downloading :"+name+" || "+a.getAsJsonObject().get("docGroupID"));
                          FileUtils.copyInputStreamToFile(
                              okHttpClient.newCall(request).execute().body().byteStream(),
                              new File(
                                  "/Users/rahil.r/Documents/temp/test"
                                      + "/"
                                      +name ));
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
