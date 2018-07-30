package org.shinthirty.klotski.models;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Blocks of the sliding puzzle. A block's size and position are define by the underlying bitboard
 * value.
 *
 * @author shinthirty
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Block extends Bitboard {

  /**
   * Name of the block.
   */
  private String name;

  /**
   * Combine grids to form a new block.
   *
   * @param grids    Collection of grids to form the new block.
   */
  public Block(Collection<Grid> grids) {
    super(combine(grids));
  }

  /**
   * Attempt move myself x units right and y units bottom.
   *
   * @param x    Distance towards right
   * @param y    Distance towards bottom
   * @return     New block position represented by the bitboard value.
   */
  public int attemptMove(final int x, final int y) {
    int shift = toValue(x, y);
    if (shift >= 0) {
      return getValue() << shift;
    } else {
      return getValue() >>> -shift;
    }
  }
}
