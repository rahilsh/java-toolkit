package com.rsh.jtoolkit.file;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

/** Utility methods for common file-system operations. */
public class FileUtil {

  /** Reads the whole file at {@code filePath} into a UTF-8 string. */
  public String readFileToString(String filePath) throws IOException {
    return FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
  }

  /** Writes {@code fileContent} (UTF-8) to {@code filePath}, creating/overwriting the file. */
  public void readStringToFile(String fileContent, String filePath) throws IOException {
    Path path = Paths.get(filePath);
    Files.write(path, fileContent.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Renames every regular file with extension {@code from} to extension {@code to} within {@code
   * folderPath} (non-recursive walk of the tree).
   */
  public void changeExtensionOfFile(String from, String to, String folderPath) throws IOException {
    try (var paths = Files.walk(Paths.get(folderPath))) {
      paths
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().endsWith("." + from))
          .forEach(
              path -> {
                String fileName = path.getFileName().toString();
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                Path target = path.resolveSibling(baseName + "." + to);
                try {
                  Files.move(path, target);
                } catch (IOException e) {
                  throw new UncheckedIOException(e);
                }
              });
    }
  }

  /** Renames (moves) the directory {@code from} to {@code to}. */
  public void renameFolder(String from, String to) {
    File f = new File(from);
    if (f.exists() && f.isDirectory()) {
      boolean result = f.renameTo(new File(to));
      if (!result) {
        throw new IllegalStateException("Rename failed: " + from + " -> " + to);
      }
    } else {
      throw new IllegalArgumentException("Folder not found: " + from);
    }
  }
}
