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

    int adjustedSpeed = (int)Math.round(player.getSpeed() * diffTime * 60);
    if (adjustedSpeed < 1) {
        adjustedSpeed = 1;
    }
    
    int hitboxLeftEdge = player.getX() + playerRenderer.getHitboxOffsetX();
    int hitboxRightEdge = hitboxLeftEdge + playerRenderer.getHitboxWidth();
    int hitboxTopEdge = player.getY() + playerRenderer.getHitboxOffsetY();
    int hitboxBottomEdge = hitboxTopEdge + playerRenderer.getHitboxHeight();


    boolean canMoveLeft = true;
    boolean canMoveRight = true;
    
    if (keyLeftPressed) {
        int leftTestPoint = hitboxLeftEdge - 1; 
        canMoveLeft = !collisionController.isCollision(leftTestPoint, hitboxTopEdge) && !collisionController.isCollision(leftTestPoint, hitboxTopEdge + playerRenderer.getHitboxHeight()/2) && !collisionController.isCollision(leftTestPoint, hitboxBottomEdge);
    }

    if (keyRightPressed) {
        int rightTestPoint = hitboxRightEdge + 1; 
        canMoveRight = !collisionController.isCollision(rightTestPoint, hitboxTopEdge) && !collisionController.isCollision(rightTestPoint, hitboxTopEdge + playerRenderer.getHitboxHeight()/2) && !collisionController.isCollision(rightTestPoint, hitboxBottomEdge);
    }

    boolean canMoveUp = true;
    boolean canMoveDown = true;
    
    if (keyUpPressed) {
        int topTestPoint = hitboxTopEdge - 1; 
        canMoveUp = !collisionController.isCollision(hitboxLeftEdge, topTestPoint) && !collisionController.isCollision(hitboxLeftEdge + playerRenderer.getHitboxWidth()/2, topTestPoint) && !collisionController.isCollision(hitboxRightEdge, topTestPoint);
    }

    if (keyDownPressed) {
        int bottomTestPoint = hitboxBottomEdge + 1; 
        canMoveDown = !collisionController.isCollision(hitboxLeftEdge, bottomTestPoint) && !collisionController.isCollision(hitboxLeftEdge + playerRenderer.getHitboxWidth()/2, bottomTestPoint) && !collisionController.isCollision(hitboxRightEdge, bottomTestPoint);
    }

    int horizontalMovement = 0;
    int verticalMovement = 0;

    if (keyLeftPressed && canMoveLeft) {
        horizontalMovement = -adjustedSpeed;
    }
    
    else if (keyRightPressed && canMoveRight) {
        horizontalMovement = adjustedSpeed;
    }

    if (keyUpPressed && canMoveUp) {
        verticalMovement = -adjustedSpeed;
    } 
    
    else if (keyDownPressed && canMoveDown) {
        verticalMovement = adjustedSpeed;
    }


    if (horizontalMovement != 0) {
        int stepDirection;
        if (horizontalMovement > 0) {
          stepDirection = 1;
        }

        else{
          stepDirection = -1;
        } 

        for (int i = 0; i < Math.abs(horizontalMovement); i++) {
            int nextPixelPosition;
            
            if (horizontalMovement > 0) {
              nextPixelPosition = hitboxRightEdge + i + 1;
            }
            else {
              nextPixelPosition = hitboxLeftEdge - i - 1;
            }
            
            boolean pathClear = !collisionController.isCollision(nextPixelPosition, hitboxTopEdge) && !collisionController.isCollision(nextPixelPosition, hitboxTopEdge + playerRenderer.getHitboxHeight()/2) && !collisionController.isCollision(nextPixelPosition, hitboxBottomEdge);
            
            if (pathClear) {
                player.setX(player.getX() + stepDirection);
            } else {
                break; 
            }
        }
    }


    if (verticalMovement != 0) {
        int stepDirection;
        
        if (verticalMovement > 0) {
          stepDirection = 1;
        }
        else {
          stepDirection = -1;
        }

        for (int i = 0; i < Math.abs(verticalMovement); i++) {
            int nextPixelPosition;
            
            if (verticalMovement > 0) {
              nextPixelPosition = hitboxBottomEdge + i + 1;
            }
            else {
              nextPixelPosition = hitboxTopEdge - i - 1;
            }
            
            
            boolean pathClear = !collisionController.isCollision(hitboxLeftEdge, nextPixelPosition) && !collisionController.isCollision(hitboxLeftEdge + playerRenderer.getHitboxWidth()/2, nextPixelPosition) && !collisionController.isCollision(hitboxRightEdge, nextPixelPosition);
            
            if (pathClear) {
                player.setY(player.getY() + stepDirection);
            } else {
                break; 
            }
        }
    }
    

    int debugPointX1 = hitboxLeftEdge;
    int debugPointY1 = hitboxTopEdge;
    int debugPointX2 = hitboxRightEdge;
    int debugPointY2 = hitboxBottomEdge;
    

    boolean movingLeft = keyLeftPressed && canMoveLeft;
    boolean movingRight = keyRightPressed && canMoveRight;
    boolean movingUp = keyUpPressed && canMoveUp;
    boolean movingDown = keyDownPressed && canMoveDown;
    
    playerRenderer.playerMovementHandler(player, movingLeft, movingRight, movingUp, movingDown, keyUporDownJustPressed, diffTime);
    
    if (keyUporDownJustPressed) {
        keyUporDownJustPressed = false;
    }

    gc.setFill(Color.RED);
    gc.fillRect(debugPointX1, debugPointY1, 2, 2);
    gc.fillRect(debugPointX2, debugPointY2, 2, 2);
  }

  /* Inventory Logics */
  public void toggleInventory() {
    System.out.println("Toggling Inventory... Current state: " + (inventoryOpened ? "Open" : "Closed"));

    if (inventoryOpened) {
        System.out.println("Closing inventory...");

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

            inventoryPane.setMaxSize(800, 400);
            inventoryPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-border-color: yellow; -fx-border-width: 5px;");
            inventoryPane.setVisible(true);
            

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
