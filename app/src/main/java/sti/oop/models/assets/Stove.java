package sti.oop.models.assets;

import sti.oop.utils.Constants;

public class Stove extends Asset {
  public Stove(int x, int y) {
    super(x, y, 1 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE, "/images/monyet.jpg", true);
  }
}
