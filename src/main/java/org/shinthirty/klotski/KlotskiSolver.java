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
class KlotskiSolver {

  /**
   * Visited set.
   */
  private Set<Long> visited;

  /**
   * Unvisited queue.
   */
  private Deque<KlotskiBoard> unvisited;

  /**
   * Output file path.
   */
  private String outputFile;

  /**
   * Constructor.
   *
   * @param configuration    Initial configuration of klotski
   * @param outputFile       Output file path
   */
  KlotskiSolver(String configuration, String outputFile) {
    visited = new HashSet<>(65536);
    unvisited = new ArrayDeque<>();

    KlotskiBoard puzzle = KlotskiBoard.parse(configuration);
    unvisited.add(puzzle);
    visited.add(puzzle.hash());
    this.outputFile = outputFile;
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
  void solve() {
    KlotskiBoard current;

    while (!unvisited.isEmpty()) {
      current = unvisited.poll();
      if (current.isSolved()) {
        generateSolution(current);
        break;
      }

      Collection<KlotskiBoard> nextPuzzles = nextBoards(current);
      nextPuzzles.forEach(puzzle -> {
        if (!visited.contains(puzzle.hash())) {
          unvisited.add(puzzle);
          visited.add(puzzle.hash());
        }
      });
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
        new FileOutputStream(outputFile), StandardCharsets.UTF_8)))) {
      int step = 1;
      pw.print("Solution\n");

      while (!steps.isEmpty()) {
        current = steps.pop();

        pw.format("%d.\n%s\n", step, current.toString());
        step++;
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

}
