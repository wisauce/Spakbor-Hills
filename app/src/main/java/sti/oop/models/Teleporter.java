package sti.oop.models;
import javafx.scene.shape.Rectangle;
import sti.oop.utils.Constants;

public class Teleporter extends Asset implements Interactable{

  public Teleporter(int x, int y, int w, int h) {
    super(x, y, x, y);
  }

  public Teleporter(int x, int y, String imageSrc) {
    super(x, y, imageSrc);
  }

  public Teleporter(int x, int y, String imageSrc, int w, int h) {
    super(x, y, imageSrc, w, h);
  }

  public void accept(Action action) {
    action.visit(this);
  }
}
