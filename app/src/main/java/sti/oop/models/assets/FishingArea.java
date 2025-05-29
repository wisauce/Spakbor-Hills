package sti.oop.models.assets;

import sti.oop.action.Action;
import sti.oop.models.Interactable;

public class FishingArea extends Asset implements Interactable {

  public FishingArea(int x, int y, int w, int h ,boolean collision) {
    super(x, y, w, h, collision);
  }

  @Override
  public void accept(Action action) {
    
  }
  
}
