package in.rsh.jutil.jasper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import in.rsh.jutil.clients.HttpClient;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Response;

@Singleton
public class ReportDownloader {

  public static final String JASPER_REPORT_EXECUTIONS_API =
      "/jasperserver/rest_v2/reportExecutions/%s/exports/%s/outputResource";
  public static final String HEADER_ACCEPT = "Accept";
  public static final String APPLICATION_JSON = "application/json";
  public static final String HEADER_COOKIE = "Cookie";
  private final String jasperHost;
  private final String jasperPassword;
  private final HttpClient httpClient;

  @Inject
  public ReportDownloader(
      @Named("jasperHost") String host,
      @Named("jasperPassword") String password,
      HttpClient httpClient) {
    this.jasperHost = host;
    this.jasperPassword = password;
    this.httpClient = httpClient;
  }

  public String downloadReport(String requestId, String exportId, String cookie)
      throws IOException {
    Response response =
        httpClient.get(
            jasperHost + String.format(JASPER_REPORT_EXECUTIONS_API, requestId, exportId),
            Headers.of(HEADER_COOKIE, cookie, HEADER_ACCEPT, APPLICATION_JSON));
    return response.body().string();
  }
}
