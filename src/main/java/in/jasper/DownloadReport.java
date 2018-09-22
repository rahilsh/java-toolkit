package in.jasper;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadReport {

  public static void main(String[] args) throws IOException {
    OkHttpClient client = new OkHttpClient();

    Request request =
        new Request.Builder()
            .url(
                "http://prodaxs.corp.zeta.in:8900/jasperserver/rest_v2/reportExecutions/5d2c6ed1-3941-4943-8573-486e2dcedfe4/exports/e0bc0688-138e-441d-a247-aa8aa6ac3e3a/outputResource")
            .get()
            .addHeader(
                "Cookie", "JSESSIONID=2D489FC4BC35F56D85901C399C0365D0;path=/jasperserver;HttpOnly")
            .addHeader("Accept", "application/json")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Postman-Token", "6482fd9d-291d-4cbf-8f19-316e5d97337a")
            .build();

    Response response = client.newCall(request).execute();
    System.out.println(response.body().string());
  }
}
