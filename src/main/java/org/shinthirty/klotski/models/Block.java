package org.shinthirty.klotski.models;

import lombok.Data;

/**
 * Blocks of the sliding puzzle. A block's size and position are define by the underlying bitboard
 * value.
 *
 * @author shinthirty
 */
@Data
public class Block extends Bitboard {

  /**
   * Horizontal coordinates of the leftmost part of the block.
   */
  private int positionX;

  /**
   * Vertical coordinates of the uppermost part of the block.
   */
  private int positionY;

  /**
   * Create an empty block.
   */
  Block() {
    super(0);
    positionX = Integer.MAX_VALUE;
    positionY = Integer.MAX_VALUE;
  }

  /**
   * Copy constructor.
   *
   * @param other    Another block
   */
  Block(final Block other) {
    super(other.getValue());
    positionX = other.getPositionX();
    positionY = other.getPositionY();
  }

  /**
   * Add a grid to a block.
   *
   * @param grid    Grid to be added
   */
  void addGrid(Grid grid) {
    setValue(getValue() | grid.getValue());
    if (grid.getPositionX() < positionX) {
      positionX = grid.getPositionX();
    }
    if (grid.getPositionY() < positionY) {
      positionY = grid.getPositionY();
    }
  }

  /**
   * Attempt to move myself x units right and y units bottom.
   *
   * @param x    Distance towards right
   * @param y    Distance towards bottom
   * @return     New block position represented by the bitboard value.
   */
  private int attemptMove(final int x, final int y) {
    int shift = getIndex(x, y);
    if (shift >= 0) {
      return getValue() << shift;
    } else {
      return getValue() >>> -shift;
    }
  }

  /**
   * Attempt to move myself towards a certain direction.
   *
   * @param direction    Direction
   * @return             New block position represented by the bitboard value.
   */
  int attemptMove(final Direction direction) {
    return attemptMove(direction.x, direction.y);
  }

  /**
   * Move myself x units right and y units bottom.
   *
   * @param x    Distance towards right
   * @param y    Distance towards bottom
   * @return     New block position represented by the bitboard value.
   */
  void move(final int x, final int y) {
    int value = attemptMove(x, y);
    positionX += x;
    positionY += y;
    setValue(value);
  }

  /**
   * Move myself towards a certain direction.
   *
   * @param direction    Direction
   */
  void move(final Direction direction) {
    move(direction.x, direction.y);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!Block.class.isAssignableFrom(obj.getClass())) {
      return false;
    }

    final Block other = (Block) obj;

    return getValue() == other.getValue();
  }

  @Override
  public int hashCode() {
    return getValue();
  }
}
