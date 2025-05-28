package sti.oop.interfaces;

import javafx.scene.shape.Rectangle;
import sti.oop.action.Action;

public interface Interactable {
  public void accept(Action action);
}