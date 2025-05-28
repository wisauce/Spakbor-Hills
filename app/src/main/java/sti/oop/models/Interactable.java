package sti.oop.models;

import javafx.scene.shape.Rectangle;
import sti.oop.action.Action;

public interface Interactable {
  public void accept(Action action);
}