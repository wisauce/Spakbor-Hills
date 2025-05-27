package sti.oop.models;

import sti.oop.controllers.FarmController;

public class Action implements Actor {
  FarmController farmController;

  public Action(FarmController farmController) {
    this.farmController = farmController;
  }

  public void act(Teleporter visited) {
    farmController.changeMap(visited.getDestination());
  }

  @Override
  public void act(MarryArea acted) {
    
  }
}