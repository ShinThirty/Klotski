package org.shinthirty.klotski;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
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
   * Visited set.
   */
  private Set<KlotskiBoard> visited;

  /**
   * Unvisited queue.
   */
  private Deque<KlotskiBoard> unvisited;

  /**
   * Constructor.
   *
   * @param configuration    Initial configuration of klotski
   */
  public KlotskiSolver(String configuration) {
    visited = new HashSet<>(65536);
    unvisited = new ArrayDeque<>();
    unvisited.add(KlotskiBoard.parse(configuration));
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
    KlotskiBoard current;

    while (!unvisited.isEmpty()) {
      current = unvisited.poll();

      if (current.isSolved()) {
        generateSolution(current);
        break;
      }

      Collection<KlotskiBoard> nextPuzzles = nextBoards(current);
      nextPuzzles.forEach(puzzle -> {
        if (!visited.contains(puzzle)) {
          unvisited.add(puzzle);
        }
      });

      visited.add(current);
    }
  }

  /**
   * Generate the solution steps.
   *
   * @param solution    Solved KlotskiBoard
   */
  private void generateSolution(KlotskiBoard solution) {
    Deque<KlotskiBoard> steps = new ArrayDeque<>();

    KlotskiBoard current = solution;
    while (current != null) {
      steps.push(current);
      current = current.getPrev();
    }

    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream("solution.txt"), StandardCharsets.UTF_8)))) {
      int step = 1;
      pw.println("Solution");
      pw.println();

      while (!steps.isEmpty()) {
        current = steps.pop();

        pw.format("%d.\n", step);
        pw.println(current.toString());
        pw.println();
        step++;
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

}
