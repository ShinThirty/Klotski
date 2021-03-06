package org.shinthirty.klotski.models;

import java.util.Collection;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Bit board representation for sliding puzzles and sliding blocks.
 *
 * @author shinthirty
 */
@Data
@RequiredArgsConstructor
public class Bitboard {

  /**
   * Bitboard width.
   */
  public static int width;

  /**
   * Bitboard height.
   */
  public static int height;

  /**
   * Bitboard value.
   */
  @NonNull
  private int value;

  /**
   * Determine whether this bitboard overlaps another one.
   *
   * @param other    {@link Bitboard} object
   * @return         {@link Boolean} true if overlap occurs
   */
  boolean overlap(final Bitboard other) {
    return (value & other.getValue()) != 0;
  }

  /**
   * Convert coordinates to bitboard value.
   *
   * @param x    Horizontal coordinate
   * @param y    Vertical coordinate
   * @return     Bitboard value
   */
  static int toValue(final int x, final int y) {
    return 1 << getIndex(x, y);
  }

  /**
   * Convert coordinates to bitboard index.
   *
   * @param x    Horizontal coordinate
   * @param y    Vertical coordinate
   * @return     Bitboard index
   */
  static int getIndex(final int x, final int y) {
    return x + y * width;
  }

  /**
   * Combine a collection of bitboard to form a new bitboard.
   *
   * @param boards    Bitboards to be combined.
   * @return          The combined bitboard value.
   */
  static int combine(final Collection<? extends Bitboard> boards) {
    int value = 0;
    for (Bitboard board : boards) {
      value |= board.getValue();
    }

    return value;
  }

  /**
   * Construct a bitboard with 1s assigned to the area defined by the following conditions:
   * 1. Top-left corner is at (x,y)
   * 2. Width and height of the area is defined by input parameter width and height
   *
   * @param x        Horizontal position of the area
   * @param y        Vertical position of the area
   * @param width    Width of the area
   * @param height   Height of the area
   * @return
   */
  static Bitboard draw(final int x, final int y, final int width, final int height) {
    int value = 0;
    for (int i = x; i < x + width; i++) {
      for (int j = y; j < y + height; j++) {
        value |= toValue(i, j);
      }
    }

    return new Bitboard(value);
  }

}
