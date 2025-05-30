package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.CollisionMap;
import sti.oop.models.Interactable;
import sti.oop.models.assets.Asset;
import sti.oop.utils.Constants;

// import sti.oop.models.Constants;

public class CollisionController {
  private Map<MapName, CollisionMap> mapOfCollisionMaps;
  private CollisionMap currentCollisionMap;

  public CollisionController() {
    mapOfCollisionMaps = Map.ofEntries(
        Map.entry(MapName.FARM, new CollisionMap("/maps/farmCollision.txt")),
        Map.entry(MapName.HOUSE, new CollisionMap("/maps/housecollisionnew.txt")),
        Map.entry(MapName.WORLD,new CollisionMap("/maps/worldCollision.txt")),
        Map.entry(MapName.NPC1_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC2_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC3_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC4_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC5_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC6_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt"))
        );
    currentCollisionMap = mapOfCollisionMaps.get(MapName.NPC1_HOUSE);
  }

  public boolean isCollision(int x, int y) {
    int tileX = x / Constants.TILE_SIZE;
    int tileY = y / Constants.TILE_SIZE;
    return currentCollisionMap.getCollisionMatrix().get(tileY).get(tileX);
  }

  public void setCurrentCollisionMap(MapName mapName) {
    currentCollisionMap = mapOfCollisionMaps.get(mapName);
  }

  public void checkAssetCollision(List<Asset> assets, PlayerController playerController,
      PanelController panelController) {
    boolean collidedWithInteractable = false;
    // Reset collision flag before checking
    playerController.setnoCollidingAsset(true);
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
          collidedWithInteractable = true; // <-- tandai kalau sedang nabrak asset

          if (playerController.isToggled() && playerController.isJustInteracted()) {
            panelController.showDialog(((Interactable) asset).accept(playerController.getAction()));
            playerController.clearJustInteracted();
          }
          if (playerController.isJustInteracted() && !playerController.isToggled()) {
            Interactable interactable = (Interactable) asset;
            if (interactable.multipleInput()) {
              playerController.toggle(); // masuk ke interaction mode
            } 
            panelController.showDialog(interactable.accept(playerController.getAction()));
            playerController.clearJustInteracted();
          }
        }
        break; // keluar dari loop kalau sudah collision
      }
    }

    // Setelah loop selesai
    boolean isMoving = (playerController.isKeyDownPressed() || playerController.isKeyLeftPressed()
        || playerController.isKeyRightPressed() || playerController.isKeyUpPressed());
    if (isMoving && playerController.isToggled()) {
      playerController.toggle(); // matikan toggle kalau player sudah keluar dari area
    }
    if (isMoving) {
      panelController.hideAllBottom();
    }
  }
}