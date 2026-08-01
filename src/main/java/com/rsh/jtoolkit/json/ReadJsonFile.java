package com.rsh.jtoolkit.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Reads a JSON file from disk into a Gson {@link JsonObject}. */
public final class ReadJsonFile {

  private ReadJsonFile() {}

  /**
   * Reads the UTF-8 JSON file at {@code filePath} and returns it as a {@link JsonObject}.
   *
   * @throws UncheckedIOException if the file cannot be read
   */
  public static JsonObject readJsonFile(String filePath) {
    try (Reader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return JsonParser.parseReader(reader).getAsJsonObject();
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to read JSON file: " + filePath, e);
    }
  }
}
