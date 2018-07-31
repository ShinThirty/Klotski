package org.shinthirty.klotski.models;

/**
 * Possible moving directions of blocks.
 *
 * @author shinthirty
 */
public enum Direction {
  UP (0, -1),
  RIGHT (1, 0),
  DOWN (0, 1),
  LEFT (-1, 0);

  /**
   * Horizontal move amount.
   */
  public final int x;

  /**
   * Vertical move amount.
   */
  public final int y;

  /**
   * Constructor.
   *
   * @param x    Horizontal move amount
   * @param y    Vertical move amount
   */
  Direction(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

}
