package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.CollisionMap;
import sti.oop.models.Interactable;
import sti.oop.models.assets.Asset;
import sti.oop.utils.Constants;

public class CollisionController {
  private Map<MapName, CollisionMap> mapOfCollisionMaps;
  private CollisionMap currentCollisionMap;

  public CollisionController() {
    mapOfCollisionMaps = Map.ofEntries(
        Map.entry(MapName.FARM, new CollisionMap("/maps/farmCollision.txt")),
        Map.entry(MapName.HOUSE, new CollisionMap("/maps/housecollisionnew.txt")),
        Map.entry(MapName.WORLD,new CollisionMap("/maps/worldCollision.txt")),
        Map.entry(MapName.STORE,new CollisionMap("/maps/storeCollision.txt")),
        Map.entry(MapName.NPC1_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC2_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC3_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC4_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt")),
        Map.entry(MapName.NPC5_HOUSE,new CollisionMap("/maps/npcHouseCollision.txt"))
        );
    currentCollisionMap = mapOfCollisionMaps.get(MapName.FARM);
  }

  public boolean isCollision(int x, int y) {
    int tileX = x / Constants.TILE_SIZE;
    int tileY = y / Constants.TILE_SIZE;
    // Ensure tileX and tileY are within bounds
    if (tileY < 0 || tileY >= currentCollisionMap.getCollisionMatrix().size() ||
        tileX < 0 || tileX >= currentCollisionMap.getCollisionMatrix().get(0).size()) {
        return true; // Treat out-of-bounds as collision
    }
    return currentCollisionMap.getCollisionMatrix().get(tileY).get(tileX);
  }

  public void setCurrentCollisionMap(MapName mapName) {
    currentCollisionMap = mapOfCollisionMaps.get(mapName);
  }

  public void checkAssetCollision(List<Asset> assets, PlayerController playerController, PanelController panelController) {
    Rectangle playerSolidArea = playerController.getSolidArea();
    int speed = playerController.getPlayer().getSpeed();

    playerController.setNoCollidingAssetUp(true);
    playerController.setNoCollidingAssetDown(true);
    playerController.setNoCollidingAssetLeft(true);
    playerController.setNoCollidingAssetRight(true);

    if (playerController.isKeyUpPressed()) {
        Rectangle tempAreaUp = new Rectangle(
            playerSolidArea.getX(),
            playerSolidArea.getY() - speed,
            playerSolidArea.getWidth(),
            playerSolidArea.getHeight());
        for (Asset asset : assets) {
            if (asset.isCollisionOn() && tempAreaUp.getBoundsInParent().intersects(asset.getSolidArea().getBoundsInParent())) {
                playerController.setNoCollidingAssetUp(false);
                break; 
            }
        }
    }

    if (playerController.isKeyDownPressed()) {
        Rectangle tempAreaDown = new Rectangle(
            playerSolidArea.getX(),
            playerSolidArea.getY() + speed,
            playerSolidArea.getWidth(),
            playerSolidArea.getHeight());
        for (Asset asset : assets) {
            if (asset.isCollisionOn() && tempAreaDown.getBoundsInParent().intersects(asset.getSolidArea().getBoundsInParent())) {
                playerController.setNoCollidingAssetDown(false);
                break; 
            }
        }
    }

    // LEFT
    if (playerController.isKeyLeftPressed()) {
        Rectangle tempAreaLeft = new Rectangle(
            playerSolidArea.getX() - speed,
            playerSolidArea.getY(),
            playerSolidArea.getWidth(),
            playerSolidArea.getHeight());
        for (Asset asset : assets) {
            if (asset.isCollisionOn() && tempAreaLeft.getBoundsInParent().intersects(asset.getSolidArea().getBoundsInParent())) {
                playerController.setNoCollidingAssetLeft(false);
                break; 
            }
        }
    }

    // RIGHT
    if (playerController.isKeyRightPressed()) {
        Rectangle tempAreaRight = new Rectangle(
            playerSolidArea.getX() + speed,
            playerSolidArea.getY(),
            playerSolidArea.getWidth(),
            playerSolidArea.getHeight());
        for (Asset asset : assets) {
            if (asset.isCollisionOn() && tempAreaRight.getBoundsInParent().intersects(asset.getSolidArea().getBoundsInParent())) {
                playerController.setNoCollidingAssetRight(false);
                break; 
            }
        }
    }

    double potentialInteractX = playerSolidArea.getX();
    double potentialInteractY = playerSolidArea.getY();

    if (playerController.isKeyUpPressed() && playerController.isNoCollidingAssetUp() && playerController.isCanMoveUp()) {
        potentialInteractY -= speed;
    } else if (playerController.isKeyDownPressed() && playerController.isNoCollidingAssetDown() && playerController.isCanMoveDown()) {
        potentialInteractY += speed;
    }

    if (playerController.isKeyLeftPressed() && playerController.isNoCollidingAssetLeft() && playerController.isCanMoveLeft()) {
        potentialInteractX -= speed;
    } else if (playerController.isKeyRightPressed() && playerController.isNoCollidingAssetRight() && playerController.isCanMoveRight()) {
        potentialInteractX += speed;
    }
    
    Rectangle potentialInteractionArea = new Rectangle(
        potentialInteractX,
        potentialInteractY,
        playerSolidArea.getWidth(),
        playerSolidArea.getHeight());

    playerController.setInteractionGuide(false); 

    for (Asset asset : assets) {
        if (potentialInteractionArea.getBoundsInParent().intersects(asset.getSolidArea().getBoundsInParent())) {
            if (!asset.isCollisionOn()) {
                playerController.setInteractionGuide(true);
                if (playerController.isJustInteracted()) {
                    Interactable interactable = (Interactable) asset;
                    interactable.accept(playerController.getAction());
                    playerController.clearJustInteracted();
                }
                break;
            } else {
                break;
            }
        }
    }

    boolean isMovingKeyPressed = (playerController.isKeyDownPressed() || playerController.isKeyLeftPressed()
        || playerController.isKeyRightPressed() || playerController.isKeyUpPressed());
    if (isMovingKeyPressed) {
      panelController.hideAllBottomPanels();
    }
  }
}