package sti.oop.models.assets;

import java.util.List;

import sti.oop.action.Action;
import sti.oop.models.Interactable;
import sti.oop.models.item.Fish;

public class FishingArea extends Asset implements Interactable {
  private List<Fish> fishes;

  public FishingArea(int x, int y, int w, int h ,List<Fish> fishes) {
    super(x, y, w, h, false);
    this.fishes = fishes;
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  }

  @Override
  public boolean multipleInput() {
    return false;
  }

  public List<Fish> getFishes() {
    return fishes;
  }
}
