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
  KlotskiSolver(final String configuration, final String outputFile) {
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
  private Collection<KlotskiBoard> nextBoards(final KlotskiBoard current) {
    List<KlotskiBoard> nextBoards = new ArrayList<>();

    for (String name : current.getBlocks().keySet()) {
      findNextBoards(current, name, nextBoards);
    }

    nextBoards.forEach(nextBoard -> nextBoard.setPrev(current));
    return nextBoards;
  }

  /**
   * Find out all next klotski boards reachable by moving the named block from the current klotski
   * board.
   *
   * @param current       Current klotski board
   * @param name          Name of the block to be moved
   * @param nextBoards    Reachable next boards
   */
  private void findNextBoards(final KlotskiBoard current, final String name,
      final List<KlotskiBoard> nextBoards) {
    List<KlotskiBoard> nexts = new ArrayList<>();
    for (Direction direction : Direction.values()) {
      if (current.canMove(name, direction)) {
        KlotskiBoard next = current.move(name, direction);
        if (!visited.contains(next.hash())) {
          nexts.add(next);
          nextBoards.add(next);
          visited.add(next.hash());
        }
      }
    }

    nexts.forEach(next -> findNextBoards(next, name, nextBoards));
  }

  /**
   * Solve the puzzle and display the steps.
   *
   */
  void solve() {
    KlotskiBoard current;

    int total = 0;
    int numberOfSteps = 0;
    while (!unvisited.isEmpty()) {
      current = unvisited.poll();
      total++;
      if (current.isSolved()) {
        numberOfSteps = generateSolution(current);
        break;
      }

      unvisited.addAll(nextBoards(current));
    }

    System.out.format("%d steps, %d explored%n", numberOfSteps, total);
  }

  /**
   * Generate the solution steps.
   *
   * @param solution    Solved KlotskiBoard
   */
  private int generateSolution(final KlotskiBoard solution) {
    Deque<KlotskiBoard> steps = new ArrayDeque<>();

    KlotskiBoard current = solution;
    while (current != null) {
      steps.push(current);
      current = current.getPrev();
    }

    int numberOfSteps = steps.size() - 1;

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

    return numberOfSteps;
  }

}
