package com.rsh.jtoolkit.zip;

import static com.rsh.jtoolkit.zip.ZipUtil.pack;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO: Add more tests
class ZipUtilTest {

  private static final String ZIP_FILE_PATH = "/tmp/tmp_zip.zip";
  private static final String SOURCE = "/tmp/tmp_zip";
  private static final File file = new File(ZIP_FILE_PATH);
  private static final File sourceFolder = new File(SOURCE);

  @BeforeEach
  public void setUp() throws IOException {
    sourceFolder.mkdir();
    new File(SOURCE + "/test.txt").createNewFile();
  }

  @AfterEach
  public void tearDown() {
    if (file.exists()) {
      file.deleteOnExit();
    }
    if (sourceFolder.exists()) {
      sourceFolder.deleteOnExit();
    }
  }

  @Test
  void testPack() throws IOException {

    pack(SOURCE, ZIP_FILE_PATH);
    assertTrue(file.exists());
    assertTrue(file.isFile());
  }
}
