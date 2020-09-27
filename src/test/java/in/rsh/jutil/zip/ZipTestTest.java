package in.rsh.jutil.zip;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import in.rsh.jutil.lib.AbstractTest;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

// TODO: Add more tests
class ZipTestTest extends AbstractTest {

  @Inject private ZipTest zipTest;
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

    zipTest.pack("/Users/rahil.shaikh/Development/tmp/tmp_zip", ZIP_FILE_PATH);
    assertTrue(file.exists());
    assertTrue(file.isFile());
  }
}
