package sti.oop.models;

import sti.oop.action.Action;

public interface Interactable {
  public String accept(Action action);
  public boolean multipleInput();
}