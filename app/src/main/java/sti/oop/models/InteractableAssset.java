package sti.oop.models;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.PlayerController;

public abstract class InteractableAssset extends Asset {
  private Rectangle interactionArea;
  
  public InteractableAssset(int x, int y, String imageSrc) {
    super(x, y, imageSrc);
    interactionArea = getSolidArea();
  }

  public Rectangle getInteractionArea() {
    return interactionArea;
  }

  public void accept(Action action) {};
  
}
