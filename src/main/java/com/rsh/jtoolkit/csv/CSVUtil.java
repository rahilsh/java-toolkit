package com.rsh.jtoolkit.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.IOUtils;

/** Reads CSV files from the classpath and maps them onto beans using OpenCSV. */
public class CSVUtil {

  /**
   * Reads a CSV classpath resource and maps each row onto an instance of {@code dataClass} using
   * header-name column mapping.
   *
   * @param fileName classpath resource name (the {@code .csv} suffix is optional)
   * @param dataClass the target bean type
   * @throws UncheckedIOException if the resource cannot be found or read
   */
  public <T> List<T> readCSVFile(String fileName, Class<T> dataClass) {
    String resourceName = fileName.endsWith(".csv") ? fileName : fileName + ".csv";
    ClassLoader classLoader = getClass().getClassLoader();
    try (InputStream in = classLoader.getResourceAsStream(resourceName)) {
      if (in == null) {
        throw new UncheckedIOException(
            new java.io.FileNotFoundException("CSV resource not found: " + resourceName));
      }
      String result = IOUtils.toString(in, StandardCharsets.UTF_8);
      return parseCSVFile(result, dataClass);
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to read CSV resource: " + resourceName, e);
    }
  }

  private <T> List<T> parseCSVFile(String csvData, Class<T> dataClass) {
    HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
    mappingStrategy.setType(dataClass);
    return new CsvToBeanBuilder<T>(new StringReader(csvData))
        .withMappingStrategy(mappingStrategy)
        .withIgnoreLeadingWhiteSpace(false)
        .build()
        .parse();
  }
}
