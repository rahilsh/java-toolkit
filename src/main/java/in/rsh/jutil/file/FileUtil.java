package in.rsh.jutil.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

public class FileUtil {
  public static void main(String[] args) {
    // create source File object
    File oldName =
        new File(
            "/Users/rahil.r/development/jasper/response/resources/Reports/pre_prod/IFI/Common/FundMovement/TESTING_unsettled_pg_txns_files");

    // create destination File object
    File newName =
        new File(
            "/Users/rahil.r/development/jasper/response/resources/Reports/pre_prod/IFI/Common/FundMovement/TESTING_unsettled_pg_txns");

    /*
     * To rename a file or directory, use
     * boolean renameTo(File destination) method of Java File class.
     *
     * This method returns true if the file was renamed successfully, false
     * otherwise.
     */

    boolean isFileRenamed = oldName.renameTo(newName);

    if (isFileRenamed) System.out.println("File has been renamed");
    else System.out.println("Error renaming the file");
  }

  public String readFileToString(String filePath) throws IOException {
    return FileUtils.readFileToString(new File(filePath));
  }

  public void readStringToFile(String fileContent, String filePath) throws IOException {
    Path path = Paths.get(filePath);
    byte[] strToBytes = fileContent.getBytes();
    Files.write(path, strToBytes);
  }

  public void changeExtensionOfFile(String from, String to, String folderPath) throws IOException {
    System.out.println(String.format("Renaming %s files to %s", from, to));
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
                e.printStackTrace();
              }
            });
  }

  public void renameFolder(String from, String to) {
    File f = new File(from);
    if (f.exists() && f.isDirectory()) {
      boolean result = f.renameTo(new File(to));
      System.out.println("Result: " + result);
    } else {
      throw new RuntimeException("FolderNotFound");
    }
  }
}
