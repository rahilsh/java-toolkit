package com.rsh.jtoolkit.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import org.apache.commons.io.IOUtils;

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

  public static void main(String[] args) throws IOException {
    String xmlString = "<Person><age>4</age></Person>";
    Person resources = new XMLUtil().parseXML(IOUtils.toInputStream(xmlString), Person.class);
    System.out.println(resources.getAge());
  }

  public <T> T parseXML(String xmlPath, Class<T> tClass) throws IOException {
    return objectMapper.readValue(new FileInputStream(xmlPath), tClass);
  }

  public <T> T parseXML(InputStream xml, Class<T> tClass) throws IOException {
    return objectMapper.readValue(xml, tClass);
  }

  @Getter
  private static class Person {
    public String age;
  }
}
