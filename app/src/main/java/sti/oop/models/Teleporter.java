package sti.oop.models;
import sti.oop.controllers.GameMapController.MapName;

public class Teleporter extends Asset implements Interactable{
  MapName destination;

  public Teleporter(int x, int y, int w, int h, MapName destination) {
    super(x, y, w, h, false);
    this.destination = destination;
  }

  public Teleporter(int x, int y, MapName destination) {
    super(x, y, false);
    this.destination = destination;
  }

  public void accept(Action action) {
    action.act(this);
  }

  public MapName getDestination() {
    return destination;
  }
  
}
