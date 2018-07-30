package org.shinthirty.klotski.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Grids of a sliding puzzle.
 *
 * @author shinthirty
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Grid extends Bitboard {

  /**
   * Horizontal coordinates of the grid.
   */
  private int positionX;

  /**
   * Vertical coordinates of the grid.
   */
  private int positionY;

  /**
   * Create a grid with coordinate (x, y).
   *
   * @param x    Horizontal coordinate
   * @param y    Vertical coordinate
   */
  public Grid(final int x, final int y) {
    super(toValue(x, y));
    positionX = x;
    positionY = y;
  }
}
