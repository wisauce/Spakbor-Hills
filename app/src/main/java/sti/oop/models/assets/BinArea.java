package sti.oop.models.assets;


import sti.oop.action.Action;
import sti.oop.models.Interactable;

public class BinArea extends Asset implements Interactable {
  private int spawnAreaX;
  private int spawnAreaY;

  public BinArea(int x, int y, int w, int h) {
    super(x, y, w, h, false);
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  }

  public int getSpawnAreaX() {
    return spawnAreaX;
  }

  public int getSpawnAreaY() {
    return spawnAreaY;
  }

  @Override
  public boolean multipleInput() {
    return true;
  }

  
}
