package com.rsh.jtoolkit.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileUtilTest {

  private FileUtil fileUtil;
  private Path tempDir;
  private Path tempFile;

  @BeforeEach
  void setUp() throws IOException {
    fileUtil = new FileUtil();
    tempDir = Files.createTempDirectory("fileutil_test");
    tempFile = tempDir.resolve("test.txt");
    Files.writeString(tempFile, "Hello World");
  }

  @AfterEach
  void tearDown() throws IOException {
    if (tempDir != null && Files.exists(tempDir)) {
      Files.walk(tempDir)
          .sorted((a, b) -> b.compareTo(a))
          .forEach(
              path -> {
                try {
                  Files.delete(path);
                } catch (IOException ignored) {
                }
              });
    }
  }

  @Test
  void testReadFileToString() throws IOException {
    String content = fileUtil.readFileToString(tempFile.toString());
    assertEquals("Hello World", content);
  }

  @Test
  void testReadFileToStringEmpty() throws IOException {
    Path emptyFile = tempDir.resolve("empty.txt");
    Files.writeString(emptyFile, "");
    String content = fileUtil.readFileToString(emptyFile.toString());
    assertEquals("", content);
  }

  @Test
  void testWriteStringToFile() throws IOException {
    Path newFile = tempDir.resolve("newfile.txt");
    fileUtil.readStringToFile("New Content", newFile.toString());
    assertTrue(Files.exists(newFile));
    assertEquals("New Content", Files.readString(newFile));
  }

  @Test
  void testWriteStringToFileWithSpecialChars() throws IOException {
    Path newFile = tempDir.resolve("special.txt");
    fileUtil.readStringToFile("Line1\nLine2\tTabbed", newFile.toString());
    assertTrue(Files.exists(newFile));
    String content = Files.readString(newFile);
    assertTrue(content.contains("Line1"));
    assertTrue(content.contains("Line2"));
  }

  @Test
  void testChangeExtensionOfFile() throws IOException {
    Path txtFile = tempDir.resolve("document.txt");
    Files.writeString(txtFile, "content");

    fileUtil.changeExtensionOfFile("txt", "md", tempDir.toString());

    Path mdFile = tempDir.resolve("document.md");
    assertTrue(Files.exists(mdFile));
    assertEquals("content", Files.readString(mdFile));
  }

  @Test
  void testChangeExtensionOfFileNoMatch() throws IOException {
    Path txtFile = tempDir.resolve("document.txt");
    Files.writeString(txtFile, "content");

    fileUtil.changeExtensionOfFile("xyz", "md", tempDir.toString());

    assertTrue(Files.exists(txtFile));
    assertEquals("content", Files.readString(txtFile));
  }

  @Test
  void testRenameFolderSuccess() throws IOException {
    Path sourceFolder = tempDir.resolve("source");
    Path targetFolder = tempDir.resolve("target");
    Files.createDirectory(sourceFolder);
    fileUtil.renameFolder(sourceFolder.toString(), targetFolder.toString());
    assertTrue(Files.exists(targetFolder));
  }

  @Test
  void testRenameFolderNotFound() {
    Path nonExistent = tempDir.resolve("nonexistent");
    assertThrows(
        RuntimeException.class,
        () -> {
          fileUtil.renameFolder(nonExistent.toString(), tempDir.resolve("target").toString());
        });
  }

  @Test
  void testRenameFolderWithFiles() throws IOException {
    Path sourceFolder = tempDir.resolve("sourceWithFiles");
    Path targetFolder = tempDir.resolve("targetWithFiles");
    Files.createDirectory(sourceFolder);
    Files.writeString(sourceFolder.resolve("file.txt"), "content");
    fileUtil.renameFolder(sourceFolder.toString(), targetFolder.toString());
    assertTrue(Files.exists(targetFolder));
    assertTrue(Files.exists(targetFolder.resolve("file.txt")));
  }
}
