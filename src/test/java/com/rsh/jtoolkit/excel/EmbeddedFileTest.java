package com.rsh.jtoolkit.excel;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class EmbeddedFileTest {

  @Test
  void defensivelyCopiesContentOnConstructionAndAccess() {
    byte[] source = "abc".getBytes(StandardCharsets.UTF_8);
    EmbeddedFile file = new EmbeddedFile("f.txt", source);

    source[0] = 'X'; // mutate caller's array
    assertEquals("abc", new String(file.content(), StandardCharsets.UTF_8));

    byte[] returned = file.content();
    returned[0] = 'Y'; // mutate returned array
    assertEquals("abc", new String(file.content(), StandardCharsets.UTF_8));
  }

  @Test
  void exposesNameAndSize() {
    EmbeddedFile file = new EmbeddedFile("f.txt", new byte[] {1, 2, 3});
    assertEquals("f.txt", file.name());
    assertEquals(3, file.size());
  }

  @Test
  void rejectsNulls() {
    assertThrows(NullPointerException.class, () -> new EmbeddedFile(null, new byte[0]));
    assertThrows(NullPointerException.class, () -> new EmbeddedFile("f", null));
  }

  @Test
  void writeToCreatesDirectoryAndFile(@TempDir Path tempDir) throws IOException {
    EmbeddedFile file = new EmbeddedFile("nested/f.txt".replace('/', '_'), new byte[] {9, 8, 7});
    Path dir = tempDir.resolve("out");
    Path written = file.writeTo(dir);
    assertTrue(Files.exists(written));
    assertArrayEquals(new byte[] {9, 8, 7}, Files.readAllBytes(written));
  }

  @Test
  void equalsAndHashCode() {
    EmbeddedFile a = new EmbeddedFile("f", new byte[] {1, 2});
    EmbeddedFile b = new EmbeddedFile("f", new byte[] {1, 2});
    EmbeddedFile c = new EmbeddedFile("f", new byte[] {1, 3});
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a, c);
    assertTrue(a.toString().contains("f"));
  }
}
