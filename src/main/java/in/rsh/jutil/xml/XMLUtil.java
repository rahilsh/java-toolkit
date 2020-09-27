package in.rsh.jutil.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import in.rsh.jutil.xml.ParserJasperXML.Resources;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class XMLUtil {
  private final ObjectMapper objectMapper;

  public static void main(String[] args) throws IOException {
    String xmlString = "<a></a>";
    new XMLUtil().parseXML(IOUtils.toInputStream(xmlString), Resources.class);
  }

  public XMLUtil() {
    JacksonXmlModule xmlModule = new JacksonXmlModule();
    xmlModule.setDefaultUseWrapper(false);
    objectMapper = new XmlMapper(xmlModule);
    DeserializationFeature failOnUnknownProperties =
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
    objectMapper.configure(failOnUnknownProperties, false);
  }

  public <T> T parseXML(String xmlPath, Class<T> tClass) throws IOException {
    return objectMapper.readValue(new FileInputStream(xmlPath), tClass);
  }

  public <T> T parseXML(InputStream xml, Class<T> tClass) throws IOException {
    return objectMapper.readValue(xml, tClass);
  }
}
