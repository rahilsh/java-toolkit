package in.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuffer output = new StringBuffer();
		Process p;
		BufferedReader reader = null;
		String[] cmd = {"/bin/sh", "-c", "cd /Users/rahil.r/Documents/repo/jasper-reports ; git pull"};
		
		try {
			System.out.println(checkIfFileExists("/Users/rahil.r/Documents/repo/jasper-reports/reports/test/MasterkeyReport_5Columns.jrxml"));
			p = Runtime.getRuntime().exec(cmd);
			int exitValue = p.waitFor();
			if (exitValue != 0) {
				reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			} else {
				reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			}
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			String sOutput = output.toString();
			System.out.println(sOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static  boolean checkIfFileExists(String filePath) {
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
          return true;
        }
        return false;
    }

}
