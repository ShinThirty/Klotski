package org.shinthirty.klotski.models;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Model for the Klotski board.
 *
 * @author shinthirty
 */
@Data
public class KlotskiBoard {

  /**
   * Target block name.
   */
  public static String target;

  /**
   * Target block state.
   */
  public static Block targetBlock;

  /**
   * Top row.
   */
  public static Bitboard top;

  /**
   * Right column.
   */
  public static Bitboard right;

  /**
   * Bottom row.
   */
  public static Bitboard bottom;

  /**
   * Left column.
   */
  public static Bitboard left;

  /**
   * Hash code for the Klotski board.
   */
  private int hash;

  /**
   * Current state of the puzzle represented by a collection of blocks.
   */
  private Map<String, Block> blocks;

  /**
   * Constructor.
   */
  private KlotskiBoard() {
    hash = 0;
    blocks = new HashMap<>();
  }

  /**
   * Check if the puzzle has been solved.
   *
   * @return    {@link Boolean} indicates the state of puzzle
   */
  public boolean isSolved() {
    return targetBlock.equals(blocks.get(target));
  }

  /**
   * Parse the following string to a {@link KlotskiBoard}
   *
   * 4 3
   * A A C .
   * A B C .
   * B B . .
   * B
   * 1 0
   *
   * Two integers, x and y, are given in the first line, where x is the number of columns and y is
   * the number of rows.
   * In the following y lines, each line contains x strings separated by spaces.
   * Each character in the string describes one cell of the Klotski puzzle.
   * A string consists only of '.'s (e.g. ".", "..."), which stands for empty cells, while other
   * strings encode blocks.
   * The next line contains one string, which indicates the target block.
   * The last line contains a coordinate that specifies the place where the target block should be
   * moved to. This coordinate is the top-left corner where the target block should be placed.
   *
   * @param configuration    Klotski puzzle configuration
   * @return                 {@link KlotskiBoard}
   */
  public static KlotskiBoard parse(final String configuration) {
    KlotskiBoard klotskiBoard = new KlotskiBoard();
    Map<String, Block> blocks = klotskiBoard.getBlocks();

    InputStream is = new ByteArrayInputStream(configuration.getBytes(StandardCharsets.UTF_8));

    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      String line = br.readLine();
      String[] size = line.split(" ");
      Bitboard.width = Integer.valueOf(size[0]);
      Bitboard.height = Integer.valueOf(size[1]);

      top = Bitboard.draw(0, 0, Bitboard.width, 1);
      right = Bitboard.draw(Bitboard.width - 1, 0, 1, Bitboard.height);
      bottom = Bitboard.draw(0, Bitboard.height - 1, Bitboard.width, 1);
      left = Bitboard.draw(0, 0, 1, Bitboard.height);

      for (int i = 0; i < Bitboard.height; i++) {
        line = br.readLine();
        String[] parts = line.split(" ");
        for (int j = 0; j < Bitboard.width; j++) {
          String name = parts[j];
          if (!".".equals(name)) {
            if (!blocks.containsKey(name)) {
              blocks.put(name, new Block());
            }

            Grid grid = new Grid(j, i);
            blocks.get(name).addGrid(grid);
          }
        }
      }

      target = br.readLine();
      line = br.readLine();
      String[] targetPosition = line.split(" ");
      int targetPositionX = Integer.valueOf(targetPosition[0]);
      int targetPositionY = Integer.valueOf(targetPosition[1]);

      targetBlock = new Block(blocks.get(target));
      targetBlock.move(targetPositionX - targetBlock.getPositionX(),
          targetPositionY - targetBlock.getPositionY());
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return klotskiBoard;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    KlotskiBoard other = (KlotskiBoard) obj;
    if (blocks.size() != other.getBlocks().size()) {
      return false;
    }

    for (String name : blocks.keySet()) {
      if (!blocks.get(name).equals(other.getBlocks().get(name))) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    int h = hash;
    if (h == 0) {
      for (Block block : blocks.values()) {
        h = 31 * h + (block == null ? 0 : block.hashCode());
      }

      hash = h;
    }

    return h;
  }
}
