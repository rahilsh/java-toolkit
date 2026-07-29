package com.rsh.jtoolkit.clients;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Objects;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Thin, dependency-injection friendly wrapper around {@link OkHttpClient}.
 *
 * <p>The underlying {@link OkHttpClient} is supplied by the caller instead of being created
 * internally. This lets an application configure and share a single client (connection pool,
 * timeouts, interceptors, dispatcher) across all collaborators, and lets tests inject a mocked or
 * {@code MockWebServer}-backed client.
 *
 * <pre>{@code
 * OkHttpClient okHttp = new OkHttpClient.Builder()
 *     .connectTimeout(Duration.ofSeconds(10))
 *     .readTimeout(Duration.ofSeconds(30))
 *     .build();
 * HttpClient httpClient = new HttpClient(okHttp);
 * try (Response response = httpClient.get("https://example.com", Map.of())) {
 *   ...
 * }
 * }</pre>
 *
 * <p>Every method returns the raw {@link Response}; the caller is responsible for closing it
 * (for example with try-with-resources).
 */
public class HttpClient {

  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  private final OkHttpClient client;

  /**
   * Creates a client backed by the supplied {@link OkHttpClient}.
   *
   * @param client the OkHttp client to delegate to; must not be {@code null}
   */
  public HttpClient(OkHttpClient client) {
    this.client = Objects.requireNonNull(client, "client must not be null");
  }

  /** Performs a GET request. */
  public Response get(String url, Map<String, String> headers) {
    return execute(requestBuilder(url, headers).get().build());
  }

  /** Performs a POST request with a JSON body. */
  public Response post(String url, String body, Map<String, String> headers) {
    return execute(requestBuilder(url, headers).post(jsonBody(body)).build());
  }

  /** Performs a PUT request with a JSON body. */
  public Response put(String url, String body, Map<String, String> headers) {
    return execute(requestBuilder(url, headers).put(jsonBody(body)).build());
  }

  /** Performs a DELETE request. */
  public Response delete(String url, Map<String, String> headers) {
    return execute(requestBuilder(url, headers).delete().build());
  }

  private Request.Builder requestBuilder(String url, Map<String, String> headers) {
    Request.Builder builder = new Request.Builder().url(url);
    if (headers != null && !headers.isEmpty()) {
      builder.headers(Headers.of(headers));
    }
    return builder;
  }

  private RequestBody jsonBody(String body) {
    return RequestBody.create(body == null ? "" : body, JSON);
  }

  private Response execute(Request request) {
    try {
      return client.newCall(request).execute();
    } catch (IOException e) {
      throw new UncheckedIOException("HTTP call failed: " + request.method() + " " + request.url(), e);
    }
  }
}
