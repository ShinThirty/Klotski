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
  public boolean overlap(final Bitboard other) {
    return (value & other.getValue()) != 0;
  }

  /**
   * Convert coordinates to bitboard value.
   *
   * @param x    Horizontal coordinate
   * @param y    Vertical coordinate
   * @return     Bitboard value
   */
  public static int toValue(final int x, final int y) {
    return 1 << getIndex(x, y);
  }

  /**
   * Convert coordinates to bitboard index.
   *
   * @param x    Horizontal coordinate
   * @param y    Vertical coordinate
   * @return     Bitboard index
   */
  public static int getIndex(final int x, final int y) {
    return x + y * width;
  }

  /**
   * Combine a collection of bitboard to form a new bitboard.
   *
   * @param boards    Bitboards to be combined.
   * @return          The combined bitboard value.
   */
  public static int combine(final Collection<? extends Bitboard> boards) {
    int value = 0;
    for (Bitboard board : boards) {
      value |= board.getValue();
    }

    return value;
  }

}
