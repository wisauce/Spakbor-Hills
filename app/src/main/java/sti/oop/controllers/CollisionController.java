package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.CollisionMap;
import sti.oop.models.Interactable;
import sti.oop.models.assets.Asset;
import sti.oop.models.assets.NPCArea;
import sti.oop.utils.Constants;

// import sti.oop.models.Constants;

public class CollisionController {
  private Map<MapName, CollisionMap> mapOfCollisionMaps;
  private CollisionMap currentCollisionMap;

  public CollisionController() {
    mapOfCollisionMaps = Map.ofEntries(
        Map.entry(MapName.FARM, new CollisionMap("/maps/farmCollision.txt")),
        Map.entry(MapName.HOUSE, new CollisionMap("/maps/housecollisionnew.txt")));
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

  public void checkAssetCollision(List<Asset> assets, PlayerController playerController) {
    // Reset collision flag before checking
    playerController.setnoCollidingAsset(true);
    playerController.setCanInteract(false);
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
          playerController.setCanInteract(true);
          if (asset instanceof NPCArea) {
            if (playerController.isKeyEPressed() && playerController.isCanInteract()) {
              if (!playerController.isHasInteracted()) {
                playerController.setMarryAcceptingKey(true);
                playerController.setHasInteracted(true);
              }
            }
            // System.out.println(playerController.isMarryAcceptingKey());
            if (playerController.isMarryAcceptingKey()) {
              if (playerController.isKey2Pressed() && playerController.isCanInteract()) {
                if (!playerController.isHasInteracted()) {
                  ((NPCArea) asset).setChoosen_act("Marry");
                  ((NPCArea) asset).accept(playerController.getAction());
                  playerController.setHasInteracted(true);

                }
              }

              if (playerController.isKey1Pressed() && playerController.isCanInteract()) {
                if (!playerController.isHasInteracted()) {
                  ((NPCArea) asset).setChoosen_act("Propose");
                  ((NPCArea) asset).accept(playerController.getAction());
                  playerController.setHasInteracted(true);

                }
              }

              if (playerController.isKey3Pressed() && playerController.isCanInteract()) {
                if (!playerController.isHasInteracted()) {
                  ((NPCArea) asset).setChoosen_act("Chat");
                  ((NPCArea) asset).accept(playerController.getAction());
                  playerController.setHasInteracted(true);

                }
              }

              if (playerController.isKey4Pressed() && playerController.isCanInteract()) {
                if (!playerController.isHasInteracted()) {
                  ((NPCArea) asset).setChoosen_act("Gift");
                  ((NPCArea) asset).accept(playerController.getAction());
                  playerController.setHasInteracted(true);

                }
              }
            }
          }
          if (playerController.isKeyEPressed() && playerController.isCanInteract()) {
            if (!playerController.isHasInteracted()) {
              ((Interactable) asset).accept(playerController.getAction());
              playerController.setHasInteracted(true);
            }
          }
        }
        break; // No need to check more if collision is found
      }

    }
  }
}