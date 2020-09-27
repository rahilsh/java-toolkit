package in.rsh.jutil.zip;

import static in.rsh.jutil.zip.ZipUtil.pack;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

// TODO: Add more tests
class ZipUtilTest {

  private static final String ZIP_FILE_PATH = "/Users/rahil.shaikh/Development/tmp/tmp_zip.zip";
  private static final File file = new File(ZIP_FILE_PATH);

  @AfterEach
  public void tearDown() {
    if (file.exists()) {
      file.deleteOnExit();
    }
  }

  @Test
  void testPack() throws IOException {

    pack("/Users/rahil.shaikh/Development/tmp/tmp_zip", ZIP_FILE_PATH);
    assertTrue(file.exists());
    assertTrue(file.isFile());
  }
}
