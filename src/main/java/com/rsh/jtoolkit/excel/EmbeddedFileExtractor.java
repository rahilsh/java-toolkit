package com.rsh.jtoolkit.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.poifs.filesystem.Ole10Native;
import org.apache.poi.poifs.filesystem.Ole10NativeException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Extracts files embedded (as OLE objects / packages) inside {@code .xls} and {@code .xlsx}
 * workbooks.
 *
 * <p>Embedded OLE packages are unwrapped with POI's {@link Ole10Native} so the original file name
 * and bytes are recovered. Anything that is not an OLE10 package is returned as-is (for example an
 * {@code .xlsx} directly embedded in another workbook).
 *
 * <pre>{@code
 * List<EmbeddedFile> files = new EmbeddedFileExtractor().extract(Path.of("report.xlsx"));
 * for (EmbeddedFile f : files) {
 *   f.writeTo(Path.of("out"));
 * }
 * }</pre>
 */
public final class EmbeddedFileExtractor {

  /** Extracts all embedded files from the workbook at {@code workbook}. */
  public List<EmbeddedFile> extract(Path workbook) throws IOException {
    try (Workbook wb = WorkbookFactory.create(workbook.toFile(), null, true)) {
      return extract(wb);
    }
  }

  /** Extracts all embedded files from a workbook supplied as a stream. */
  public List<EmbeddedFile> extract(InputStream in) throws IOException {
    try (Workbook wb = WorkbookFactory.create(in)) {
      return extract(wb);
    }
  }

  /**
   * Extracts all embedded files from {@code workbook} into {@code targetDir}, returning the names
   * written.
   */
  public List<String> extractTo(Path workbook, Path targetDir) throws IOException {
    List<EmbeddedFile> files = extract(workbook);
    List<String> written = new ArrayList<>(files.size());
    for (EmbeddedFile file : files) {
      file.writeTo(targetDir);
      written.add(file.name());
    }
    return written;
  }

  private List<EmbeddedFile> extract(Workbook workbook) throws IOException {
    if (workbook instanceof XSSFWorkbook xssf) {
      return extractFromXssf(xssf);
    }
    if (workbook instanceof HSSFWorkbook hssf) {
      return extractFromHssf(hssf);
    }
    throw new IOException("Unsupported workbook type: " + workbook.getClass().getName());
  }

  private List<EmbeddedFile> extractFromXssf(XSSFWorkbook workbook) throws IOException {
    List<EmbeddedFile> files = new ArrayList<>();
    try {
      for (PackagePart part : workbook.getAllEmbeddedParts()) {
        byte[] raw;
        try (InputStream in = part.getInputStream()) {
          raw = IOUtils.toByteArray(in);
        }
        files.add(toEmbeddedFile(raw, baseName(part.getPartName().getName())));
      }
    } catch (OpenXML4JException e) {
      throw new IOException("Failed to read embedded parts", e);
    }
    return files;
  }

  private List<EmbeddedFile> extractFromHssf(HSSFWorkbook workbook) throws IOException {
    List<EmbeddedFile> files = new ArrayList<>();
    for (HSSFObjectData obj : workbook.getAllEmbeddedObjects()) {
      if (!obj.hasDirectoryEntry()) {
        continue;
      }
      DirectoryNode node = (DirectoryNode) obj.getDirectory();
      try {
        Ole10Native ole = Ole10Native.createFromEmbeddedOleObject(node);
        files.add(new EmbeddedFile(baseName(ole.getFileName()), ole.getDataBuffer()));
      } catch (Ole10NativeException | IOException notAnOlePackage) {
        files.add(new EmbeddedFile(baseName(node.getName()) + ".ole", serialize(node)));
      }
    }
    return files;
  }

  private EmbeddedFile toEmbeddedFile(byte[] raw, String fallbackName) {
    if (raw.length >= 8 && FileMagic.valueOf(raw) == FileMagic.OLE2) {
      try (POIFSFileSystem fs = new POIFSFileSystem(new ByteArrayInputStream(raw))) {
        Ole10Native ole = Ole10Native.createFromEmbeddedOleObject(fs);
        return new EmbeddedFile(baseName(ole.getFileName()), ole.getDataBuffer());
      } catch (Ole10NativeException | IOException notAnOlePackage) {
        // Fall through: the compound document is not an OLE10 package.
      }
    }
    return new EmbeddedFile(fallbackName, raw);
  }

  private static byte[] serialize(DirectoryNode node) throws IOException {
    try (POIFSFileSystem dest = new POIFSFileSystem();
        ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      copyNodes(node, dest.getRoot());
      dest.writeFilesystem(bos);
      return bos.toByteArray();
    }
  }

  private static void copyNodes(DirectoryEntry src, DirectoryEntry dest) throws IOException {
    for (Entry entry : src) {
      if (entry instanceof DirectoryEntry dir) {
        DirectoryEntry newDir = dest.createDirectory(entry.getName());
        newDir.setStorageClsid(dir.getStorageClsid());
        copyNodes(dir, newDir);
      } else {
        try (InputStream in = ((DirectoryNode) src).createDocumentInputStream(entry)) {
          dest.createDocument(entry.getName(), in);
        }
      }
    }
  }

  private static String baseName(String name) {
    if (name == null || name.isBlank()) {
      return "embedded";
    }
    int slash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
    String base = slash >= 0 ? name.substring(slash + 1) : name;
    return base.isBlank() ? "embedded" : base;
  }
}
