package sti.oop.models.assets;


import sti.oop.models.Interactable;
import sti.oop.action.Action;

public class SleepingArea extends Asset implements Interactable {
  private int spawnAreaX;
  private int spawnAreaY;

  public SleepingArea(int x, int y, int w, int h, int spawnAreaX, int spawnAreaY) {
    super(x, y, w, h, false);
    this.spawnAreaX = spawnAreaX;
    this.spawnAreaY = spawnAreaY;
  }

  @Override
  public String accept(Action action) {
    return action.act(this);
  }

  public int getSpawnAreaX() {
    return spawnAreaX;
  }

  public int getSpawnAreaY() {
    return spawnAreaY;
  }

  
}
