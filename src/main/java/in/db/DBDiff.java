package in.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DBDiff {


  public static void main(String[] args) throws Exception {

    try {
      String databaseName = "corp";

      Class.forName("com.mysql.jdbc.Driver");

      Connection conn = DriverManager.getConnection(
          "jdbc:mysql://mv-demo.cbrhhaoqnjwg.ap-southeast-1.rds.amazonaws.com:3306/corp",
          "root", "8NUbsKi77OSwQvRvtmXF");

      String[] types = {"TABLE"};
      ResultSet resultSet = conn.getMetaData()
          .getTables(databaseName, null, "%", types);
      String[] tableName = new String[2000];
      int i = 0, tableCount = 0;
      while (resultSet.next()) {
        tableName[i] = resultSet.getString(3);
        i++;
      }
      tableCount = i;
      int[] colCount = new int[400];
      String[][] colType = new String[400][400];
      String[][] colName = new String[400][400];
      i = 0;
      while (i < tableCount) {
        PreparedStatement ps;
        if (tableName[i].equals("order")) {
          ps = conn.prepareStatement("select * from `order` limit 1");
        } else {
          ps = conn.prepareStatement("select * from " + tableName[i] + " limit 1");
        }
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        colCount[i] = resultSetMetaData.getColumnCount();
        int j = 0;
        while (j < colCount[i]) {
          colType[i][j] = resultSetMetaData.getColumnTypeName(j + 1);
          colName[i][j] = resultSetMetaData.getColumnName(j + 1);
          j++;
        }
        i++;
      }

      Class.forName("org.postgresql.Driver");

      databaseName = "corp";

      Connection conn2 = DriverManager.getConnection(
          "jdbc:postgresql://corp.cbrhhaoqnjwg.ap-southeast-1.rds.amazonaws.com/corp", "corp",
          "8NUbsKi77OSwQvRvtmXF");

      ResultSet resultSetPost = conn2.getMetaData()
          .getTables(databaseName, null, "%", types);
      String[] tableNamePost = new String[2000];
      i = 0;
      int tableCountPost = 0;
      int[] colCountPost = new int[300];
      String[][] colTypePost = new String[300][300];
      String[][] colNamePost = new String[400][400];
      while (resultSetPost.next()) {
        tableNamePost[i] = resultSetPost.getString(3);
        i++;
      }
      tableCountPost = i;
      i = 0;
      while (i < tableCountPost) {
        PreparedStatement ps;
        if (tableNamePost[i].equals("order")) {
          ps = conn2.prepareStatement("select * from \"order\" limit 1");
        } else {
          ps = conn2.prepareStatement("select * from " + tableNamePost[i] + " limit 1");
        }
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData resultSetMetaDataPost = rs.getMetaData();
        colCountPost[i] = resultSetMetaDataPost.getColumnCount();
        int j = 0;
        while (j < colCountPost[i]) {
          colTypePost[i][j] = resultSetMetaDataPost.getColumnTypeName(j + 1);
          colNamePost[i][j] = resultSetMetaDataPost.getColumnName(j + 1);
          j++;
        }
        i++;
      }
      resultSetPost.close();
      conn2.close();

      System.out.println(
          "Table count\n MySql - " + tableCount + " \n Postgres - " + tableCountPost + "\n");

      for (int k = 0; k < tableCount; k++) {
        for (int l = 0; l < tableCountPost; l++) {
          if (tableName[k].equals(tableNamePost[l])) {
            System.out.println(
                "Table MySQl - " + tableName[k] + "\n");
            System.out.println(
                "Col count MySql - " + colCount[k] + " col count post - "
                    + colCountPost[l]
                    + "\n");
            int x = Math.min(colCount[k], colCountPost[l]);
            for (int y = 0; y < x; y++) {
              String x1 = colType[k][y].toLowerCase();
              String x2 = colTypePost[l][y].toLowerCase();
              if (!x1.equals(x2)) {
                System.out.println(
                    "Type Not same for col - " + colName[k][y] + "\nMysql-" + colType[k][y]
                        + "  Post-" + colTypePost[l][y] + "\n");
              }
            }
            System.out.println("--------------------------------------------");
            break;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
