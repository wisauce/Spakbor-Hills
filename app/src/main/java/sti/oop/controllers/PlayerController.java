package sti.oop.controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
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

  public void playerUpdate() {
    playerRenderer.playerMovementHandler(player, keyLeftPressed, keyRightPressed, keyUpPressed, keyDownPressed, keyUporDownJustPressed);
    player.move(keyLeftPressed, keyUpPressed, keyDownPressed, keyRightPressed);
    playerRenderer.renderPlayer();
    if (keyUporDownJustPressed) {
      keyUporDownJustPressed = false;
    }
  }
}
