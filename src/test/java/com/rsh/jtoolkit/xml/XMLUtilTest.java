package com.rsh.jtoolkit.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class XMLUtilTest {

  private final XMLUtil xmlUtil = new XMLUtil();

  @Test
  void parsesFromInputStream() throws IOException {
    String xml = "<Person><name>Graham</name><type>Developer</type></Person>";
    Person person =
        xmlUtil.parseXML(
            new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), Person.class);
    assertEquals("Graham", person.getName());
    assertEquals("Developer", person.getType());
  }

  @Test
  void parsesFromFilePath(@TempDir Path tempDir) throws IOException {
    Path file = tempDir.resolve("person.xml");
    Files.writeString(file, "<Person><name>Ada</name><type>Engineer</type></Person>");
    Person person = xmlUtil.parseXML(file.toString(), Person.class);
    assertEquals("Ada", person.getName());
  }

  @Test
  void ignoresUnknownProperties() throws IOException {
    String xml = "<Person><name>Bob</name><unknown>x</unknown></Person>";
    Person person =
        xmlUtil.parseXML(
            new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), Person.class);
    assertEquals("Bob", person.getName());
  }

  public static class Person {
    private String name;
    private String type;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
