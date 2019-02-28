package in.r.util.file;

import java.io.File;

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
}
