package sti.oop.controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Player.Gender;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class FarmController {
  @FXML
  private BorderPane hud;

  @FXML
  private Canvas canvas;

  private final int originalTileSize = 16;
  private final int scale = 4;
  private final int tileSize = originalTileSize * scale;

  private Scene scene;
  private GraphicsContext gc;

  private boolean keyLeftPressed;
  private boolean keyUpPressed;
  private boolean keyDownPressed;
  private boolean keyRightPressed;

  private Farm farm;

  int spriteCounter = 0;
  int idleCounter = 0;
  boolean isIdle = true;
  public void KeyHandler() {
    if (keyLeftPressed || keyRightPressed || keyUpPressed || keyDownPressed) {
      spriteCounter = (spriteCounter + 1) % 10;
      isIdle = false;
    } else {
      isIdle = true;
    }
    if (keyLeftPressed) {
      farm.getPlayer().moveLeft();
      Player.frameY = 1;
      if (spriteCounter == 9) Player.frameX = (Player.frameX + 1) % 2;
    } 
    if (keyRightPressed) {
      farm.getPlayer().moveRight();
      Player.frameY = 1;
      if (spriteCounter == 9) Player.frameX = 2 + (Player.frameX + 1) % 2;
    }
    if (keyUpPressed) {
      farm.getPlayer().moveUp();
      Player.frameY = 2;
      if (spriteCounter == 9) Player.frameX = 2 + (Player.frameX + 1) % 2;
    }
    if (keyDownPressed) {
      farm.getPlayer().moveDown();
      Player.frameY = 0;
      if (spriteCounter == 9) Player.frameX = 2 + (Player.frameX + 1) % 2;
    }
    if (isIdle) {
      if (Player.frameY == 0 || Player.frameY == 2) {
        idleCounter = (idleCounter + 1) % 50;
        if (idleCounter == 49) Player.frameX = (Player.frameX + 1) % 2;
      } else {
        if (Player.frameX < 2) {
          Player.frameX = 1;
        } else {
          Player.frameX = 3;
        }
      }
    } else {
      idleCounter = 0;
    }
  }

  public void render() {
    // gc.setFill(Color.GREEN);
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    gc.drawImage(Player.playerSpriteSheet, Player.sourceX(), Player.sourceY(), Player.playerFrameWidth, Player.playerFrameHeight, farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, 128, 128);
    // gc.fillRect(farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, tileSize, tileSize);
  }

  @FXML
  public void initialize() {
    Player player = new Player("Asep", Gender.MALE, "Asep's diary");
    farm = new Farm(player);
    // Delay until the scene is ready
    Platform.runLater(() -> {
      scene = hud.getParent().getScene();
      gc = canvas.getGraphicsContext2D();
      scene.setOnKeyPressed(e -> {
        switch (e.getCode()) {
          case KeyCode.A -> keyLeftPressed = true;
          case KeyCode.W -> keyUpPressed = true;
          case KeyCode.S -> keyDownPressed = true;
          case KeyCode.D -> keyRightPressed = true;
          default -> {}
        }
      });

      scene.setOnKeyReleased(e -> {
        switch (e.getCode()) {
          case KeyCode.A -> keyLeftPressed = false;
          case KeyCode.W -> {keyUpPressed = false; Player.frameX = 0;}
          case KeyCode.S -> {keyDownPressed = false; Player.frameX = 0;}
          case KeyCode.D -> keyRightPressed = false;
          default -> {}
        }
      });
    });

    AnimationTimer gameTime = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // System.out.println(now);
        KeyHandler();
        render();
      }
    };

    gameTime.start();
  }
}