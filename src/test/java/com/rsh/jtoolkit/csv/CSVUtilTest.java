package com.rsh.jtoolkit.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UncheckedIOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class CSVUtilTest {

  private final CSVUtil csvUtil = new CSVUtil();

  @Test
  void readsCsvResourceWithoutExtension() {
    List<Person> people = csvUtil.readCSVFile("people", Person.class);
    assertEquals(2, people.size());
    assertEquals("alice", people.get(0).getName());
    assertEquals("30", people.get(0).getAge());
  }

  @Test
  void readsCsvResourceWithExtension() {
    List<Person> people = csvUtil.readCSVFile("people.csv", Person.class);
    assertEquals(2, people.size());
    assertEquals("bob", people.get(1).getName());
  }

  @Test
  void throwsWhenResourceMissing() {
    assertThrows(UncheckedIOException.class, () -> csvUtil.readCSVFile("does-not-exist", Person.class));
  }

  public static class Person {
    private String name;
    private String age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAge() {
      return age;
    }

    public void setAge(String age) {
      this.age = age;
    }
  }
}
