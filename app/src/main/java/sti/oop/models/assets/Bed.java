package sti.oop.models.assets;

import sti.oop.utils.Constants;

public class Bed extends Asset {
  public Bed(int x, int y) {
    super(x, y, 2 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, "/assets/bed.png", true);
  }
}
