package sti.oop.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sti.oop.models.Player;

public class InventoryController {

  @FXML
  private StackPane inventoryPane;

  @FXML
  private GridPane inventoryGrid;

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
  private PlayerController playerController;

  @FXML
  public void initialize() {
    inventoryPane.setFocusTraversable(true);
    inventoryPane.requestFocus();

    gc = playerInventoryCanvas.getGraphicsContext2D();
    gc.setImageSmoothing(false);
    if (inventoryGrid != null) {
      createInventorySlots();
    }
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderPlayerinInventory();
            }
        };
        animationTimer.start();
    }
    
    private void createInventorySlots() {
        inventoryGrid.getChildren().clear();


        inventoryGrid.setHgap(7.5);
        inventoryGrid.setVgap(13.3);

        inventoryGrid.setPadding(new javafx.geometry.Insets(0, 0, 39, -2));

        for (int i = 0; i < 6; i++) { //column
            for (int j = 0; j < 3; j++) { //row
                StackPane slotsInventory= new StackPane();
                slotsInventory.setPrefSize(69, 69);
                slotsInventory.setMinSize(69, 69);
                slotsInventory.setMaxSize(69, 69);
                slotsInventory.setPickOnBounds(true);

                slotsInventory.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(139, 69, 19); -fx-border-width: 1;");
                
                int column = i;
                int row = j;

                slotsInventory.setOnMouseEntered(e -> slotsInventory.setStyle("-fx-border-color:#B52121; -fx-border-width: 1;"));
                slotsInventory.setOnMouseExited(e -> slotsInventory.setStyle("-fx-border-color: rgba(139, 69, 19, 0); -fx-border-width: 1;"));
                slotsInventory.setOnMouseClicked(e -> { 
                    System.out.println("Slot: " + row + "," + column + " Selected");

                    // handleItemSelection(row, column);
                });
                
                inventoryGrid.add(slotsInventory, i, j);
            }
        }
    }

    // private void handleItemSelection(int i, int j) {
    //     if (player != null && player.getInventory() != null) {
    //         int idx_slotsInventory = i * 6 + j; 

    //         Set<String> allItems = 
    //     }
    // }
    

    
    
    public void setPlayer(PlayerController playerController) {
      this.playerController = playerController;
      updatePlayerStats();
      // Tambahkan update inventory items disini jika perlu
      // updateInventoryItems();
    }

  public void updatePlayerStats() {
    if (playerController != null) {
      playerNameLabel.setText(playerController.getPlayer().getName());
      farmNameLabel.setText(playerController.getPlayer().getFarmName());
      goldLabel.setText("Current Gold: " + playerController.getPlayer().getGold() + "g");
    }
  }

  public void renderPlayerinInventory() {
    gc.clearRect(0, 0, playerInventoryCanvas.getWidth(), playerInventoryCanvas.getHeight());

    animationCounter = (animationCounter + 1) % 50;
    if (animationCounter == 49) {
      frameX = (frameX + 1) % 2;
    }

    gc.drawImage(playerController.getPlayerSpriteSheet(), frameX * playerController.getPlayerFrameHeight(), 0,
        playerController.getPlayerFrameWidth(), playerController.getPlayerFrameHeight(), 0, 0,
        playerInventoryCanvas.getWidth(), playerInventoryCanvas.getHeight());
  }

  @FXML
  public void handleKeyPress(KeyEvent event) {
    if (event.getCode() == KeyCode.E || event.getCode() == KeyCode.ESCAPE) {
      closeInventory();
      event.consume();
    }
  }

  @FXML
  public void handleClose() {
    closeInventory();
  }

  private void closeInventory() {
    if (farmController != null) {
      animationTimer.stop();
      farmController.toggleInventory();
    }
  }

  public void setFarmController(FarmController controller) {
    this.farmController = controller;
  }
}
