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

    for (int i = 0; i < 3; i++) { // row
      for (int j = 0; j < 6; j++) { // column
        StackPane slot = new StackPane();
        slot.setPrefSize(64, 64);
        slot.setStyle("-fx-background-color: transparent;");

        int finalRow = i;
        int finalCol = j;

        slot.setOnMouseEntered(e -> slot.setStyle(
            "-fx-border-color: #ffcc00; -fx-border-width: 2; -fx-background-color: rgba(255, 255, 255, 0.1);"));
        slot.setOnMouseExited(e -> slot.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;"));
        slot.setOnMouseClicked(e -> System.out.println("Clicked on slot: " + finalRow + "," + finalCol));

        inventoryGrid.add(slot, i, j);
      }
    }
  }

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
