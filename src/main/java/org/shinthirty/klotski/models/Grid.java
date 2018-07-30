package org.shinthirty.klotski.models;

/**
 * Grids of a sliding puzzle.
 *
 * @author shinthirty
 */
public class Grid extends Bitboard {

  /**
   * Create a grid with coordinate (x, y).
   *
   * @param x    Horizontal coordinate
   * @param y    Vertical coordinate
   */
  public Grid(final int x, final int y) {
    super(toValue(x, y));
  }
}
