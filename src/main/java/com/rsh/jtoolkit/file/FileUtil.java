package com.rsh.jtoolkit.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

public class FileUtil {

  public String readFileToString(String filePath) throws IOException {
    return FileUtils.readFileToString(new File(filePath));
  }

  public void readStringToFile(String fileContent, String filePath) throws IOException {
    Path path = Paths.get(filePath);
    byte[] strToBytes = fileContent.getBytes();
    Files.write(path, strToBytes);
  }

  public void changeExtensionOfFile(String from, String to, String folderPath) throws IOException {
    Files.walk(Paths.get(folderPath))
        .filter(Files::isRegularFile)
        .filter(path -> path.getFileName().toString().contains("." + from))
        .forEach(
            path -> {
              Path targetPath =
                  Paths.get(path.toString().substring(0, path.toString().lastIndexOf("/")));
              try {
                Files.move(
                    path,
                    targetPath.resolve(
                        path.getFileName()
                                .toString()
                                .substring(0, path.getFileName().toString().lastIndexOf("."))
                            + "."
                            + to));
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            });
  }

  public void renameFolder(String from, String to) {
    File f = new File(from);
    if (f.exists() && f.isDirectory()) {
      boolean result = f.renameTo(new File(to));
      if (!result) {
        throw new RuntimeException("Rename failed");
      }
    } else {
      throw new RuntimeException("FolderNotFound");
    }
  }
}
