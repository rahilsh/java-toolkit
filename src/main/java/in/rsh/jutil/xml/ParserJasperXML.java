package in.rsh.jutil.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import lombok.Getter;

public class ParserJasperXML {

  public static void main(String[] args) throws IOException {
    JacksonXmlModule xmlModule = new JacksonXmlModule();
    xmlModule.setDefaultUseWrapper(false);
    ObjectMapper objectMapper = new XmlMapper(xmlModule);
    DeserializationFeature failOnUnknownProperties =
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
    objectMapper.configure(failOnUnknownProperties, false);
    Resources resources =
        objectMapper.readValue(new FileInputStream("/Users/rahil.r/test1.xml"), Resources.class);
    resources.getReportUnitURI().stream()
        .filter(reportUnitURI -> !reportUnitURI.getReportPath().startsWith("/reports/users/"))
        .forEach(reportUnitURI -> System.out.println(reportUnitURI.getReportPath()));
  }

  public static class Resources {
    @Getter
    @JacksonXmlProperty(localName = "resourceDescriptor")
    List<ReportUnitURI> reportUnitURI;

    public static class ReportUnitURI {
      @Getter
      @JacksonXmlProperty(isAttribute = true, localName = "uriString")
      private String reportPath;
    }
  }
}
