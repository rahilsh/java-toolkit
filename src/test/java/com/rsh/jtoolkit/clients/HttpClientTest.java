package com.rsh.jtoolkit.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HttpClientTest {

  private MockWebServer server;
  private HttpClient httpClient;

  @BeforeEach
  void setUp() throws IOException {
    server = new MockWebServer();
    server.start();
    httpClient = new HttpClient(new OkHttpClient());
  }

  @AfterEach
  void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  void constructorRejectsNullClient() {
    assertThrows(NullPointerException.class, () -> new HttpClient(null));
  }

  @Test
  void getReturnsResponseAndSendsHeaders() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(200).setBody("hello"));

    try (Response response = httpClient.get(server.url("/thing").toString(), Map.of("X-Test", "1"))) {
      assertEquals(200, response.code());
      assertEquals("hello", response.body().string());
    }

    RecordedRequest request = server.takeRequest();
    assertEquals("GET", request.getMethod());
    assertEquals("/thing", request.getPath());
    assertEquals("1", request.getHeader("X-Test"));
  }

  @Test
  void postSendsJsonBody() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(201));

    try (Response response = httpClient.post(server.url("/create").toString(), "{\"a\":1}", Map.of())) {
      assertEquals(201, response.code());
    }

    RecordedRequest request = server.takeRequest();
    assertEquals("POST", request.getMethod());
    assertEquals("{\"a\":1}", request.getBody().readUtf8());
    assertTrue(request.getHeader("Content-Type").contains("application/json"));
  }

  @Test
  void putSendsBodyAndHandlesNullBody() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(200));

    try (Response response = httpClient.put(server.url("/update").toString(), null, null)) {
      assertEquals(200, response.code());
    }

    RecordedRequest request = server.takeRequest();
    assertEquals("PUT", request.getMethod());
    assertEquals("", request.getBody().readUtf8());
  }

  @Test
  void deleteSendsDeleteRequest() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(204));

    try (Response response = httpClient.delete(server.url("/remove").toString(), Map.of())) {
      assertEquals(204, response.code());
    }

    RecordedRequest request = server.takeRequest();
    assertEquals("DELETE", request.getMethod());
  }

  @Test
  void wrapsIoExceptionAsUnchecked() throws IOException {
    String url = server.url("/gone").toString();
    server.shutdown(); // nothing listening anymore
    assertThrows(UncheckedIOException.class, () -> httpClient.get(url, Map.of()));
  }
}
