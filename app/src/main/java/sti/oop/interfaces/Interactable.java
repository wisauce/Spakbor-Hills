package sti.oop.interfaces;

import javafx.scene.shape.Rectangle;
import sti.oop.models.Action;

public interface Interactable {
  public void accept(Action action);
}