package in.jasper;

import in.connection.ConnectionUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JasperTest {

  public static void main(String[] args) {


    JRDesignQuery query = new JRDesignQuery();
    query.setText("select * from Employee");

    JasperDesign jd = new JasperDesign();
    jd.setName("Test name");
    jd.setQuery(query);

    JRDesignField field = new JRDesignField();
    field.setName("name");
    field.setValueClassName("java.lang.String");

    try {
      jd.addField(field);

      JRDesignBand detail = new JRDesignBand();
      detail.setHeight(16); // in points, 1/72 of an inch

      JRDesignExpression expression = new JRDesignExpression();

      JRDesignTextField tf = new JRDesignTextField();
      tf.setAnchorNameExpression(expression); // set its various atributes
      detail.addElement(tf); // add the field to the band
      /*DynamicJasperDesign djd=new DynamicJasperDesign
      					djd.setDetail(detail); // set the band as report detail section
      */
      JasperReport jasperReport = JasperCompileManager.compileReport(jd);

      Map parameters = new HashMap();
      parameters.put("ReportTitle", "Basic JasperReport");
      parameters.put("MaxSalary", new Double(25000.00));
      // Third, get a database connection
      Connection conn = ConnectionUtils.getConnection();

      // Fourth, create JasperPrint using fillReport() method
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
      // Make sure the output directory exists.

      File outDir = new File("\\Users\\rahil.r\\Documents\\");
      outDir.mkdirs();

      // PDF Exportor.
      JRPdfExporter exporter = new JRPdfExporter();

      ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
      // ExporterInput
      exporter.setExporterInput(exporterInput);

      // ExporterOutput
      OutputStreamExporterOutput exporterOutput =
          new SimpleOutputStreamExporterOutput("FirstJasperReport.pdf");
      // Output
      exporter.setExporterOutput(exporterOutput);

      //
      SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
      exporter.setConfiguration(configuration);
      exporter.exportReport();

      System.out.print("Done!");
    } catch (JRException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
