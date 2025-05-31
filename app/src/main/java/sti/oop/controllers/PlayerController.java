package sti.oop.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import sti.oop.action.Action;
import sti.oop.interfaces.Renderable;
import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.utils.Constants;

public class PlayerController implements Renderable {
  private Player player;
  private CollisionController collisionController;
  private FarmController farmController;
  private Action action;

  /* Key Attributes */
  private boolean keyLeftPressed = false;
  private boolean keyRightPressed = false;
  private boolean keyDownPressed = false;
  private boolean keyUpPressed = false;
  private boolean keyEPressed = false;
  private boolean keyTPressed = false;
  private boolean justInteracted = false;
  private boolean justEaten = false;
  private boolean canMoveLeft = true;
  private boolean canMoveRight = true;
  private boolean canMoveUp = true;
  private boolean canMoveDown = true;

  public void setJustEaten(boolean justEaten) {
    this.justEaten = justEaten;
  }

  public boolean isJustEaten() {
    return justEaten;
  }

  public void setInteractionGuide(boolean visible) {
    farmController.setInteractNotification(visible);
  }

  /* Render Attributes */
  private int frameX = 0;
  private int frameY = 0;
  private int spriteCounter = 0;
  private int idleCounter = 0;
  private boolean isIdle = true;
  private final int playerFrameWidth = 32;
  private final int playerFrameHeight = 32;
  private Image playerSpriteSheet = new Image(getClass().getResourceAsStream("/sprites/spritePlayer.png"));
  private boolean noCollidingAssetUp = true;
  private boolean noCollidingAssetLeft = true;
  private boolean noCollidingAssetRight = true;
  private boolean noCollidingAssetDown = true;

  public boolean isJustInteracted() {
    return justInteracted;
  }

  public void clearJustInteracted() {
    justInteracted = false;
  }

  /* Collision Attributes */
  private final int hitboxOffsetX = (int) (11 * Constants.TILE_SIZE / playerFrameHeight);
  private final int hitboxOffsetY = (int) (23 * Constants.TILE_SIZE / playerFrameHeight);
  private final int hitboxWidth = (int) (10 * Constants.TILE_SIZE / playerFrameHeight);
  private final int hitboxHeight = (int) (9 * Constants.TILE_SIZE / playerFrameHeight);
  private Rectangle solidArea;

  public PlayerController(Player player, CollisionController collisionController, FarmController farmController) {
    this.player = player;
    this.collisionController = collisionController;
    this.farmController = farmController;
    this.solidArea = new Rectangle(player.getX() + hitboxOffsetX, player.getY() + hitboxOffsetY, hitboxWidth,
        hitboxHeight);
    action = new Action(farmController);
  }

  public void updateSolidArea() {
    solidArea.setX(player.getX() + hitboxOffsetX);
    solidArea.setY(player.getY() + hitboxOffsetY);
  }

  public Rectangle getSolidArea() {
    return solidArea;
  }

  public int sourceX() {
    return frameX * playerFrameWidth;
  }

  public int sourceY() {
    return frameY * playerFrameHeight;
  }

  /* -------------------------------------------------------------------------- */
  /* Input Keyboard Logics */
  /* -------------------------------------------------------------------------- */

  public void keyHandler() {
    if (farmController.getStatusInventory()) {
      return;
    }
    boolean isMoving = keyLeftPressed || keyRightPressed || keyUpPressed || keyDownPressed;
    if (isMoving) {
      spriteCounter = (spriteCounter + 1) % 10;
      isIdle = false;
    } else {
      isIdle = true;
    }

    boolean directionChanged = false;

    int intersectPoint1X = 0;
    int intersectPoint1Y = 0;
    int intersectPoint2X = 0;
    int intersectPoint2Y = 0;

    if (keyLeftPressed) {
      intersectPoint1X = player.getX() + hitboxOffsetX - player.getSpeed();
      intersectPoint2X = intersectPoint1X;
      intersectPoint1Y = player.getY() + hitboxOffsetY;
      intersectPoint2Y = intersectPoint1Y + hitboxHeight;

      canMoveLeft = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
          &&
          !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    }

    if (keyRightPressed) {
      intersectPoint1X = player.getX() + hitboxOffsetX + hitboxWidth + player.getSpeed();
      intersectPoint2X = intersectPoint1X;
      intersectPoint1Y = player.getY() + hitboxOffsetY;
      intersectPoint2Y = intersectPoint1Y + hitboxHeight;

      canMoveRight = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
          &&
          !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    }

    if (keyUpPressed) {
      intersectPoint1X = player.getX() + hitboxOffsetX;
      intersectPoint2X = intersectPoint1X + hitboxWidth;
      intersectPoint1Y = player.getY() + hitboxOffsetY - player.getSpeed();
      intersectPoint2Y = intersectPoint1Y;

      canMoveUp = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
          &&
          !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    }

    if (keyDownPressed) {
      intersectPoint1X = player.getX() + hitboxOffsetX;
      intersectPoint2X = intersectPoint1X + hitboxWidth;
      intersectPoint1Y = player.getY() + hitboxOffsetY + hitboxHeight + player.getSpeed();
      intersectPoint2Y = intersectPoint1Y;

      canMoveDown = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
          &&
          !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    }

    boolean leftMovement = keyLeftPressed && canMoveLeft && noCollidingAssetLeft;
    boolean rightMovement = keyRightPressed && canMoveRight && noCollidingAssetRight;
    boolean upMovement = keyUpPressed && canMoveUp && noCollidingAssetUp;
    boolean downMovement = keyDownPressed && canMoveDown && noCollidingAssetDown;

    if (upMovement) {
      player.moveUp();
      if (frameY != 2) {
        frameY = 2;
        frameX = 2;
        directionChanged = true;
      }
      if (spriteCounter == 9 && !directionChanged) {
        frameX = 2 + (frameX + 1) % 2;
      }
      updateSolidArea();
    }

    if (downMovement) {
      player.moveDown();
      if (frameY != 0) {
        frameY = 0;
        frameX = 2;
        directionChanged = true;
      }
      if (spriteCounter == 9 && !directionChanged) {
        frameX = 2 + (frameX + 1) % 2;
      }
      updateSolidArea();
    }

    if (leftMovement) {
      player.moveLeft();
      if (!keyUpPressed && !keyDownPressed) {
        if (frameY != 1 || frameX >= 2) {
          frameY = 1;
          frameX = 0;
          directionChanged = true;
        }
        if (spriteCounter == 9 && !directionChanged) {
          frameX = (frameX + 1) % 2;
        }
      }
      updateSolidArea();
    }
    if (rightMovement) {
      player.moveRight();
      if (!keyUpPressed && !keyDownPressed) {
        if (frameY != 1 || frameX < 2) {
          frameY = 1;
          frameX = 2;
          directionChanged = true;
        }
        if (spriteCounter == 9 && !directionChanged) {
          frameX = 2 + (frameX + 1) % 2;
        }
      }
      updateSolidArea();
    }

    if (isIdle) {
      idleCounter = (idleCounter + 1) % 50;
      if (idleCounter == 49) {
        if (frameY == 0 || frameY == 2) {
          frameX = (frameX + 1) % 2;
        }
      }
    } else {
      idleCounter = 0;
    }
  }

  public void keyMapper(Scene scene) {
    scene.setOnKeyPressed(e -> {
      System.out.println("Key pressed: " + e.getCode());
      switch (e.getCode()) {
        case KeyCode.A -> keyLeftPressed = true;
        case KeyCode.W -> keyUpPressed = true;
        case KeyCode.S -> keyDownPressed = true;
        case KeyCode.D -> keyRightPressed = true;
        case KeyCode.F -> farmController.toggleInventory();
        case KeyCode.SHIFT -> player.setRun(true);
        case KeyCode.E -> {
          if (!keyEPressed) {
            justInteracted = true;
          }
          keyEPressed = true;
        }
        case KeyCode.T -> {
          if (!keyTPressed) {
            justEaten = true;
          }
          keyTPressed = true;
          justEaten = false;
        }
        default -> {
        }
      }
    });

    /* Key Release Toggle Controls */
    scene.setOnKeyReleased(e -> {
      switch (e.getCode()) {
        case KeyCode.A -> keyLeftPressed = false;
        case KeyCode.W -> {
          keyUpPressed = false;
          frameX = 0;
        }
        case KeyCode.S -> {
          keyDownPressed = false;
          frameX = 0;
        }
        case KeyCode.D -> keyRightPressed = false;
        case KeyCode.SHIFT -> player.setRun(false);
        case KeyCode.T -> keyTPressed = false;
        case KeyCode.E -> {
          keyEPressed = false;
          justInteracted = false;
        }
        default -> {
        }
      }
    });
  }

  @Override
  // Contoh di PlayerController.java
  public void render(GraphicsContext gc) {
    keyHandler();

    Canvas canvas = gc.getCanvas();
    double canvasWidth = canvas.getWidth();
    double canvasHeight = canvas.getHeight();

    double playerScreenX = (canvasWidth / 2) - (Constants.TILE_SIZE / 2);
    double playerScreenY = (canvasHeight / 2) - (Constants.TILE_SIZE / 2);

    gc.drawImage(playerSpriteSheet, sourceX(), sourceY(), playerFrameWidth, playerFrameHeight,
        playerScreenX, playerScreenY, // Gunakan posisi dinamis ini
        Constants.TILE_SIZE, Constants.TILE_SIZE);
    double screenHitboxX = playerScreenX + hitboxOffsetX;
    double screenHitboxY = playerScreenY + hitboxOffsetY;

    gc.setStroke(javafx.scene.paint.Color.RED);
    gc.strokeRect(screenHitboxX, screenHitboxY, solidArea.getWidth(), solidArea.getHeight());
  }

  /* -------------------------------------------------------------------------- */
  /* Attribute Getter Setter */
  /* -------------------------------------------------------------------------- */

  public Player getPlayer() {
    return player;
  }

  public int getPlayerFrameWidth() {
    return playerFrameWidth;
  }

  public int getPlayerFrameHeight() {
    return playerFrameHeight;
  }

  public Image getPlayerSpriteSheet() {
    return playerSpriteSheet;
  }

  

  /* -------------------------------------------------------------------------- */
  /* Key Pressed Logics */
  /* -------------------------------------------------------------------------- */

  public void setNoCollidingAssetUp(boolean noCollidingAssetUp) {
    this.noCollidingAssetUp = noCollidingAssetUp;
  }

  public void setNoCollidingAssetLeft(boolean noCollidingAssetLeft) {
    this.noCollidingAssetLeft = noCollidingAssetLeft;
  }

  public void setNoCollidingAssetRight(boolean noCollidingAssetRight) {
    this.noCollidingAssetRight = noCollidingAssetRight;
  }

  public void setNoCollidingAssetDown(boolean noCollidingAssetDown) {
    this.noCollidingAssetDown = noCollidingAssetDown;
  }

  public boolean isKeyLeftPressed() {
    return keyLeftPressed;
  }

  public boolean isKeyRightPressed() {
    return keyRightPressed;
  }

  public boolean isKeyDownPressed() {
    return keyDownPressed;
  }

  public boolean isKeyUpPressed() {
    return keyUpPressed;
  }

  public Action getAction() {
    return action;
  }

  /* -------------------------------------------------------------------------- */
  /* Hotbar Logics */
  /* -------------------------------------------------------------------------- */

  @FXML
  public void handleKeyPress(KeyEvent event) {
    if (event.getCode() == KeyCode.DIGIT1) {
      cycleToNextItem();
      event.consume();
    }
  }

  public void setCanMoveLeft(boolean canMoveLeft) {
    this.canMoveLeft = canMoveLeft;
  }

  public void setCanMoveRight(boolean canMoveRight) {
    this.canMoveRight = canMoveRight;
  }

  public void setCanMoveUp(boolean canMoveUp) {
    this.canMoveUp = canMoveUp;
  }

  public void setCanMoveDown(boolean canMoveDown) {
    this.canMoveDown = canMoveDown;
  }

  private void cycleToNextItem() {
    Inventory inventory = player.getInventory();
    int totalItems = inventory.getAllItem().size();

    if (totalItems > 0) {
      int nextIndex = (player.getOnHandInventoryIndex() + 1) % totalItems;
      player.setOnHandInventoryIndex(nextIndex);

      Item currentItem = player.getOnHandItem();
      if (currentItem != null) {
        System.out.println("Switched to: " + currentItem.getItemName());
      }

      else {
        System.out.println("No item selected");
      }
    }
  }

  public CollisionController getCollisionController() {
    return collisionController;
  }

  public FarmController getFarmController() {
    return farmController;
  }

  public boolean isKeyEPressed() {
    return keyEPressed;
  }

  public boolean isKeyTPressed() {
    return keyTPressed;
  }

  public boolean isCanMoveLeft() {
    return canMoveLeft;
  }

  public boolean isCanMoveRight() {
    return canMoveRight;
  }

  public boolean isCanMoveUp() {
    return canMoveUp;
  }

  public boolean isCanMoveDown() {
    return canMoveDown;
  }



  public boolean isNoCollidingAssetUp() {
    return noCollidingAssetUp;
  }

  public boolean isNoCollidingAssetLeft() {
    return noCollidingAssetLeft;
  }

  public boolean isNoCollidingAssetRight() {
    return noCollidingAssetRight;
  }

  public boolean isNoCollidingAssetDown() {
    return noCollidingAssetDown;
  }
}