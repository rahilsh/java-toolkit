package in.jasper;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JasperUtil {

  protected Map params = new HashMap();

  public Map getParams() {
    return params;
  }

  protected JasperPrint jp;
  protected JasperReport jr;
  protected DynamicReport dr;

  public DynamicReport buildReport() throws Exception {
    /** Creates the DynamicReportBuilder and sets the basic options for the report */
    FastReportBuilder drb = new FastReportBuilder();
    drb.addColumn("Id", "id", Integer.class.getName(), 30)
        .addColumn("name", "name", String.class.getName(), 30)
        .addColumn("dept", "dept", String.class.getName(), 50)
        .addColumn("salary", "salary", Integer.class.getName(), 50)
        .setTitle("employee")
        .setQuery(
            "select * from employee where name like $P{start}", DJConstants.QUERY_LANGUAGE_SQL)
        // .setQuery("select * from customer", DJConstants.QUERY_LANGUAGE_SQL)
        .setTemplateFile("templates/TemplateReportTest.jrxml")
        .setUseFullPageWidth(true);

    DynamicReport dr = drb.build();

    // Note that the query has a parameter, by putting in the map
    // an item with the proper key, it will be automatically registered as a parameter
    params.put("start", "%a%");

    return dr;
  }

  public static void main(String[] args) throws Exception {
    JasperUtil test = new JasperUtil();
    test.testReport();
    // JasperViewer.viewReport(test.jp);	//finally display the report report
    //			JasperDesignViewer.viewReportDesign(jr);

    String jrxml = JRXmlWriter.writeReport(test.jr, "UTF-8");
    try (PrintStream out = new PrintStream(new FileOutputStream("test.jrxml"))) {
      out.print(jrxml);
    }
    System.out.println(jrxml);
  }

  public void testReport() throws Exception {
    Connection con = null;
    try {
      dr = buildReport();
      // con = createSQLConnection();
      // jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), con,params );
      // ReportExporter.exportReport(jp, System.getProperty("employee.dir")+
      // "/target/"+this.getClass().getName()+".pdf");
      jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), params);
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        con.close();
      } catch (Exception e1) {
      }
    }
  }

  public static Connection createSQLConnection() throws ClassNotFoundException, SQLException {
    String hostName = "localhost";
    String dbName = "sparta_db";
    String userName = "root";
    String password = "";
    return getMySQLConnection(hostName, dbName, userName, password);
  }

  public static Connection getMySQLConnection(
      String hostName, String dbName, String userName, String password)
      throws SQLException, ClassNotFoundException {

    // Declare the class Driver for MySQL DB
    // This is necessary with Java 5 (or older)
    // Java6 (or newer) automatically find the appropriate driver.
    // If you use Java> 5, then this line is not needed.
    Class.forName("com.mysql.jdbc.Driver");

    // Cấu trúc URL Connection dành cho Oracle
    // Ví dụ: jdbc:mysql://localhost:3306/simplehr
    String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

    Connection conn = DriverManager.getConnection(connectionURL, userName, password);
    return conn;
  }
}
