package sti.oop.models.assets;


import java.util.List;

import sti.oop.action.Action;
import sti.oop.interfaces.MultipleOptions;
import sti.oop.models.Interactable;

public class CookingArea extends Asset implements Interactable, MultipleOptions {

  public CookingArea(int x, int y, int w, int h) {
    super(x, y, w, h, false);
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  }

  @Override
  public boolean multipleInput() {
    return false;
  }

  @Override
  public List<String> getOptions() {
    return List.of("FishnChips", "Baguette", "Sashimi", "Fugu", "Wine", "Pumpkin Pie", "Veggie Soup", "Fish Stew", "Spakbor Salad", "Fish Sandwich", "The Legends of Spakbor");
  }
}
