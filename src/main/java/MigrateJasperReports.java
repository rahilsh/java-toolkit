import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MigrateJasperReports {

  public static String resourcesPath;
  public static String reportsList;
  private static String jasperURL;
  private static String jasperUser;
  private static String jasperPass;
  private static String environment;

  public static void main(String[] args) throws Exception {

    MigrateJasperReports migrateJasperReports = new MigrateJasperReports();
    migrateJasperReports.loadProperties(args[0], args[1]);

    try {
      Object object = readFile(reportsList);
      JSONArray a = (JSONArray) object;
      System.out.println("Renaming .data files to .jrxml ...");
      Files.walk(Paths.get(resourcesPath))
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().contains(".data"))
          .forEach(
              path -> {
                Path targetPath =
                    Paths.get(path.toString().substring(0, path.toString().lastIndexOf("/")));

                try {
                  Files.move(
                      path,
                      targetPath.resolve(
                          path.getFileName()
                                  .toString()
                                  .substring(0, path.getFileName().toString().lastIndexOf("."))
                              + ".jrxml"));
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });

      String[] renameFolders = {
        "/bin/sh", "-c", "find " + resourcesPath + " -type d -execdir rename 's/_files//' '{}' \\;"
      };
      Process proc = null;
      try {
        System.out.println("Renaming _files folder...");
        proc = Runtime.getRuntime().exec(renameFolders);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (proc != null) {
          proc.destroy();
        }
      }
      int exitVal = 0;
      try {
        exitVal = proc.waitFor();
      } catch (InterruptedException e) {
        System.out.println(exitVal);
        e.printStackTrace();
      }

      for (int i = 0; i < a.size(); i++) {
        System.out.println("No: " + i);
        String reportPath = String.valueOf(a.get(i));
        System.out.println("Processing report: " + reportPath);
        String[] reportPathArray = reportPath.split("/", -1);
        String containingDir = reportPathArray[reportPathArray.length - 1];
        updateActualReportName(resourcesPath + reportPath, containingDir);
        updateJobs(reportPath);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void updateJobs(String reportPath) throws Exception {
    System.out.println("Updating jobs");
    String url =
        jasperURL
            + "jasperserver/rest_v2/jobs"
            + "?j_username="
            + jasperUser
            + "&j_password="
            + jasperPass
            + "&reportUnitURI="
            + reportPath;
    OkHttpClient httpClient = new OkHttpClient();
    okhttp3.Request request =
        new okhttp3.Request.Builder()
            .url(url)
            .get()
            .addHeader("Accept", "application/json")
            .build();
    Response response = httpClient.newCall(request).execute();
    if (response.code() == 200) {
      org.json.JSONObject jsonObject = new org.json.JSONObject(response.body().string());
      org.json.JSONArray array = jsonObject.getJSONArray("jobsummary");
      String[] createJobFolder = {
        "/bin/sh", "-c", "mkdir -p " + resourcesPath + reportPath + "/jobs"
      };
      int exitVal;
      Process proc = Runtime.getRuntime().exec(createJobFolder);
      exitVal = proc.waitFor();
      if (exitVal != 0) {
        throw new Exception("Exit not 0 for reading xml");
      }
      System.out.println("No of jobs: " + array.length());
      for (int i = 0; i < array.length(); i++) {
        org.json.JSONObject jsonObject1 = array.getJSONObject(i);
        int id = jsonObject1.getInt("id");
        String jobName = jsonObject1.getString("label");
        String getJobDetails =
            jasperURL
                + "jasperserver/rest_v2/jobs/"
                + id
                + "?j_username="
                + jasperUser
                + "&j_password="
                + jasperPass;
        okhttp3.Request jobRequest =
            new okhttp3.Request.Builder()
                .url(getJobDetails)
                .get()
                .addHeader("Accept", "application/json")
                .build();
        Response jobResponse = httpClient.newCall(jobRequest).execute();
        if (jobName.contains("/")) {
          System.out.println("Job with slash: " + jobName);
        }
        String newFileName =
            resourcesPath + reportPath + "/jobs/" + jobName.replace("/", "||") + ".json";
        File f = new File(newFileName);
        if (f.exists()) {
          newFileName =
              resourcesPath
                  + reportPath
                  + "/jobs/"
                  + jobName.replace("/", "||")
                  + "_"
                  + id
                  + ".json";
        }
        try (PrintWriter out = new PrintWriter(newFileName)) {
          out.println(jobResponse.body().string());
        }
      }
    } else {
      System.out.println("no jobs found");
    }
  }

  private static void updateActualReportName(String reportPath, String containingDir)
      throws Exception {
    System.out.println("Updating actual report name");
    String[] xmlPath = {"/bin/sh", "-c", "xml2json " + reportPath + ".xml"};
    Process proc = Runtime.getRuntime().exec(xmlPath);
    InputStream stdin = proc.getInputStream();
    InputStreamReader isr = new InputStreamReader(stdin);
    BufferedReader br = new BufferedReader(isr);
    String line;
    StringBuffer json = new StringBuffer();

    while ((line = br.readLine()) != null) json.append(line);

    int exitVal = proc.waitFor();
    if (exitVal != 0) {
      throw new Exception("Exit not 0 for reading xml");
    }
    JSONParser parser = new JSONParser();
    org.json.JSONObject jsonObject =
        new org.json.JSONObject(((JSONObject) parser.parse(json.toString())).toJSONString());
    String label = jsonObject.getJSONObject("reportUnit").getString("label");
    // .getJSONObject("localResource")
    // .getString("label");
    if (!label.equals(containingDir) || !label.equals("main_jrxml")) {
      String moveCommand = null;
      if (environment.equals("local")) {
        moveCommand =
            "mv "
                + reportPath
                + "/"
                + containingDir
                + ".jrxml '"
                + reportPath
                + "/"
                + label
                + ".jrxml'";
      } else {
        moveCommand =
            "mv " + reportPath + "/main_jrxml.jrxml '" + reportPath + "/" + label + ".jrxml'";
      }
      String[] renameToCorrectLabel = {"/bin/sh", "-c", moveCommand};

      proc = Runtime.getRuntime().exec(renameToCorrectLabel);
      exitVal = proc.waitFor();
      BufferedReader reader;
      StringBuffer output = new StringBuffer();
      if (exitVal != 0) {
        reader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        while ((line = reader.readLine()) != null) {
          output.append(line + "\n");
        }
        String sOutput = output.toString();
        sOutput = output.toString();
        System.out.println("sOutput" + sOutput);
      } else {
        reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      }
    }
  }

  private static Object readFile(String path) throws IOException, ParseException {
    System.out.println("Reading properties file...");
    JSONParser parser = new JSONParser();
    return parser.parse(new FileReader(path));
  }

  private void loadProperties(String propertiesFilePath, String environment)
      throws IOException, ParseException {
    Object object = readFile(propertiesFilePath);
    JSONObject propertiesJson = (JSONObject) object;
    org.json.JSONObject properties = new org.json.JSONObject(propertiesJson.toJSONString());
    this.resourcesPath = properties.getString("resourcesPath");
    this.reportsList = properties.getString("reportsList");
    this.jasperURL = properties.getString("jasperURL");
    this.jasperPass = properties.getString("jasperPass");
    this.jasperUser = properties.getString("jasperUser");
    this.environment = environment;
  }
}
