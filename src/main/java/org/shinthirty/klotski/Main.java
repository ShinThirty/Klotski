package org.shinthirty.klotski;

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
public class Main {

  /**
   * Entry point.
   *
   * @param args    Command line arguments
   */
  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream("puzzle.txt"), StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    KlotskiSolver solver = new KlotskiSolver(sb.toString());
    solver.solve();
  }

}
