package com.rsh.jtoolkit.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonObject;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReadJsonFileTest {

  @Test
  void readsJsonObjectFromFile(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("data.json");
    Files.writeString(file, "{\"id\":1,\"name\":\"toolkit\"}");

    JsonObject json = ReadJsonFile.readJsonFile(file.toString());

    assertEquals(1, json.get("id").getAsInt());
    assertEquals("toolkit", json.get("name").getAsString());
  }

  @Test
  void throwsWhenFileMissing(@TempDir Path tempDir) {
    Path missing = tempDir.resolve("missing.json");
    assertThrows(UncheckedIOException.class, () -> ReadJsonFile.readJsonFile(missing.toString()));
  }
}
