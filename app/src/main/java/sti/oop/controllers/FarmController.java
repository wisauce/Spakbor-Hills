package sti.oop.controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Player.Gender;
import javafx.scene.input.KeyCode;

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
  private Player player;
  private MapController mapController;

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
      player.moveLeft();
      player.setFrameY(1);
      if (spriteCounter == 9) player.setFrameX((player.getFrameX()+1)%2);
    } 
    if (keyRightPressed) {
      player.moveRight();
      player.setFrameY(1);
      if (spriteCounter == 9) player.setFrameX(2 + (player.getFrameX() + 1) %2);
    }
    if (keyUpPressed) {
      player.moveUp();
      player.setFrameY(2);
      if (spriteCounter == 9) player.setFrameX(2 + (player.getFrameX()+1) %2);
    }
    if (keyDownPressed) {
      player.moveDown();
      player.setFrameY(0);
      if (spriteCounter == 9) player.setFrameX(2 + (player.getFrameX() + 1) % 2); 
    }
    if (isIdle) {
      if (player.getFrameY() == 0 || player.getFrameY() == 2) {
        idleCounter = (idleCounter + 1) % 50;
        if (idleCounter == 49) player.setFrameX((player.getFrameX() + 1) % 2);
      } else {
        if (player.getFrameX() < 2) {
          player.setFrameX(1);
        } else {
          player.setFrameX(3);
        }
      }
    } else {
      idleCounter = 0;
    }
  }

  public void render() {
    // gc.setFill(Color.GREEN);
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    mapController.render(player.getX(), player.getY(), player.getScreenX(), player.getScreenY());
    gc.drawImage(player.getPlayerSpriteSheet(), player.sourceX(), player.sourceY(), player.getPlayerFrameWidth(), player.getPlayerFrameHeight(), player.getScreenX(), player.getScreenY(), 128, 128);
    // gc.fillRect(farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, tileSize, tileSize);
  }
  
  @FXML
  public void initialize() {
    Player player = new Player("Asep", Gender.MALE, "Asep's diary");
    farm = new Farm(player);
    gc = canvas.getGraphicsContext2D();
    mapController = new MapController(gc,"/tileSheet/farm/cek2.png","/tileSheet/farm/pond.txt", 128 );
    this.player = player;
    // Delay until the scene is ready
    Platform.runLater(() -> {
      scene = hud.getParent().getScene();
      // canvas.setHeight(scene.getHeight());
      // canvas.setWidth(scene.getWidth());
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
          case KeyCode.W -> {keyUpPressed = false; player.setFrameX(0);}
          case KeyCode.S -> {keyDownPressed = false; player.setFrameX(0);}
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