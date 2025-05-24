package sti.oop.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import sti.oop.interfaces.Renderable;
import sti.oop.models.Player;
import sti.oop.utils.Constants;

public class PlayerController implements Renderable {
  private Player player;

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
  private boolean inventoryOpened = false;
  private final int playerFrameWidth = 256;
  private final int playerFrameHeight = 256;
  private Image playerSpriteSheet = new Image(getClass().getResourceAsStream("/sprites/spritePlayer.png"));

  public PlayerController(Player player) {
    this.player = player;
  }

  public int sourceX() {
    return frameX * playerFrameWidth;
  }

  public int sourceY() {
    return frameY * playerFrameHeight;
  }

  public void keyHandler() {
    if (inventoryOpened) {
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

    if (keyUpPressed) {
      player.moveUp();
      if (frameY != 2) {
        frameY = 2;
        frameX = 2;
        directionChanged = true;
      }
      if (spriteCounter == 9 && !directionChanged) {
        frameX = 2 + (frameX + 1) % 2;
      }
    } else if (keyDownPressed) {
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

    if (keyLeftPressed) {
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
    if (keyRightPressed) {
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
        } else {
          if (frameX < 2) {
            frameX = (frameX + 1) % 2;
          } else {
            frameX = 2 + (frameX + 1) % 2;
          }
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
        // case KeyCode.E -> toggleInventory();
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

  public void update() {
    keyHandler();
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.drawImage(playerSpriteSheet, sourceX(), sourceY(), playerFrameWidth, playerFrameHeight, Constants.PLAYER_SCREEN_X,
        Constants.PLAYER_SCREEN_Y, Constants.TILE_SIZE, Constants.TILE_SIZE);
  }
}
