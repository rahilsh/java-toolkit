package com.rsh.jtoolkit.excel;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class EmbeddedFileExtractorTest {

  private static final byte[] PAYLOAD = "hi there".getBytes(StandardCharsets.UTF_8);
  private final EmbeddedFileExtractor extractor = new EmbeddedFileExtractor();

  @Test
  void returnsEmptyWhenXlsxHasNoEmbeddedFiles(@TempDir Path tempDir) throws IOException {
    Path xlsx = tempDir.resolve("plain.xlsx");
    try (XSSFWorkbook wb = new XSSFWorkbook()) {
      wb.createSheet("data");
      write(wb, xlsx);
    }
    assertTrue(extractor.extract(xlsx).isEmpty());
  }

  @Test
  void extractsOlePackageFromXlsx(@TempDir Path tempDir) throws IOException {
    Path xlsx = tempDir.resolve("with-embedded.xlsx");
    writeXlsxWithEmbeddedFile(xlsx);

    List<EmbeddedFile> files = extractor.extract(xlsx);

    assertEquals(1, files.size());
    assertEquals("hello.txt", files.get(0).name());
    assertArrayEquals(PAYLOAD, files.get(0).content());
  }

  @Test
  void extractsOlePackageFromXls(@TempDir Path tempDir) throws IOException {
    Path xls = tempDir.resolve("with-embedded.xls");
    writeXlsWithEmbeddedFile(xls);

    List<EmbeddedFile> files = extractor.extract(xls);

    assertEquals(1, files.size());
    assertEquals("hello.txt", files.get(0).name());
    assertArrayEquals(PAYLOAD, files.get(0).content());
  }

  @Test
  void extractsFromStream(@TempDir Path tempDir) throws IOException {
    Path xlsx = tempDir.resolve("stream.xlsx");
    writeXlsxWithEmbeddedFile(xlsx);

    try (var in = Files.newInputStream(xlsx)) {
      List<EmbeddedFile> files = extractor.extract(in);
      assertEquals(1, files.size());
      assertArrayEquals(PAYLOAD, files.get(0).content());
    }
  }

  @Test
  void extractToWritesFilesToDisk(@TempDir Path tempDir) throws IOException {
    Path xlsx = tempDir.resolve("to-disk.xlsx");
    writeXlsxWithEmbeddedFile(xlsx);

    Path outDir = tempDir.resolve("extracted");
    List<String> written = extractor.extractTo(xlsx, outDir);

    assertEquals(List.of("hello.txt"), written);
    assertArrayEquals(PAYLOAD, Files.readAllBytes(outDir.resolve("hello.txt")));
  }

  private void writeXlsxWithEmbeddedFile(Path path) throws IOException {
    try (XSSFWorkbook wb = new XSSFWorkbook()) {
      XSSFSheet sheet = wb.createSheet("data");
      int oleIdx = wb.addOlePackage(PAYLOAD, "hello", "hello.txt", "hello.txt");
      int picIdx = wb.addPicture(pngIcon(), Workbook.PICTURE_TYPE_PNG);
      XSSFDrawing drawing = sheet.createDrawingPatriarch();
      ClientAnchor anchor = wb.getCreationHelper().createClientAnchor();
      anchor.setCol1(0);
      anchor.setRow1(0);
      anchor.setCol2(2);
      anchor.setRow2(3);
      drawing.createObjectData(anchor, oleIdx, picIdx);
      write(wb, path);
    }
  }

  private void writeXlsWithEmbeddedFile(Path path) throws IOException {
    try (HSSFWorkbook wb = new HSSFWorkbook()) {
      HSSFSheet sheet = wb.createSheet("data");
      int oleIdx = wb.addOlePackage(PAYLOAD, "hello", "hello.txt", "hello.txt");
      int picIdx = wb.addPicture(pngIcon(), Workbook.PICTURE_TYPE_PNG);
      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
      HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 0, (short) 2, 3);
      patriarch.createObjectData(anchor, oleIdx, picIdx);
      write(wb, path);
    }
  }

  private static void write(Workbook wb, Path path) throws IOException {
    try (var out = Files.newOutputStream(path)) {
      wb.write(out);
    }
  }

  private static byte[] pngIcon() throws IOException {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    return baos.toByteArray();
  }
}
