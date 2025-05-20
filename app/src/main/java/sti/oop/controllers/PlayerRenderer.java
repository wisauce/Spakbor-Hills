package sti.oop.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sti.oop.models.Player;

public class PlayerController {
  private boolean keyLeftPressed = false;
  private boolean keyRightPressed = false;
  private boolean keyDownPressed = false;
  private boolean keyUpPressed = false;
  private boolean keyUporDownJustPressed = false;

  private Scene scene;
  private Player player;
  private PlayerRenderer playerRenderer;


  public PlayerController(Scene scene, Player player, PlayerRenderer playerRenderer) {
    this.scene = scene;
    this.player = player;
    this.playerRenderer = playerRenderer;
  }

  public void inputMovementHandler() {
    scene.setOnKeyPressed(e -> {
      switch (e.getCode()) {
        case KeyCode.A -> {
          keyLeftPressed = true;
        }
        case KeyCode.W -> {
          keyUpPressed = true;
        }
        case KeyCode.S -> {
          keyDownPressed = true;
        }
        case KeyCode.D -> {
          keyRightPressed = true;
        }
        default -> {}
      }
    });     

    scene.setOnKeyReleased(e -> {
      switch (e.getCode()) {
        case KeyCode.A -> {
          keyLeftPressed = false;
        }
        case KeyCode.W -> {
          keyUpPressed = false;
          keyUporDownJustPressed = true;
        }
        case KeyCode.S -> {
          keyDownPressed = false;
          keyUporDownJustPressed = true;
        }
        case KeyCode.D -> {
          keyRightPressed = false;
        }
        default -> {}
      }
    });
  }

  public void playerUpdate(CollisionController collisionController, GraphicsContext gc) {
    playerRenderer.renderPlayer();

    int intersectPoint1X = 0;
    int intersectPoint1Y = 0;
    int intersectPoint2X = 0;
    int intersectPoint2Y = 0;


    if (keyLeftPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX() - player.getSpeed();
      intersectPoint2X = intersectPoint1X;
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY();
      intersectPoint2Y = intersectPoint1Y + playerRenderer.getHitboxHeight();
    }
    else if (keyUpPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX();
      intersectPoint2X = intersectPoint1X + playerRenderer.getHitboxWidth();
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY() - player.getSpeed();
      intersectPoint2Y = intersectPoint1Y;
    } 
    else if (keyRightPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX() + playerRenderer.getHitboxWidth() + player.getSpeed();
      intersectPoint2X = intersectPoint1X;
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY();
      intersectPoint2Y = intersectPoint1Y + playerRenderer.getHitboxHeight();
    } 
    else if (keyDownPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX();
      intersectPoint2X = intersectPoint1X + playerRenderer.getHitboxWidth();
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY() + playerRenderer.getHitboxHeight() + player.getSpeed();
      intersectPoint2Y = intersectPoint1Y;
    } 

    if (!collisionController.isCollision(intersectPoint1X, intersectPoint1Y) && !collisionController.isCollision(intersectPoint2X, intersectPoint2Y)) {
      playerRenderer.playerMovementHandler(player, keyLeftPressed, keyRightPressed, keyUpPressed, keyDownPressed, keyUporDownJustPressed);
      player.move(keyLeftPressed, keyUpPressed, keyDownPressed, keyRightPressed);
    }
    if (keyUporDownJustPressed) {
      keyUporDownJustPressed = false;
    }

    gc.setFill(Color.RED);
    gc.rect(intersectPoint1X, intersectPoint1Y, 2, 2);
    gc.rect(intersectPoint2X, intersectPoint2Y, 2, 2);
  }
}