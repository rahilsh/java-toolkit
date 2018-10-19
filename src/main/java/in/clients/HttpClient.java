package in.clients;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class HttpClient {

  static OkHttpClient okHttpClient =
      new OkHttpClient()
          .newBuilder()
          .connectTimeout(1, TimeUnit.MINUTES)
          .readTimeout(1, TimeUnit.MINUTES)
          .build();

  public Response get(String url, Headers headers) throws IOException {
    Request request = new Request.Builder().url(url).get().headers(headers).build();
    return okHttpClient.newCall(request).execute();
  }

  public JSONObject put(String url, String message, Map<String, String> headers)
      throws IOException, UnirestException {
    HttpResponse<JsonNode> jsonResponse = Unirest.put(url).headers(headers).body(message).asJson();
    return jsonResponse.getBody().getObject();
  }
}
