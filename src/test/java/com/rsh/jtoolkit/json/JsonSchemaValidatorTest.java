package com.rsh.jtoolkit.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.everit.json.schema.ValidationException;
import org.junit.jupiter.api.Test;

class JsonSchemaValidatorTest {

  private static final String SCHEMA =
      "{\"$schema\":\"http://json-schema.org/draft-07/schema#\","
          + "\"type\":\"object\","
          + "\"properties\":{\"id\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}},"
          + "\"required\":[\"id\",\"name\"]}";

  @Test
  void validDocumentPasses() {
    assertTrue(JsonSchemaValidator.isValid(SCHEMA, "{\"id\":1,\"name\":\"toolkit\"}"));
  }

  @Test
  void missingRequiredFieldFails() {
    assertFalse(JsonSchemaValidator.isValid(SCHEMA, "{\"id\":1}"));
  }

  @Test
  void wrongTypeFails() {
    assertFalse(JsonSchemaValidator.isValid(SCHEMA, "{\"id\":\"x\",\"name\":\"y\"}"));
  }

  @Test
  void validateThrowsOnInvalidDocument() {
    assertThrows(
        ValidationException.class, () -> JsonSchemaValidator.validate(SCHEMA, "{\"id\":1}"));
  }
}
