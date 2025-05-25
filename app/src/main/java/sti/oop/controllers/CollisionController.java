package sti.oop.controllers;

import java.util.Map;

import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.CollisionMap;
import sti.oop.utils.Constants;

// import sti.oop.models.Constants;

public class CollisionController {
  private Map<MapName, CollisionMap> mapOfCollisionMaps;
  private CollisionMap currentCollisionMap;

  public CollisionController() {
    mapOfCollisionMaps = Map.ofEntries(
      Map.entry(MapName.FARM, new CollisionMap("/maps/farmCollision.txt")),
      Map.entry(MapName.HOUSE, new CollisionMap("/maps/houseCollision.txt"))
    );
    currentCollisionMap = mapOfCollisionMaps.get(MapName.FARM);
  }

  public boolean isCollision(int x, int y) {
    int tileX = x / Constants.TILE_SIZE;
    int tileY = y / Constants.TILE_SIZE;
    return currentCollisionMap.getCollisionMatrix().get(tileY).get(tileX);
  }

  public void setCurrentCollisionMap(MapName mapName) {
    currentCollisionMap = mapOfCollisionMaps.get(mapName);
  }
}