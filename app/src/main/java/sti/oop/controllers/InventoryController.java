package sti.oop.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import sti.oop.models.Player;
import sti.oop.models.item.Item;
import sti.oop.models.Inventory;
import sti.oop.utils.ItemSpriteManager;

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
  
  @FXML
  private Button prevPageButton;

  @FXML
  private Button nextPageButton;

  @FXML
  private Label pageLabel;

  private GraphicsContext gc;

  private AnimationTimer animationTimer;
  private int animationCounter = 0;
  private int frameX = 0;

  private Player player;
  private FarmController farmController;
  private PlayerController playerController;

  private int currentPage = 0;
  private final int ITEMS_PER_PAGE = 18;
  private int totalPages = 1;


  /* -------------------------------------------------------------------------- */
  /*                            Initialize Inventory                            */
  /* -------------------------------------------------------------------------- */

  @FXML
  public void initialize() {
    inventoryPane.setFocusTraversable(true);
    inventoryPane.requestFocus();

    gc = playerInventoryCanvas.getGraphicsContext2D();
    gc.setImageSmoothing(false);

    currentPage = 0;
    totalPages = 1;

    animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            renderPlayerinInventory();
        }
    };
    animationTimer.start();
  }
    
  /* -------------------------------------------------------------------------- */
  /*                        Make New Inventory Grid/Slot                        */
  /* -------------------------------------------------------------------------- */

  private void createInventorySlot() {
      inventoryGrid.getChildren().clear();

      inventoryGrid.setHgap(7.5);
      inventoryGrid.setVgap(13.3);
      inventoryGrid.setPadding(new javafx.geometry.Insets(0, 0, 39, -2));

      if (player == null || player.getInventory() == null) {
        updatePageControl();
        return;
      }

      Inventory inventory = player.getInventory();
      Item[] itemAsArray = inventory.getAllItem().toArray(new Item[0]);
      totalPages = Math.max(1, (int) Math.ceil((double) itemAsArray.length / ITEMS_PER_PAGE));

      currentPage = Math.max(0, Math.min(currentPage, totalPages - 1));

      int idxStart = currentPage * ITEMS_PER_PAGE;
      int idxEnd = Math.min(idxStart + ITEMS_PER_PAGE, itemAsArray.length);

      for (int i = 0; i < 6; i++) { 
          for (int j = 0; j < 3; j++) { 
              StackPane inventorySlot = new StackPane();
              inventorySlot.setPrefSize(69, 69);
              inventorySlot.setMinSize(69, 69);
              inventorySlot.setMaxSize(69, 69);
              inventorySlot.setPickOnBounds(true);

              inventorySlot.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(139, 69, 19); -fx-border-width: 0;");
              
              int column = i;
              int row = j;
              int idxGrid = row * 6 + column;
              int idxItemGlobal = idxStart + idxGrid;

              if (idxItemGlobal < idxEnd) {
                insertInventoryToGrid(inventorySlot, idxItemGlobal);
              }

              inventorySlot.setOnMouseEntered(e -> {
                  if (player.getOnHandInventoryIndex() != idxItemGlobal) {
                      inventorySlot.setStyle("-fx-border-color:#B52121; -fx-border-width: 1;");
                  }
              });

              inventorySlot.setOnMouseExited(e -> {
                  if (player.getOnHandInventoryIndex() != idxItemGlobal) {
                      inventorySlot.setStyle("-fx-border-color: rgba(139, 69, 19, 0); -fx-border-width: 0;");
                  } 
                  else {
                      inventorySlot.setStyle("-fx-border-color: #FFD700; -fx-border-width: 3; -fx-background-color: rgba(255, 215, 0, 0.3);");
                  }
              });

              inventorySlot.setOnMouseClicked(e -> { 
                  if (idxItemGlobal < idxEnd) {
                    System.out.println("Slot: " + (row + 1) + "," + (column + 1) + " Selected");
                    handleItemSelection(idxItemGlobal);
                  }
              });
              
              inventoryGrid.add(inventorySlot, i, j);
          }
      }
    updatePageControl();
  }

  /* -------------------------------------------------------------------------- */
  /*                   Put All Inventory to Inventory Grid UI                   */
  /* -------------------------------------------------------------------------- */

  private void insertInventoryToGrid(StackPane grid, int idxGrid) {
      if (player == null || player.getInventory() == null) {
          return;
      }
      
      Inventory inventory = player.getInventory();
      Item[] itemArray = inventory.getAllItem().toArray(new Item[0]);
      
      if (idxGrid < itemArray.length) {
        Item ownItem = itemArray[idxGrid];
        String itemID = ownItem.getItemID();
        System.out.println(itemID);
        int quantity = inventory.getItemCount(ownItem);
          
        Image itemSprite = ItemSpriteManager.getItemSprite(itemID);
          
        if (itemSprite != null) {
          VBox itemContainer = new VBox();
          itemContainer.setAlignment(javafx.geometry.Pos.CENTER);
          itemContainer.setSpacing(2);
            
          ImageView spriteView = new ImageView(itemSprite);
          spriteView.setFitWidth(40);
          spriteView.setFitHeight(40);
          spriteView.setPreserveRatio(true);
          spriteView.setSmooth(false);
            
          Label quantityLabel = new Label(String.valueOf(quantity));
          quantityLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; " + "-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 1 3 1 3; " + "-fx-background-radius: 3;");
            
          itemContainer.getChildren().addAll(spriteView, quantityLabel);
          grid.getChildren().add(itemContainer);
        } 
        else {
          Label itemLabel = new Label(itemID + "\n" + quantity);
          itemLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-alignment: center;");
          grid.getChildren().add(itemLabel);
        }
        
        javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(itemID);
        javafx.scene.control.Tooltip.install(grid, tooltip);
      }
      
      grid.setOnMouseClicked(e -> {handleItemSelection(idxGrid);});

      if (player.getOnHandInventoryIndex() == idxGrid) {
          grid.setStyle("-fx-border-color: #FFD700; -fx-border-width: 3;"); // Gold border for selected
      } else {
        grid.setStyle("-fx-border-color: transparent; -fx-border-width: 1;");
      }

  }

  /* -------------------------------------------------------------------------- */
  /*                            Item Selection Logics                           */
  /* -------------------------------------------------------------------------- */

  private void handleItemSelection(int idxItemGlobal) {
      if (player != null && player.getInventory() != null) {            
          Inventory inventory = player.getInventory();
          Item[] ownItem = inventory.getAllItem().toArray(new Item[0]);
          
          if (idxItemGlobal < ownItem.length) {
              Item selectedItemID = ownItem[idxItemGlobal];
              int quantity = inventory.getItemCount(selectedItemID);

              player.setOnHandInventoryIndex(idxItemGlobal);
              player.setOnHandItem(selectedItemID);

              if (farmController != null) {
                farmController.updateHotbar();
              }

              updateInventoryDisplay();
              
              System.out.println("Selected item: " + selectedItemID.getItemName() + " (Quantity: " + quantity + ")");
              System.out.println("Page: " + (currentPage + 1) + ", Global Index: " + idxItemGlobal);
          }
      }
  }

  public void updateInventoryDisplay() {
      if (inventoryGrid != null) {
          createInventorySlot();
      }
  }

  public void handleHotbarDeselection() {
    updateInventoryDisplay(); 
  }

  /* -------------------------------------------------------------------------- */
  /*                            Inventory Page Logics                           */
  /* -------------------------------------------------------------------------- */

  private void updatePageControl() {
    if (pageLabel != null) {
      pageLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
    }

    if (prevPageButton != null) {
      prevPageButton.setDisable(currentPage <= 0); //Max can prev is 0
    }

    if (nextPageButton != null) {
      nextPageButton.setDisable(currentPage >= totalPages - 1); //Max can next is *totalPages
    }
  }

  @FXML
  private void goToPreviousPage() {
      if (currentPage > 0) {
          currentPage--;
          createInventorySlot();
      }
  }

  @FXML
  private void goToNextPage() {
      if (currentPage < totalPages - 1) {
          currentPage++;
          createInventorySlot();
      }
  }

  @FXML
  public void handleKeyPressInventory(KeyEvent e) {
    if (e.getCode() == KeyCode.F || e.getCode() == KeyCode.ESCAPE) {
      closeInventory();
      e.consume();
    }

    else if (e.getCode() == KeyCode.LEFT) {
      goToPreviousPage();
      e.consume();
    }

    else if (e.getCode() == KeyCode.RIGHT) {
      goToNextPage();
      e.consume();
    }
  }

  public void setPlayer(PlayerController playerController) {
    this.playerController = playerController;
    this.player = playerController.getPlayer();
    updatePlayerStats();
    updateInventoryDisplay(); 
  }

  /* -------------------------------------------------------------------------- */
  /*                         Player in Inventory Logics                         */
  /* -------------------------------------------------------------------------- */

  public void updatePlayerStats() {
    if (playerController != null) {
      playerNameLabel.setText(playerController.getPlayer().getName());
      farmNameLabel.setText(playerController.getPlayer().getFarmName());
      goldLabel.setText("Current Gold: " + playerController.getPlayer().getGold() + "g");
    }
  }

  public void renderPlayerinInventory() {
    if (playerController == null) {
      return;
    }

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
