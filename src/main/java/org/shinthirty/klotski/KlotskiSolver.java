package org.shinthirty.klotski;

import org.shinthirty.klotski.models.KlotskiBoard;

/**
 * Klotski solver.
 *
 * @author shinthirty
 */
public class KlotskiSolver {

  /**
   * Klotski puzzle to be solved.
   */
  private KlotskiBoard puzzle;

  /**
   * Constructor.
   *
   * @param configuration    Initial configuration of klotski
   */
  public KlotskiSolver(String configuration) {
    puzzle = KlotskiBoard.parse(configuration);
  }

  /**
   * Solve the puzzle and display the steps.
   */
  public void solve() {
    // TODO
  }

}
