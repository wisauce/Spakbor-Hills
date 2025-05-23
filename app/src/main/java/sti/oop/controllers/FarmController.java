package sti.oop.controllers;

import java.io.IOException;

import javafx.animation.AnimationTimer;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Player.Gender;

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




public class FarmController {
    @FXML
    private BorderPane hud;

    @FXML
    private Canvas canvas;

    private Scene scene;
    private GraphicsContext gc;


    private Player player;
    private MapController mapController;
    private CollisionController collisionController;
    private PlayerController playerController;
    private PlayerRenderer playerRenderer;

    private long lastTime = 0;
    private int inGameHour = 6;
    private int inGameMinute = 0;
    private String timeOfDay = "AM";
    private boolean timeFrozen = false;


    private final int MINUTES_PER_SECOND = 5;

    private long lastFrameTime = 0; 
    private double diffTime;

    private NPC mayorTadi;
    private NPC abigail;
    private NPC caroline;
    private NPC emily;
    private NPC dasco;
    private NPC perry;

    int spriteCounter = 0;
    int idleCounter = 0;
    boolean isIdle = true;
    

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        mapController.render(player.getX(), player.getY(), playerRenderer.getScreenX(), playerRenderer.getScreenY());
        playerController.playerUpdate(collisionController, gc, diffTime);

        // Calculate the camera offset
        int offsetX = playerRenderer.getScreenX() - player.getX();
        int offsetY = playerRenderer.getScreenY() - player.getY();
        
        // Draw NPCs with offset applied
        gc.drawImage(mayorTadi.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, 
                    mayorTadi.getLocation().x + offsetX, mayorTadi.getLocation().y + offsetY, 128, 128);
        gc.drawImage(abigail.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, 
                    abigail.getLocation().x + offsetX, abigail.getLocation().y + offsetY, 128, 128);
        gc.drawImage(caroline.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, 
                    caroline.getLocation().x + offsetX, caroline.getLocation().y + offsetY, 128, 128);
        gc.drawImage(emily.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, 
                    emily.getLocation().x + offsetX, emily.getLocation().y + offsetY, 128, 128);
        gc.drawImage(dasco.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, 
                    dasco.getLocation().x + offsetX, dasco.getLocation().y + offsetY, 128, 128);
        gc.drawImage(perry.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, 
                    perry.getLocation().x + offsetX, perry.getLocation().y + offsetY, 128, 128);
        
        // Draw clock
        String hourString = (inGameHour < 10) ? "0" + inGameHour : String.valueOf(inGameHour);
        String minuteString = (inGameMinute < 10) ? "0" + inGameMinute : String.valueOf(inGameMinute);
        String timeString = hourString + ":" + minuteString + " " + timeOfDay;
        
        // Draw clock background
        gc.setFill(new Color(0, 0, 0, 0.6));  // Semi-transparent black
        gc.fillRoundRect(canvas.getWidth() - 110, 10, 100, 30, 10, 10);
        
        // Draw clock text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.fillText(timeString, canvas.getWidth() - 100, 30);
    }

    @FXML
    public void initialize() {
        /* Initialize Player */
        player = new Player("Asep", Gender.MALE, "Asep's diary", 50, 4);

        

        /* Initialize NPC */

        mayorTadi = new MayorTadi();
        abigail = new Abigail();
        caroline = new Caroline();  
        emily = new Emily();
        dasco = new Dasco();
        perry = new Perry();

        AnimationTimer gameTime = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastFrameTime == 0) {
                    lastFrameTime = now;
                }
                
                diffTime = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;
                
                if (diffTime > 0.1) {
                    diffTime = 0.1;
                }
                
                playerController.playerUpdate(collisionController, gc, diffTime);

                if (lastTime == 0) {
                    lastTime = now;
                }

                long diffTime = now - lastTime;

                if (diffTime >= 1_000_000_000) {
                    updateGameTime();
                    lastTime = now;
                }

                render();
            }
        };
        
        /* Delay until scene is ready */
        Platform.runLater(() -> {
            scene = hud.getScene();
            gc = canvas.getGraphicsContext2D();
            playerRenderer = new PlayerRenderer(gc);
            playerController = new PlayerController(scene, player, playerRenderer, this);
            mapController = new MapController(gc,"/tileSheet/farm/cek2.png","/tileSheet/farm/pond.txt", 128);
            collisionController = new CollisionController("/tileSheet/farm/pondCollision.txt");
            playerController.inputMovementHandler();
            
            // Start the animation timer only after initialization is complete
            gameTime.start();
        });
    }
    

    /* In game time logics */
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

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

}