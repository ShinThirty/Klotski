package org.shinthirty.klotski;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.shinthirty.klotski.models.Direction;
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
   * Visited set.
   */
  private Set<KlotskiBoard> visited;

  /**
   * Container for searching the state-space.
   */
  private Queue<KlotskiBoard> queue;

  /**
   * Constructor.
   *
   * @param configuration    Initial configuration of klotski
   */
  public KlotskiSolver(String configuration) {
    puzzle = KlotskiBoard.parse(configuration);
    visited = new HashSet<>(65536);
    visited.add(puzzle);
    queue = new LinkedList<>();
    queue.add(puzzle);
  }

  /**
   * Generate the next possible puzzle boards from the current puzzle board using legal moves.
   *
   * @param current    Current puzzle board
   * @return           Next possible puzzle boards
   */
  private Collection<KlotskiBoard> nextBoards(KlotskiBoard current) {
    List<KlotskiBoard> nextBoards = new ArrayList<>();

    for (String name : current.getBlocks().keySet()) {
      for (Direction direction : Direction.values()) {
        if (current.canMove(name, direction)) {
          nextBoards.add(current.move(name, direction));
        }
      }
    }

    return nextBoards;
  }

  /**
   * Solve the puzzle and display the steps.
   */
  public void solve() {

  }

}
