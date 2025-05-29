package sti.oop.models.assets;
import sti.oop.action.Action;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Interactable;

public class Teleporter extends Asset implements Interactable, EnergyConsuming {
  MapName destination;
  int energyRequired;
  int destinationX;
  int destinationY;

  public Teleporter(int x, int y, int w, int h, MapName destination, int destinationX, int destinationY) {
    super(x, y, w, h, false);
    this.destination = destination;
    this.destinationX = destinationX;
    this.destinationY = destinationY;
  }

  public Teleporter(int x, int y, MapName destination, int destinationX, int destinationY) {
    super(x, y, false);
    this.destination = destination;
    this.destinationY = destinationY;
    this.destinationX = destinationX;
  }

  public String accept(Action action) {
    return action.act(this);
  }

  public MapName getDestination() {
    return destination;
  }

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }

  public int getDestinationX() {
    return destinationX;
  }

  public int getDestinationY() {
    return destinationY;
  }

  @Override
  public boolean multipleInput() {
    return false;
  }
 
  
}
