package com.rsh.jtoolkit.json;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

/** Validates JSON documents against a JSON Schema (draft-07 and friends) using everit-json-schema. */
public final class JsonSchemaValidator {

  private JsonSchemaValidator() {}

  /**
   * Validates {@code json} against {@code schemaJson}.
   *
   * @param schemaJson the JSON Schema document
   * @param json the JSON document to validate
   * @throws ValidationException if the document does not conform to the schema
   * @throws org.json.JSONException if either argument is not valid JSON
   */
  public static void validate(String schemaJson, String json) {
    Schema schema = SchemaLoader.load(new JSONObject(schemaJson));
    schema.validate(new JSONObject(json));
  }

  /**
   * Returns {@code true} if {@code json} conforms to {@code schemaJson}, {@code false} otherwise.
   *
   * @throws org.json.JSONException if either argument is not valid JSON
   */
  public static boolean isValid(String schemaJson, String json) {
    try {
      validate(schemaJson, json);
      return true;
    } catch (ValidationException e) {
      return false;
    }
  }
}
