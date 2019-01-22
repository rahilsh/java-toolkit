package in.r.util.migration;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import in.r.util.clients.HttpClient;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.Headers;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

public class ZenUpdateUser {
  private static final Type REVIEW_TYPE = new TypeToken<Map<Long, Long>>() {}.getType();

  public static void main(String[] args) throws FileNotFoundException, SQLException {
    Gson gson = new Gson();
    HttpClient httpClient = new HttpClient();
    // Map<Long, Long> o = gson.fromJson("{\"12\":12}", REVIEW_TYPE);
    Connection zetauser = null;
    Statement stmt = null;

    Connection corpben = null;

    // here sonoo is database name, root is username and password
    Statement statement = null;
    try {
      Class.forName("org.postgresql.Driver");
      Class.forName("com.mysql.jdbc.Driver");
      zetauser =
          DriverManager.getConnection(
              "jdbc:postgresql://10.19.2.148:5432/reporting", "rahilr", "3YuzM6ras7");

      stmt = zetauser.createStatement();

      corpben =
          DriverManager.getConnection(
              "jdbc:mysql://prodaxs.corp.zeta.in:13306/corp", "rahilr", "R83cE%!23ioMisS");

      statement = corpben.createStatement();

      JsonReader reader =
          new JsonReader(new FileReader("/Users/rahil.r/Documents/zen_july_unique_users.json"));
      Map<Long, Long> map = gson.fromJson(reader, REVIEW_TYPE);
      Statement finalStmt = stmt;
      Statement finalStatement = statement;
      try (BufferedWriter writer =
              Files.newBufferedWriter(Paths.get("/Users/rahil.r/Documents/zendesk_o.csv"));
          CSVPrinter csvPrinter =
              new CSVPrinter(
                  writer,
                  CSVFormat.DEFAULT.withHeader(
                      "ticketId",
                      "ZendeskUserId",
                      "email",
                      "phone",
                      "ZetaUserIds",
                      "corpId",
                      "Status")); ) {
        map.keySet()
            .stream()
            .forEach(
                userId -> {
                  String status = "";
                  Long ticketId = map.get(userId);
                  String email = null;
                  String phone = null;
                  List<Long> zetaUserIds = new ArrayList<>();
                  System.out.printf("Processing %s,%s", ticketId, userId);
                  JsonObject user = null;
                  ResultSet rs = null;
                  ResultSet rs1 = null;
                  Long corpId = null;
                  try {
                    user =
                        gson.fromJson(
                            httpClient
                                .get(
                                    "https://zeta.zendesk.com/api/v2/users/" + userId + ".json",
                                    Headers.of(
                                        "Authorization",
                                        "Basic cmFoaWxyQHpldGEudGVjaDpaZXRhWmVuZGVza0AxNDUw"))
                                .body()
                                .string(),
                            JsonObject.class);

                    email =
                        !user.get("user").getAsJsonObject().get("email").isJsonNull()
                            ? user.get("user").getAsJsonObject().get("email").getAsString()
                            : null;
                    phone =
                        !user.get("user").getAsJsonObject().get("phone").isJsonNull()
                            ? user.get("user").getAsJsonObject().get("phone").getAsString()
                            : null;
                    if (email == null && phone == null) {
                      status = "Email and phone not present!";
                    } else {
                      System.out.print(". " + email + "," + phone);
                      String sql = "";
                      if (email != null && !email.isEmpty()) {
                        sql =
                            "select userid, attrs->>'isCorpUser' from zetauser.user_profiles where userid in "
                                + "(select userid from zetauser.emails where email = '"
                                + email
                                + "')";

                        rs = finalStmt.executeQuery(sql);
                        while (rs.next()) {
                          zetaUserIds.add(rs.getLong(1));
                        }
                      }
                      if (zetaUserIds.isEmpty()) {
                        sql =
                            "select userid, attrs->>'isCorpUser' from zetauser.user_profiles\n"
                                + "where mobilenumber = '"
                                + phone
                                + "'";
                        rs = finalStmt.executeQuery(sql);
                        while (rs.next()) {
                          zetaUserIds.add(rs.getLong(1));
                        }
                      }

                      if (!zetaUserIds.isEmpty()) {
                        System.out.print(". " + StringUtils.join(zetaUserIds, " AND "));
                        sql =
                            "select corporateid\n"
                                + "from corp.company\n"
                                + "where id in (select distinct companyid\n"
                                + "             from corp.order\n"
                                + "             where id in\n"
                                + "                   (select order_id from corp.payout where id = "
                                + "(select max(id) from corp.payout where user_id in ("
                                + StringUtils.join(zetaUserIds, ",")
                                + "))))";

                        rs1 = finalStatement.executeQuery(sql);
                        int corpCount = 0;

                        while (rs1.next()) {
                          corpCount++;
                          corpId = rs1.getLong(1);
                        }

                        if (corpCount > 0 && corpId != null && corpId > 0) {
                          System.out.print(". " + corpId);

                          JsonObject userFields = new JsonObject();
                          JsonObject jsonObject = new JsonObject();
                          JsonObject fields = new JsonObject();
                          fields.addProperty("corpid", String.valueOf(corpId));
                          fields.addProperty("iscorpuser", "true");
                          userFields.add("user_fields", fields);
                          jsonObject.add("user", userFields);

                          httpClient.put(
                              "https://zeta.zendesk.com/api/v2/users/" + userId + ".json",
                              gson.toJson(jsonObject),
                              ImmutableMap.of(
                                  "Authorization",
                                  "Basic cmFoaWxyQHpldGEudGVjaDpaZXRhWmVuZGVza0AxNDUw",
                                  "Content-Type",
                                  "application/json"));
                          status = "corpID populated!";
                        } else {
                          status = "No payouts for user!";
                        }
                      } else {
                        status =
                            String.format("No zeta user for email %s and phone %s", email, phone);
                      }
                    }
                    System.out.println(". Status: " + status);

                    csvPrinter.printRecord(
                        ticketId,
                        userId,
                        email,
                        phone,
                        StringUtils.join(zetaUserIds, " AND "),
                        corpId,
                        status);

                    System.out.printf(
                        "CSVROW= %s,%s,%s,%s,%s,%s,%s",
                        ticketId,
                        userId,
                        email,
                        phone,
                        StringUtils.join(zetaUserIds, " AND "),
                        corpId,
                        status);
                    System.out.println();
                  } catch (Exception e1) {
                    System.out.println("Error while processing user: " + userId);
                    e1.printStackTrace();
                  } finally {
                    if (rs != null) {
                      try {
                        rs.close();
                      } catch (SQLException e) {
                        e.printStackTrace();
                      }
                    }
                    if (rs1 != null) {
                      try {
                        rs1.close();
                      } catch (SQLException e) {
                        e.printStackTrace();
                      }
                    }
                  }
                });
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    } finally {
      try {
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();

      }
      zetauser.close();
    }
  }
}
