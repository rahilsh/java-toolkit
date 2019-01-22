package in.r.util.pdf;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import in.r.util.clients.HttpClient;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class PdfToImage {

  public static final String USER_BILLS_URL =
      "https://api.gw.zetapay.in/zeta.in/biller/1.0/userBills?"
          + "token=U3N5VTlXMndFZGFyK1hhbzR5Y1BraEl3RTl2RExzRU1"
          + "4MU0vSkxSb1Vjc3M5ZXJpOkFRRUM2UXNyWTBtanlRZFZ4WEp0"
          + "LzlpMjJSbXRFWVNUVmdqUTA0WjlHUG1rNWw1RTBQTmdla3FSM0"
          + "NjV2h6ZEVaaW15QldDdURXOEtiUndmcDNRVys0c0tQQmhoSDNE"
          + "TmpIRzI3S01acDhlNkhvYnJHcytGOXJycHdSMGc1cjVZTUI2TU"
          + "dWMHAvRHFnWEtvMlE0OU43UT09"
          + "&userID=%s&cardProgramID=%s&count=100";
  public static final String CSV_FILE_PATH =
      "/Users/rahil.r/Documents/temp/practo/july_sept/gadget_1.csv";

  public static final String BASE_PATH_FOR_SAVING_BILLS =
      "/Users/rahil.r/Documents/temp/practo/july_sept/java_gadget/";
  private static final String SLASH = "/";
  private static Gson gson = new Gson();
  private static String cardProgramID = "23c65c87-444a-4eb7-af70-bfb36ff91f3b";
  private static HttpClient httpClient = new HttpClient();

  public static void main(String[] args) throws IOException {

    System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

    File inputF = new File(CSV_FILE_PATH);
    BufferedReader br = null;
    try {
      InputStream inputFS = new FileInputStream(inputF);
      br = new BufferedReader(new InputStreamReader(inputFS));
      br.lines()
          .skip(1)
          .forEach(
              (line) -> {
                String[] row = line.split(",");
                User user = getUser(row);
                System.out.println("Processing userID: " + user.getUserID());
                try {
                  JsonObject response = getBillsForUser(user);
                  String userPath = BASE_PATH_FOR_SAVING_BILLS + user.getUserID();
                  deleteAndMakeDir(userPath);
                  saveMetaDataAsJsonFile(user, response, userPath);
                  System.out.println(
                      "No of Bills: " + response.get("bills").getAsJsonArray().size());
                  if (response.get("bills").getAsJsonArray().size() == 0) {
                    return;
                  }
                  for (JsonElement bill : response.get("bills").getAsJsonArray()) {
                    processBill(userPath, bill);
                  }
                } catch (Exception e) {
                  System.out.println(
                      "Error while processing User: "
                          + user.getUserID()
                          + " Message: "
                          + e.getMessage());
                }
              });

    } finally {
      br.close();
    }
    //    Files.walk(Paths.get("/Users/rahil.r/Documents/temp/column_soft/communication/"))
    //        .filter(path -> path.getFileName().toString().contains(".pdf"))
    //        .forEach(
    //            path -> {
    //              PDDocument document = null;
    //              try {
    //                document = PDDocument.load(new File(path.toAbsolutePath().toString()));
    //                PDFRenderer pdfRenderer = new PDFRenderer(document);
    //                for (int page = 0; page < document.getNumberOfPages(); ++page) {
    //                  BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300,
    // ImageType.RGB);
    //
    //                  // suffix in filename will be used as the file format
    //                  ImageIOUtil.writeImage(
    //                      bim,
    //                      "/Users/rahil.r/Documents/temp/pdftoimagetest/"
    //                          + path.getFileName().toString().replace(".pdf", "")
    //                          + "-"
    //                          + (page + 1)
    //                          + ".png",
    //                      100);
    //                }
    //                document.close();
    //              } catch (IOException e) {
    //                e.printStackTrace();
    //              }
    //            });
  }

  private static void saveMetaDataAsJsonFile(User user, JsonObject response, String userPath)
      throws IOException {
    Files.write(
        Paths.get(userPath + SLASH + user.getUserID() + ".json"), gson.toJson(response).getBytes());
  }

  private static JsonObject getBillsForUser(User user) throws IOException {
    return gson.fromJson(
        httpClient
            .get(String.format(USER_BILLS_URL, user.getUserID(), cardProgramID), Headers.of())
            .body()
            .string(),
        JsonObject.class);
  }

  private static void processBill(String userPath, JsonElement bill) throws IOException {
    String billNumber =
        Optional.ofNullable(
                bill.getAsJsonObject().getAsJsonObject("attrs").get("billNumber").getAsString())
            .filter(number -> !number.isEmpty())
            .orElse(bill.getAsJsonObject().get("claimId").getAsString().replace("/", "_"));
    System.out.println("Processing bill number: " + billNumber);
//    if(billNumber.equals("3983928468") ){
//      return;
//    }
    String billState = bill.getAsJsonObject().get("state").getAsString();
    if (ImmutableList.of("APPROVED", "UNPAID", "PARTIALLY_PAID", "PAID").contains(billState)) {
      String billFolderPath = userPath + SLASH + "bills/" + billNumber;
      makeDir(billFolderPath);
      int count = 1;
      for (JsonElement billUrl : bill.getAsJsonObject().getAsJsonArray("billUrls")) {
        processBillUrl(billNumber, billFolderPath, count, billUrl);
      }
    }
  }

  private static void processBillUrl(
      String billNumber, String billFolderPath, int count, JsonElement billUrl) throws IOException {
    Response actualBill = httpClient.get(billUrl.getAsString(), Headers.of());
    Headers headers = actualBill.headers();
    String extn = getExtension(headers);
    FileUtils.copyInputStreamToFile(
        actualBill.body().byteStream(),
        new File(billFolderPath + SLASH + billNumber + "_" + count + extn));
    handleBillAsPdf(billNumber, billFolderPath, count, headers, extn);
  }

  private static void handleBillAsPdf(
      String billNumber, String billFolderPath, int count, Headers headers, String extn)
      throws IOException {
    if (headers.get("Content-Type").equals("application/pdf")) {
      Path pdfPath = Paths.get(billFolderPath + SLASH + billNumber + "_" + count + extn);
      PDDocument document = null;
      try {
        document = PDDocument.load(new File(pdfPath.toAbsolutePath().toString()));

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
          BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
          ImageIOUtil.writeImage(
              bim,
              pdfPath.getParent().toString()
                  + SLASH
                  + pdfPath.getFileName().toString().replace(".pdf", "")
                  + "-"
                  + (page + 1)
                  + ".png",
              100);
        }
      } finally {
        try {
          if (document != null) document.close();
        } catch (Exception e) {
          System.out.println("Error while closing doc");
        }
      }
    }
  }

  private static String getExtension(Headers headers) {
    if (headers.get("Content-Type").equals("application/pdf")) {
      return ".pdf";
    }
    if (headers.get("Content-Type").equals("image/png")) {
      return ".png";
    }
    if (!headers.get("Content-Type").equals("image/jpeg")) {
      System.out.println("Bill not jpg or pdf or png");
    }
    return ".jpg";
  }

  private static void makeDir(String path) {
    File folder = new File(path);
    folder.mkdirs();
  }

  private static void deleteAndMakeDir(String path) throws IOException {
    File folder = new File(path);
    FileUtils.deleteDirectory(folder);
    folder.mkdirs();
  }

  private static User getUser(String[] row) {
    return User.builder()
        .cardID(Long.parseLong(row[0]))
        .email(row[1])
        .name(row[2])
        .corpID(Long.parseLong(row[3]))
        .companyID(Long.parseLong(row[4]))
        .employeeID(Long.parseLong(row[5]))
        .userID(Long.parseLong(row[6]))
        .build();
  }

  @Getter
  @Builder
  private static class User {
    private Long cardID;
    private String email;
    private String name;
    private Long corpID;
    private Long companyID;
    private Long employeeID;
    private Long userID;
  }
}
