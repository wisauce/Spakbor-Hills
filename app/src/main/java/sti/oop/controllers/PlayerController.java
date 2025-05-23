package sti.oop.controllers;

import java.io.IOException;
import java.net.URL;

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

    // Debug the scene structure
    System.out.println("Scene root: " + scene.getRoot().getClass().getName());
    
    this.hud = new BorderPane();
    
    if (scene.getRoot() instanceof BorderPane) {
        BorderPane root = (BorderPane) scene.getRoot();
        root.setCenter(this.hud);
        System.out.println("HUD successfully added to scene root");
    } else {
        System.out.println("[Error]: Cannot add hud to scene root - not a BorderPane");
        System.out.println("Scene root is: " + scene.getRoot().getClass().getName());
    }
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

  public void playerUpdate(CollisionController collisionController, GraphicsContext gc, double diffTime) {
    playerRenderer.renderPlayer();

    if (inventoryOpened) {
      return;
    }

    int frameAdjustedSpeed = (int)Math.round(player.getSpeed() * diffTime * 60);

    if (frameAdjustedSpeed < 1) {
      frameAdjustedSpeed = 1;
    }

    int intersectPoint1X = 0;
    int intersectPoint1Y = 0;
    int intersectPoint2X = 0;
    int intersectPoint2Y = 0;

    boolean canMoveLeft = true;
    boolean canMoveRight = true;
    boolean canMoveUp = true;
    boolean canMoveDown = true;

    if (keyLeftPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX() - frameAdjustedSpeed;
      intersectPoint2X = intersectPoint1X;
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY();
      intersectPoint2Y = intersectPoint1Y + playerRenderer.getHitboxHeight();

      canMoveLeft = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
                    &&
                    !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    }

    if (keyRightPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX() + playerRenderer.getHitboxWidth() + frameAdjustedSpeed;
      intersectPoint2X = intersectPoint1X;
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY();
      intersectPoint2Y = intersectPoint1Y + playerRenderer.getHitboxHeight();
      
      canMoveRight = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
                    &&
                    !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    } 

    if (keyUpPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX();
      intersectPoint2X = intersectPoint1X + playerRenderer.getHitboxWidth();
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY() - frameAdjustedSpeed;
      intersectPoint2Y = intersectPoint1Y;

      canMoveUp = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
                    &&
                    !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    } 


    if (keyDownPressed) {
      intersectPoint1X = player.getX() + playerRenderer.getHitboxOffsetX();
      intersectPoint2X = intersectPoint1X + playerRenderer.getHitboxWidth();
      intersectPoint1Y = player.getY() + playerRenderer.getHitboxOffsetY() + playerRenderer.getHitboxHeight() + frameAdjustedSpeed;
      intersectPoint2Y = intersectPoint1Y;

      canMoveDown = !collisionController.isCollision(intersectPoint1X, intersectPoint1Y)
                    &&
                    !collisionController.isCollision(intersectPoint2X, intersectPoint2Y);
    } 

    boolean leftMovement = keyLeftPressed && canMoveLeft;
    boolean rightMovement = keyRightPressed && canMoveRight;
    boolean upMovement = keyUpPressed && canMoveUp;
    boolean downMovement = keyDownPressed && canMoveDown;

    playerRenderer.playerMovementHandler(player, leftMovement, rightMovement, upMovement, downMovement, keyUporDownJustPressed, diffTime);
    
    player.move(leftMovement, upMovement, downMovement, rightMovement);
    
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
        // Get the root StackPane and remove the inventory overlay
        if (scene.getRoot() instanceof StackPane) {
            StackPane root = (StackPane) scene.getRoot();
            root.getChildren().remove(inventoryPane);
            System.out.println("Removed inventory from StackPane root");
        }
        
        inventoryOpened = false;
        farmController.unfreezeTime();
        System.out.println("Inventory closed");
    } else {
        System.out.println("Opening inventory...");
        farmController.freezeTime();

        /* Player can't move whilst inventory is opened */
        keyLeftPressed = false;
        keyRightPressed = false;
        keyUpPressed = false;
        keyDownPressed = false;
        
        try {
            // Load the inventory FXML as usual
            URL resourceUrl = getClass().getResource("/views/Inventory.fxml");
            if (resourceUrl == null) {
                System.out.println("ERROR: Inventory.fxml not found");
                return;
            }
            
            FXMLLoader inventoryLoader = new FXMLLoader(resourceUrl);
            Parent inventoryParent = inventoryLoader.load();
            inventoryPane = (StackPane) inventoryParent;
            
            InventoryController inventoryController = inventoryLoader.getController();
            inventoryController.setPlayerController(this);
            inventoryController.setPlayer(player);

            // Style and configure the inventory
            inventoryPane.setMaxSize(800, 400);
            inventoryPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-border-color: yellow; -fx-border-width: 5px;");
            inventoryPane.setVisible(true);
            
            // Add directly to the scene's root StackPane
            if (scene.getRoot() instanceof StackPane) {
                StackPane root = (StackPane) scene.getRoot();
                root.getChildren().add(inventoryPane);
                System.out.println("Added inventory to StackPane root");
            } else {
                System.out.println("Cannot add inventory - scene root is not a StackPane");
                return;
            }
            
            inventoryPane.requestFocus();
            inventoryOpened = true;
            System.out.println("Inventory opened");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }

}
