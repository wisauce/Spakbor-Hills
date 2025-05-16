package sti.oop.controllers;

import java.io.IOException;
import java.util.Stack;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sti.oop.models.Abigail;
import sti.oop.models.Caroline;
import sti.oop.models.Dasco;
import sti.oop.models.Emily;
import sti.oop.models.Farm;
import sti.oop.models.MayorTadi;
import sti.oop.models.NPC;
import sti.oop.models.Perry;
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

  private long lastTime = 0;
  private int inGameHour = 6;
  private int inGameMinute = 0;
  private String timeOfDay = "AM";
  private boolean timeFrozen = false;

  private final int MINUTES_PER_SECOND = 5; //5 Minute for each Second

  private Scene scene;
  private GraphicsContext gc;

  private boolean keyLeftPressed;
  private boolean keyUpPressed;
  private boolean keyDownPressed;
  private boolean keyRightPressed;

  private boolean inventoryOpened = false;
  private StackPane inventoryPane;

  private Farm farm;

  private NPC mayorTadi;
  private NPC abigail;
  private NPC caroline;
  private NPC emily;
  private NPC dasco;
  private NPC perry;

  int spriteCounter = 0;
  int idleCounter = 0;
  boolean isIdle = true;
  public void KeyHandler() {
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
        farm.getPlayer().moveUp();
        if (Player.frameY != 2) {
            Player.frameY = 2;
            Player.frameX = 2; 
            directionChanged = true;
        }
        if (spriteCounter == 9 && !directionChanged) {
            Player.frameX = 2 + (Player.frameX + 1) % 2;
        }
    }
    else if (keyDownPressed) {
        farm.getPlayer().moveDown();
        if (Player.frameY != 0) {
            Player.frameY = 0;
            Player.frameX = 2;
            directionChanged = true;
        }
        if (spriteCounter == 9 && !directionChanged) {
            Player.frameX = 2 + (Player.frameX + 1) % 2;
        }
    }

    if (keyLeftPressed) {
        farm.getPlayer().moveLeft();
        if (!keyUpPressed && !keyDownPressed) {
            if (Player.frameY != 1 || Player.frameX >= 2) {
                Player.frameY = 1;
                Player.frameX = 0;
                directionChanged = true;
            }
            if (spriteCounter == 9 && !directionChanged) {
                Player.frameX = (Player.frameX + 1) % 2;
            }
        }
    } 
    if (keyRightPressed) {
        farm.getPlayer().moveRight();
        if (!keyUpPressed && !keyDownPressed) {
            if (Player.frameY != 1 || Player.frameX < 2) {
                Player.frameY = 1;
                Player.frameX = 2;
                directionChanged = true;
            }
            if (spriteCounter == 9 && !directionChanged) {
                Player.frameX = 2 + (Player.frameX + 1) % 2;
            }
        }
    }
    
    if (isIdle) {
        idleCounter = (idleCounter + 1) % 50;
        if (idleCounter == 49) {
            if (Player.frameY == 0 || Player.frameY == 2) {
                Player.frameX = (Player.frameX + 1) % 2;
            } else {
                if (Player.frameX < 2) {
                    Player.frameX = (Player.frameX + 1) % 2;
                } else {
                    Player.frameX = 2 + (Player.frameX + 1) % 2;
                }
            }
        }
    } else {
        idleCounter = 0;
    }
  }

  public void render() {
    // gc.setFill(Color.GREEN);
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    gc.setFill(Color.WHITE);
    gc.fillRect(canvas.getWidth() - 120, 10, 110, 30);
    gc.setFill(Color.BLACK);
    gc.fillText(String.format("%d:%02d %s", inGameHour, inGameMinute, timeOfDay), canvas.getWidth() - 110, 30);

    /* Render each NPC into the World */
    gc.drawImage(mayorTadi.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, mayorTadi.getLocation().x, mayorTadi.getLocation().y, 128, 128);
    gc.drawImage(abigail.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, abigail.getLocation().x, abigail.getLocation().y, 128, 128);
    gc.drawImage(caroline.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, caroline.getLocation().x, caroline.getLocation().y, 128, 128);
    gc.drawImage(emily.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, emily.getLocation().x, emily.getLocation().y, 128, 128);
    gc.drawImage(dasco.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, dasco.getLocation().x, dasco.getLocation().y, 128, 128);
    gc.drawImage(perry.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, perry.getLocation().x, perry.getLocation().y, 128, 128);
    
    /* Render Player Last into the World */
    gc.drawImage(Player.playerSpriteSheet, Player.sourceX(), Player.sourceY(), Player.playerFrameWidth, Player.playerFrameHeight, farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, 128, 128);
    // gc.setStroke(Color.RED); 
    // Rectangle2D playerHitbox = new Rectangle2D(farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, 128, 128);
    // gc.strokeRect(playerHitbox.getMinX(), playerHitbox.getMinY(), playerHitbox.getWidth(), playerHitbox.getHeight());
    // gc.strokeRect(mayorTadi.getHitbox().getMinX(), mayorTadi.getHitbox().getMinY(), mayorTadi.getHitbox().getWidth(), mayorTadi.getHitbox().getHeight());
    // gc.strokeRect(abigail.getHitbox().getMinX(), abigail.getHitbox().getMinY(), abigail.getHitbox().getWidth(), abigail.getHitbox().getHeight());
    // gc.fillRect(farm.getPlayer().getLocation().x, farm.getPlayer().getLocation().y, tileSize, tileSize);
  }

  @FXML
  public void initialize() {
    Player player = new Player("Asep", Gender.MALE, "Asep's diary");
    farm = new Farm(player);

    mayorTadi = new MayorTadi();
    abigail = new Abigail();
    caroline = new Caroline();  
    emily = new Emily();
    dasco = new Dasco();
    perry = new Perry();
    // Delay until the scene is ready
    Platform.runLater(() -> {
      scene = hud.getParent().getScene();
      gc = canvas.getGraphicsContext2D();
      scene.setOnKeyPressed(e -> {
        System.out.println("Key pressed: " + e.getCode());
        switch (e.getCode()) {
          case KeyCode.A -> keyLeftPressed = true;
          case KeyCode.W -> keyUpPressed = true;
          case KeyCode.S -> keyDownPressed = true;
          case KeyCode.D -> keyRightPressed = true;
          case KeyCode.E -> toggleInventory();
          case KeyCode.SHIFT -> player.setRun(true);
          default -> {}
        }
      });

      scene.setOnKeyReleased(e -> {
        switch (e.getCode()) {
          case KeyCode.A -> keyLeftPressed = false;
          case KeyCode.W -> {keyUpPressed = false; Player.frameX = 0;}
          case KeyCode.S -> {keyDownPressed = false; Player.frameX = 0;}
          case KeyCode.D -> keyRightPressed = false;
          case KeyCode.SHIFT -> player.setRun(false);
          default -> {}
        }
      });
    });

    AnimationTimer gameTime = new AnimationTimer() {
        @Override
        public void handle(long now) {
            // System.out.println(now);
            if (lastTime == 0) {
                lastTime = now;
            }

            long diffTime = now - lastTime;
            if (diffTime >= 1_000_000_000) {
                updateGameTime();
                lastTime = now;
            }
            KeyHandler();
            render();
        }
    };

    gameTime.start();
  }

  public void updateGameTime() {
    if (timeFrozen) {
        return;
    }

    inGameMinute += MINUTES_PER_SECOND;

    if (inGameMinute >= 60) {
        inGameHour += inGameMinute / 60;
        inGameMinute %= 60;

        if (inGameHour == 12) {
            if (timeOfDay.equals("AM")) {
                timeOfDay = "PM";
            }
            else {
                timeOfDay = "AM";
            }
        }
        else if (inGameHour > 12) {
            inGameHour %= 12;
            if (inGameHour == 0) {
                inGameHour = 12;
            }
        }

        /* Can update with a message whenever new Day --> (inGameHour == 12 && timeOfDay.equals("AM")) */
    }
  }

  public void freezeTime() {
    timeFrozen = true;
  }

  public void unfreezeTime() {
    timeFrozen = false;
  }

  public void toggleInventory() {
    System.out.println("Toggling Inventory... Current state: " + (inventoryOpened ? "Open" : "Closed"));

    if (inventoryOpened) {
        System.out.println("Closing inventory...");
        hud.getChildren().remove(inventoryPane);
        inventoryOpened = false;
        unfreezeTime();
        System.out.println("Inventory closed");
    }
    else {
        System.out.println("Opening inventory...");
        freezeTime();
        keyLeftPressed = false;
        keyRightPressed = false;
        keyUpPressed = false;
        keyDownPressed = false;
        
        try {
            FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("/views/Inventory.fxml"));
            Parent inventoryParent = inventoryLoader.load();
            inventoryPane = (StackPane) inventoryParent;
            
            InventoryController inventoryController = inventoryLoader.getController();
            inventoryController.setFarmController(this);
            inventoryController.setPlayer(farm.getPlayer());
            

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