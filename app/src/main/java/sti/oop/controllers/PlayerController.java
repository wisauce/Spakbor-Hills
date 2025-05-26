package sti.oop.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import sti.oop.interfaces.Renderable;
import sti.oop.models.Player;
import sti.oop.utils.Constants;

public class PlayerController implements Renderable {
  private Player player;
  private CollisionController collisionController;
  private FarmController farmController;
  // key
  private boolean keyLeftPressed = false;
  private boolean keyRightPressed = false;
  private boolean keyDownPressed = false;
  private boolean keyUpPressed = false;

  // render
  private int frameX = 0;
  private int frameY = 0;
  private int spriteCounter = 0;
  private int idleCounter = 0;
  private boolean isIdle = true;
  private final int playerFrameWidth = 32;
  private final int playerFrameHeight = 32;
  private Image playerSpriteSheet = new Image(getClass().getResourceAsStream("/sprites/spritePlayer.png"));

  // collision
  private final int hitboxOffsetX = (int) (11 * Constants.TILE_SIZE / playerFrameHeight);
  private final int hitboxOffsetY = (int) (23 * Constants.TILE_SIZE / playerFrameHeight);
  private final int hitboxWidth = (int) (10 * Constants.TILE_SIZE / playerFrameHeight);
  private final int hitboxHeight = (int) (9 * Constants.TILE_SIZE / playerFrameHeight);

  public PlayerController(Player player, CollisionController collisionController, FarmController farmController) {
    this.player = player;
    this.collisionController = collisionController;
    this.farmController = farmController;
  }

  public int sourceX() {
    return frameX * playerFrameWidth;
  }

  public int sourceY() {
    return frameY * playerFrameHeight;
  }

  /* -------------------------------------------------------------------------- */
  /* INPUT KEYBOARD LOGICS */
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

    boolean canMoveLeft = true;
    boolean canMoveRight = true;
    boolean canMoveUp = true;
    boolean canMoveDown = true;

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

    boolean leftMovement = keyLeftPressed && canMoveLeft;
    boolean rightMovement = keyRightPressed && canMoveRight;
    boolean upMovement = keyUpPressed && canMoveUp;
    boolean downMovement = keyDownPressed && canMoveDown;

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
        case KeyCode.E -> farmController.toggleInventory();
        case KeyCode.SHIFT -> player.setRun(true);
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
        default -> {
        }
      }
    });
  }

  /* -------------------------------------------------------------------------- */
  /* INPUT KEYBOARD LOGICS */
  /* -------------------------------------------------------------------------- */

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
  }

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

}
