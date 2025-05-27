package sti.oop.models;

import sti.oop.utils.Constants;

public class MarryArea extends Asset implements Interactable {
  public MarryArea() {
    super(20 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE, "/images/monyet.jpg");
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  }
}
