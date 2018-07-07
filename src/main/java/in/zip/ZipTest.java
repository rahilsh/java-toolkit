package in.zip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {
  public static void main(String[] args) throws IOException {
    pack("/Users/rahil.r/Documents/test_zip", "/Users/rahil.r/Documents/test_zip.zip");
  }

  public static void pack(String source, String zipFilePath) throws IOException {
    Path zipPath = Files.createFile(Paths.get(zipFilePath));
    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      Path sourcePath = Paths.get(source);
      Files.walk(sourcePath)
          .filter(path -> !Files.isDirectory(path))
          .forEach(
              path -> {
                ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
                try {
                  zs.putNextEntry(zipEntry);
                  Files.copy(path, zs);
                  zs.closeEntry();
                } catch (IOException e) {
                  throw new RuntimeException();
                }
              });
    }
  }
}
