package sti.oop.controllers;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
import sti.oop.utils.Constants;



public class FarmController {
    @FXML
    private Label timeDisplay;

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

    public void render() {}

    @FXML
    public void initialize() {

        /* Initialize Player */
        Player player = new Player("Asep", Gender.MALE, "Asep's diary");
        farm = new Farm(player);
        CollisionController collisionController = new CollisionController("/tilesheets/farm/pondCollision.txt");
        PlayerController playerController = new PlayerController(player, collisionController, this);
        MapController mapController = new MapController(player,"/tilesheets/farm/test.png","/tilesheets/farm/test.txt", 64 );

        /* Initialize NPC */
        mayorTadi = new MayorTadi();
        abigail = new Abigail();
        caroline = new Caroline();  
        emily = new Emily();
        dasco = new Dasco();
        perry = new Perry();

/* <------------------------------------------SEPERATOR------------------------------------------------> */

        AnimationTimer gameTime = new AnimationTimer() {
            @Override
            public void handle(long now) {

            if (lastTime == 0) {
                lastTime = now;
            }
            
            long diffTime = now - lastTime;
            if (diffTime >= 1_000_000_000) {
                updateGameTime();
                lastTime = now;
            }

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            mapController.render(gc);

            timeDisplay.setText(String.format("%d:%02d %s", inGameHour, inGameMinute, timeOfDay));

            renderNPCs();

            playerController.render(gc);
            }
        };
        /* Delay until scene is ready */
        Platform.runLater(() -> {
            scene = hud.getParent().getScene();
            gc = canvas.getGraphicsContext2D();
            playerController.keyMapper(scene);
            gameTime.start();
        });
    }

    /* -------------------------------------------------------------------------- */
    /*                              Render NPC Logics                             */
    /* -------------------------------------------------------------------------- */

    private void renderNPCs() {
        int x_player = farm.getPlayer().getX();
        int y_player = farm.getPlayer().getY();

        renderNPC(mayorTadi, x_player, y_player);
        renderNPC(abigail, x_player, y_player);
        renderNPC(caroline, x_player, y_player);
        renderNPC(emily, x_player, y_player);
        renderNPC(dasco, x_player, y_player);
        renderNPC(perry, x_player, y_player);
    }

    private void renderNPC(NPC NPCname, int x_player, int y_player) {
        int x_screen = NPCname.getLocation().x - x_player + Constants.PLAYER_SCREEN_X;
        int y_screen = NPCname.getLocation().y - y_player + Constants.PLAYER_SCREEN_Y;

        if (x_screen > -128 && x_screen < canvas.getWidth() + 128 && y_screen > -128 && y_screen < canvas.getHeight() + 128) {
            gc.drawImage(NPCname.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, x_screen, y_screen, 128, 128);
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                              Render NPC Logics                             */
    /* -------------------------------------------------------------------------- */

/* <------------------------------------------SEPERATOR------------------------------------------------> */

    /* -------------------------------------------------------------------------- */
    /*                             In Game Time Logics                            */
    /* -------------------------------------------------------------------------- */

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
    
    /* timeFrozen setters */
    public void freezeTime() {
        timeFrozen = true;
    }

    public void unfreezeTime() {
        timeFrozen = false;
    }

    /* -------------------------------------------------------------------------- */
    /*                             In Game Time Logics                            */
    /* -------------------------------------------------------------------------- */

/* <------------------------------------------SEPERATOR------------------------------------------------> */

    /* -------------------------------------------------------------------------- */
    /*                              Inventory Logics                              */
    /* -------------------------------------------------------------------------- */

    /* Inventory Logics */
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
            freezeTime(); // freeze time

            /* Inventory GUI render */
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

    public boolean getStatusInventory() {
        return inventoryOpened;
    }

    /* -------------------------------------------------------------------------- */
    /*                              Inventory Logics                              */
    /* -------------------------------------------------------------------------- */

}