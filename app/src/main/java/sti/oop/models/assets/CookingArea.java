package sti.oop.models.assets;


import sti.oop.models.Interactable;
import sti.oop.action.Action;

public class CookingArea extends Asset implements Interactable {
  String choosenCook = "";
  public CookingArea(int x, int y, int w, int h) {
    super(x, y, w, h, false);
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  }

  public String getChoosenCook() {
    return choosenCook;
  }

  public void setChoosenCook(String choosenCook) {
    this.choosenCook = choosenCook;
  }

  @Override
  public boolean multipleInput() {
    return false;
  }

  
}
