package in.helper;

import com.amazonaws.util.IOUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class CSVHelper {

  public <T> List<T> readCSVFile(String fileName, Class<T> dataClass) {
    if (!fileName.endsWith(".csv")) {
      fileName = fileName + ".csv";
    }
    String csvData = readFile(fileName);
    return parseCSVFile(csvData, dataClass);
  }

  public String readFile(String fileName) {
    String fullFileName;
    if (fileName.startsWith("resources")) {
      fullFileName = fileName;
    } else {
      fullFileName = "resources/" + fileName;
    }
    String result = "";
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  private <T> List<T> parseCSVFile(String csvData, Class<T> dataClass) {
    HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
    mappingStrategy.setType(dataClass);
    CsvToBean<T> csvBean = new CsvToBean<>();
    CSVReader reader =
        new CSVReader(
            new StringReader(csvData),
            CSVParser.DEFAULT_SEPARATOR,
            CSVParser.DEFAULT_QUOTE_CHARACTER,
            CSVParser.DEFAULT_ESCAPE_CHARACTER,
            0,
            false,
            false);
    return csvBean.parse(mappingStrategy, reader);
  }
}
