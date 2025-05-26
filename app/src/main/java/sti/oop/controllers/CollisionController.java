package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Asset;
import sti.oop.models.CollisionMap;
import sti.oop.models.InteractableAssset;
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

public void checkAssetCollision(PlayerController playerController) {
  // Reset collision flag before checking
  playerController.setnoCollidingAsset(true);
  for (Asset asset : assets) {
    Rectangle tempArea = new Rectangle(
      playerController.getSolidArea().getX(),
      playerController.getSolidArea().getY(),
      playerController.getSolidArea().getWidth(),
      playerController.getSolidArea().getHeight()
    );

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
      if (asset.isCollisonOn()) {
        playerController.setnoCollidingAsset(false);
      }
      break; // No need to check more if collision is found
    }
    if (asset instanceof InteractableAssset) {
      // InteractableAssset interactableAssset = (InteractableAssset) asset;

      // Rectangle playerArea = playerController.getSolidArea(); // ⬅️ Pakai yang ini
      // Rectangle interactionArea = interactableAssset.getInteractionArea();

      // if (isRectFullyContained(playerArea, interactionArea)) {
      //   System.out.println("hello");
      //   playerController.inInteractiveArea(true);
      //   if (playerController.isKeyEPressed() && playerController.isCanInteract()) {
      //     interactableAssset.accept(playerController.getAction());
      //   }
      // } else {
      //   playerController.inInteractiveArea(false);
      // }
    }

  }
}
    private boolean isRectFullyContained(Rectangle rect1, Rectangle rect2) {
        if (rect1 == null || rect2 == null) {
            return false;
        }
        // Koordinat rect1
        double r1x = rect1.getX();
        double r1y = rect1.getY();
        double r1xMax = r1x + rect1.getWidth();
        double r1yMax = r1y + rect1.getHeight();

        // Koordinat rect2
        double r2x = rect2.getX();
        double r2y = rect2.getY();
        double r2xMax = r2x + rect2.getWidth();
        double r2yMax = r2y + rect2.getHeight();

        return r1x >= r2x &&       // Sisi kiri rect1 >= sisi kiri rect2
               r1y >= r2y &&       // Sisi atas rect1 >= sisi atas rect2
               r1xMax <= r2xMax && // Sisi kanan rect1 <= sisi kanan rect2
               r1yMax <= r2yMax;   // Sisi bawah rect1 <= sisi bawah rect2
    }

  // public boolean isCollideWithAsset(Player player, Asset asset) {
    
  // }
}