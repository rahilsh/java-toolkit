package com.rsh.jtoolkit.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StreamUtilTest {

  @Test
  void writesStreamToFile(@TempDir Path tempDir) throws IOException {
    Path out = tempDir.resolve("out.txt");
    InputStream in = new ByteArrayInputStream("payload".getBytes(StandardCharsets.UTF_8));

    StreamUtil.streamToFile(in, out.toString());

    assertEquals("payload", Files.readString(out));
  }

  @Test
  void wrapsFailureAsRuntimeException() {
    InputStream in = new ByteArrayInputStream(new byte[0]);
    // A directory path is not a valid destination file.
    assertThrows(RuntimeException.class, () -> StreamUtil.streamToFile(in, "/"));
  }
}
