package in.zeta.jasper;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class JasperTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("FirstJasperReport.pdf");
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
