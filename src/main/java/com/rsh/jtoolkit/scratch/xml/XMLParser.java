package com.rsh.jtoolkit.scratch.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.gson.Gson;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class XMLParser {
  public static void main(String[] args) throws IOException {
    JacksonXmlModule xmlModule = new JacksonXmlModule();
    xmlModule.setDefaultUseWrapper(false);
    ObjectMapper objectMapper = new XmlMapper(xmlModule);
    DeserializationFeature failOnUnknownProperties =
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
    objectMapper.configure(failOnUnknownProperties, false);
    String xml =
        "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "    <employee id=\"12345\">\n"
            + "        <name>Graham</name>\n"
            + "        <type>Developer</type>\n"
            + "    </employee>";
    Employee employee = objectMapper.readValue(IOUtils.toInputStream(xml), Employee.class);

    System.out.println(new Gson().toJson(employee));
  }

  public static class Employee {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty private String name;

    @JacksonXmlProperty private String type;
  }
}
