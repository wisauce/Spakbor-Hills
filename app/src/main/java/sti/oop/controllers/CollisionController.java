package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Asset;
import sti.oop.models.CollisionMap;
import sti.oop.models.Teleporter;
import sti.oop.utils.Constants;

// import sti.oop.models.Constants;

public class CollisionController {
  private Map<MapName, CollisionMap> mapOfCollisionMaps;
  private CollisionMap currentCollisionMap;
  private List<Asset> assets;

  public CollisionController(List<Asset> assets) {
    this.assets = assets;
    mapOfCollisionMaps = Map.ofEntries(
        Map.entry(MapName.FARM, new CollisionMap("/maps/farmCollision.txt")),
        Map.entry(MapName.HOUSE, new CollisionMap("/maps/houseCollision.txt")));
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

  public void checkAssetCollision(PlayerController playerController) {
    // Reset collision flag before checking
    playerController.setnoCollidingAsset(true);
    playerController.inInteractiveArea(false);
    for (Asset asset : assets) {
      Rectangle tempArea = new Rectangle(
          playerController.getSolidArea().getX(),
          playerController.getSolidArea().getY(),
          playerController.getSolidArea().getWidth(),
          playerController.getSolidArea().getHeight());

      int speed = playerController.getPlayer().getSpeed();

      // Simulate movement
      if (playerController.isKeyUpPressed()) {
        tempArea.setY(tempArea.getY() - speed);
      }
      if (playerController.isKeyDownPressed()) {
        tempArea.setY(tempArea.getY() + speed);
      }
      if (playerController.isKeyLeftPressed()) {
        tempArea.setX(tempArea.getX() - speed);
      }
      if (playerController.isKeyRightPressed()) {
        tempArea.setX(tempArea.getX() + speed);
      }

      // Check collision
      if (tempArea.getBoundsInParent().intersects(asset.getSolidArea().getBoundsInParent())) {
        if (asset.isCollisionOn()) {
          playerController.setnoCollidingAsset(false);
        } else {
          playerController.inInteractiveArea(true);
          if (playerController.isKeyEPressed() && playerController.isCanInteract()) {
            ((Teleporter)asset).accept(playerController.getAction());
          }
        }
        break; // No need to check more if collision is found
      }

    }
  }

  public boolean isFullyContained(Rectangle outer, Rectangle inner) {
    return inner.getX() >= outer.getX()
        && inner.getY() >= outer.getY()
        && inner.getX() + inner.getWidth() <= outer.getX() + outer.getWidth()
        && inner.getY() + inner.getHeight() <= outer.getY() + outer.getHeight();
  }

  // public boolean isCollideWithAsset(Player player, Asset asset) {

  // }
}