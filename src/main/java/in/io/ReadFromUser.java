package in.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFromUser {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter =
        new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String entryTime = bufferedReader.readLine();

    String leavingTime = bufferedReader.readLine();

    int res = 1; // findParkingPrice(entryTime, leavingTime);

    bufferedWriter.write(String.valueOf(res));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}
