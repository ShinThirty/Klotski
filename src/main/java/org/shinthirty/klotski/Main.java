package org.shinthirty.klotski;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Entry point.
 *
 * @author shinthirty
 */
public class Main implements Runnable {

  @Parameter(names = { "-i", "--inputFile" }, description = "Path of input file")
  private String inputFile;

  @Parameter(names = { "-o", "--outputFile" }, description = "Path of output file")
  private String outputFile;

  /**
   * Entry point.
   *
   * @param args    Command line arguments
   */
  public static void main(String[] args) {
    Main main = new Main();
    JCommander.newBuilder()
        .addObject(main)
        .build()
        .parse(args);

    main.run();
  }

  @Override
  public void run() {
    StringBuilder sb = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(inputFile), StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    KlotskiSolver solver = new KlotskiSolver(sb.toString(), outputFile);
    solver.solve();
  }

}
