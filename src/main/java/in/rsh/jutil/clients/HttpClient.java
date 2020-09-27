package in.rsh.jutil.clients;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import net.sf.jasperreports.engine.util.JRStyledText.Run;
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

  public Response get(String url, Headers headers)  {
    Request request = new Request.Builder().url(url).get().headers(headers).build();
    try {
      return okHttpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public JSONObject put(String url, String message, Map<String, String> headers)
      throws UnirestException {
    HttpResponse<JsonNode> jsonResponse = Unirest.put(url).headers(headers).body(message).asJson();
    return jsonResponse.getBody().getObject();
  }

  private static Void fireAndForget(String url, String body, Map<String, String> headers)
      throws ExecutionException, InterruptedException {
    return CompletableFuture.completedFuture(null)
        .thenAccept(
            __ -> {
              try {
                Unirest.post(url).headers(headers).body(body).asString();
              } catch (UnirestException e) {
                e.printStackTrace();
              }
            })
        .get();
  }
}
