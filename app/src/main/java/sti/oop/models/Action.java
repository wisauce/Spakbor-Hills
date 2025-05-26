package sti.oop.models;

import sti.oop.controllers.CollisionController;
import sti.oop.controllers.GameMapController;
import sti.oop.controllers.GameMapController.MapName;

public class Action {
  GameMapController gameMapController;
  CollisionController collisionController;

  public Action(GameMapController gameMapController, CollisionController collisionController) {
    this.gameMapController = gameMapController;
    this.collisionController = collisionController;
  }

  public void visit(Teleporter visited) {
    gameMapController.setCurrentMap(MapName.HOUSE);
    collisionController.setCurrentCollisionMap(MapName.HOUSE);
  }
}
