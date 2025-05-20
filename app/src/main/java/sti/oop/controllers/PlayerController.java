package sti.oop.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sti.oop.models.Player;
import sti.oop.controllers.FarmController;

public class PlayerController {
  @FXML
  private BorderPane hud;

  @FXML 
  private Canvas canvas;

  private boolean keyLeftPressed = false;
  private boolean keyRightPressed = false;
  private boolean keyDownPressed = false;
  private boolean keyUpPressed = false;
  private boolean keyUporDownJustPressed = false;

  private boolean inventoryOpened = false;
  private StackPane inventoryPane;


  private Scene scene;
  private Player player;
  private PlayerRenderer playerRenderer;

  private FarmController farmController;


  public PlayerController(Scene scene, Player player, PlayerRenderer playerRenderer, FarmController farmController) {
    this.scene = scene;
    this.player = player;
    this.playerRenderer = playerRenderer;
    this.farmController = farmController;
  }

  public void setFarmController(FarmController farmController) {
    this.farmController = farmController;
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
        case KeyCode.SHIFT -> {
            player.setRun(true);
        }
        case KeyCode.E -> {
            toggleInventory();
        }
        default -> {}
      }
    });     

    scene.setOnKeyReleased(e -> {
      System.out.println("Key pressed: " + e.getCode());
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
        case KeyCode.SHIFT -> {
            player.setRun(false);
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
  /* Inventory Logics */
  public void toggleInventory() {
      System.out.println("Toggling Inventory... Current state: " + (inventoryOpened ? "Open" : "Closed"));

      if (inventoryOpened) {
          System.out.println("Closing inventory...");
          hud.getChildren().remove(inventoryPane);
          inventoryOpened = false;
          farmController.unfreezeTime();
          System.out.println("Inventory closed");
      }
      
      else {
          System.out.println("Opening inventory...");
          farmController.freezeTime(); // freeze time

          /* Player can't move whilst inventory is opened */
          keyLeftPressed = false;
          keyRightPressed = false;
          keyUpPressed = false;
          keyDownPressed = false;
          
          /* Inventory GUI render */
          try {
              FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("/views/Inventory.fxml"));
              Parent inventoryParent = inventoryLoader.load();
              inventoryPane = (StackPane) inventoryParent;
              
              InventoryController inventoryController = inventoryLoader.getController();
              // Ubah dari setFarmController ke setPlayerController
              inventoryController.setPlayerController(this);
              // Gunakan player langsung, bukan farm.getPlayer()
              inventoryController.setPlayer(player);
              
              inventoryPane.setMaxSize(800, 400);
              inventoryPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-border-color: white; -fx-border-width: 2px;");
              
              BorderPane.setAlignment(inventoryPane, javafx.geometry.Pos.CENTER);
              hud.setCenter(inventoryPane);

              inventoryPane.requestFocus();
              
              inventoryOpened = true;
              System.out.println("Inventory opened");
          } catch (IOException e) {
              e.printStackTrace();
              System.out.println("Failed to load Inventory.fxml: " + e.getMessage());
          } catch (Exception e) {
              e.printStackTrace();
              System.out.println("Unexpected error: " + e.getMessage());
          }
      }
  }
}
