package com.rsh.jtoolkit.excel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

/**
 * An immutable representation of a file embedded inside an Excel workbook.
 *
 * <p>The backing byte array is defensively copied on both construction and access, so instances are
 * safe to share.
 */
public final class EmbeddedFile {

  private final String name;
  private final byte[] content;

  public EmbeddedFile(String name, byte[] content) {
    this.name = Objects.requireNonNull(name, "name");
    this.content = Objects.requireNonNull(content, "content").clone();
  }

  /** The (base) file name of the embedded object, without any directory path. */
  public String name() {
    return name;
  }

  /** The embedded file's bytes (a defensive copy). */
  public byte[] content() {
    return content.clone();
  }

  /** The size of the embedded content in bytes. */
  public int size() {
    return content.length;
  }

  /**
   * Writes this embedded file into {@code directory} (created if necessary) and returns the path
   * written.
   */
  public Path writeTo(Path directory) throws IOException {
    Files.createDirectories(directory);
    Path target = directory.resolve(name);
    Files.write(target, content);
    return target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EmbeddedFile other)) {
      return false;
    }
    return name.equals(other.name) && Arrays.equals(content, other.content);
  }

  @Override
  public int hashCode() {
    return 31 * name.hashCode() + Arrays.hashCode(content);
  }

  @Override
  public String toString() {
    return "EmbeddedFile[name=" + name + ", size=" + content.length + "]";
  }
}
