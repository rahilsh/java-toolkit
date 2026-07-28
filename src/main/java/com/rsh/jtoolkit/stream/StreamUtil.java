package com.rsh.jtoolkit.stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;

public class StreamUtil {

  public static void streamToFile(InputStream inputStream, String outputFilePath) {
    try {
      FileUtils.copyInputStreamToFile(inputStream, new File(outputFilePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
