package in.rsh.jutil.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadJsonFile {

  public static JsonObject readJsonFile(String filePath) {
    JsonReader reader;
    JsonObject json = null;
    try {
      reader = new JsonReader(new FileReader(filePath));
      JsonParser parser = new JsonParser();
      json = parser.parse(reader).getAsJsonObject();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return json;
  }
}
