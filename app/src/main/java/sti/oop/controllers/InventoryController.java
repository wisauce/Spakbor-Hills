package sti.oop.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sti.oop.models.Player;

public class InventoryController {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label inventoryLabel;

    @FXML
    private Canvas playerInventoryCanvas;

    @FXML
    private Label farmNameLabel;
    @FXML
    private Label goldLabel;
    @FXML
    private Label playerNameLabel;

    private GraphicsContext gc;
    private FarmController farmController;
    private AnimationTimer animationTimer;
    private int animationCounter = 0;
    private int frameX = 0;
    private Player player;


    public void initialize() {
        inventoryLabel.setText("Press E to Close");
        
        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();

        gc = playerInventoryCanvas.getGraphicsContext2D();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderPlayerinInventory();
            }
        };
        animationTimer.start();
    }
    
        public void setPlayer(Player player) {
        this.player = player;
        updatePlayerStats();
    }

    public void updatePlayerStats() {
        if (player != null) {
            playerNameLabel.setText(player.getName());
            farmNameLabel.setText(player.getFarmName());
            goldLabel.setText("Current Gold: " + player.getGold());
        }
    }

    public void renderPlayerinInventory() {
        gc.clearRect(0, 0, playerInventoryCanvas.getWidth(), playerInventoryCanvas.getHeight());

        animationCounter = (animationCounter + 1) % 50;
        if (animationCounter == 49) {
            frameX = (frameX + 1) % 2;
        }

        gc.drawImage(Player.playerSpriteSheet, frameX * Player.playerFrameWidth, 0, Player.playerFrameHeight, Player.playerFrameWidth, 0, 0, playerInventoryCanvas.getWidth(), playerInventoryCanvas.getHeight());
    }
    

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.E) {
            if (farmController != null) {
                animationTimer.stop();
                farmController.toggleInventory();
            }
            event.consume();
        }
    }
    
    public void setFarmController(FarmController controller) {
        this.farmController = controller;
    }
    
}
