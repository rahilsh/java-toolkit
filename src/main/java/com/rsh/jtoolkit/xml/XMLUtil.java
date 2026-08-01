package com.rsh.jtoolkit.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XMLUtil {
  private final ObjectMapper objectMapper;

  public XMLUtil() {
    JacksonXmlModule xmlModule = new JacksonXmlModule();
    xmlModule.setDefaultUseWrapper(false);
    objectMapper = new XmlMapper(xmlModule);
    DeserializationFeature failOnUnknownProperties =
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
    objectMapper.configure(failOnUnknownProperties, false);
  }

  public <T> T parseXML(String xmlPath, Class<T> tClass) throws IOException {
    try (FileInputStream in = new FileInputStream(xmlPath)) {
      return objectMapper.readValue(in, tClass);
    }
  }

  public <T> T parseXML(InputStream xml, Class<T> tClass) throws IOException {
    return objectMapper.readValue(xml, tClass);
  }
}
