package in.clients;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HttpClient {

  static OkHttpClient okHttpClient = new OkHttpClient();

  public static Response get(String url, Headers headers) {
    try {
      okhttp3.Request request =
          new okhttp3.Request.Builder().url(url).get().headers(headers).build();
      return okHttpClient.newCall(request).execute();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
