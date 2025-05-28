package sti.oop.models;
import java.util.Map;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.interfaces.Interactable;
import sti.oop.utils.Constants;

public class Teleporter extends Asset implements Interactable{
  MapName destination;

  public Teleporter(int x, int y, int w, int h, MapName destination) {
    super(x, y, w, h);
    this.destination = destination;
  }

  public Teleporter(int x, int y, MapName destination) {
    super(x, y);
    this.destination = destination;
  }

  public Teleporter(int x, int y, String imageSrc, MapName destination) {
    super(x, y, imageSrc);
    this.destination = destination;
  }

  public Teleporter(int x, int y, String imageSrc, int w, int h, MapName destination) {
    super(x, y, imageSrc, w, h);
    this.destination = destination;
  }

  public void accept(Action action) {
    action.act(this);
  }

  public MapName getDestination() {
    return destination;
  }

  
}
