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

  public void KeyHandler() {
    if (keyLeftPressed) farm.getPlayer().moveLeft();
    if (keyRightPressed) farm.getPlayer().moveRight();
    if (keyUpPressed) farm.getPlayer().moveUp();
    if (keyDownPressed) farm.getPlayer().moveDown();
  }

  public void render() {
    gc.setFill(Color.GREEN);
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    gc.fillRect(farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, tileSize, tileSize);
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
          case KeyCode.W -> keyUpPressed = false;
          case KeyCode.S -> keyDownPressed = false;
          case KeyCode.D -> keyRightPressed = false;
          default -> {}
        }
      });
    });

    AnimationTimer gameTime = new AnimationTimer() {
      @Override
      public void handle(long now) {
        KeyHandler();
        render();
      }
    };

    gameTime.start();
  }
}
