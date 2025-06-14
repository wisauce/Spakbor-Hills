package sti.oop.controllers;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.Node;

import sti.oop.models.Player;
import sti.oop.models.StoreItemRegistry;
import sti.oop.models.Item.Item;
import sti.oop.interfaces.Valuable;
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

  private Label shippingStatusLabel;
  private Label shoppingStatusLabel;


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
                  if (player.isBinOpen()) {
                     inventorySlot.setStyle("-fx-border-color: #FFD700; -fx-border-width: 2;"); 
                  } 
                  
                  else {
                    if (player.getOnHandInventoryIndex() != idxItemGlobal) {
                      inventorySlot.setStyle("-fx-border-color:#B52121; -fx-border-width: 1;");
                    }
                  }
              });

              inventorySlot.setOnMouseExited(e -> {
                if (player.isBinOpen()) {
                  Item[] itemArray = inventory.getAllItem().toArray(new Item[0]);
                  if (idxItemGlobal < itemArray.length) {
                      Item item = itemArray[idxItemGlobal];
                      boolean inShippingBin = player.getShippingBinItems().containsKey(item);
                      if (inShippingBin) {
                          inventorySlot.setStyle("-fx-border-color: #00FF00; -fx-border-width: 2; -fx-background-color: rgba(0, 255, 0, 0.2);");
                      } else {
                          inventorySlot.setStyle("-fx-border-color: rgba(139, 69, 19, 0); -fx-border-width: 0;");
                      }
                  }
                }
                else {
                  if (player.getOnHandInventoryIndex() != idxItemGlobal) {
                      inventorySlot.setStyle("-fx-border-color: rgba(139, 69, 19, 0); -fx-border-width: 0;");
                  } 
                  else {
                      inventorySlot.setStyle("-fx-border-color: #FFD700; -fx-border-width: 3; -fx-background-color: rgba(255, 215, 0, 0.3);");
                  }
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

        boolean inShippingBin = player.isBinOpen() && player.getShippingBinItems().containsKey(ownItem);
        int shippingQuantity;
        if (inShippingBin) {
          shippingQuantity = player.getShippingBinItems().get(ownItem);
        }
        else {
          shippingQuantity = 0;
        }
          
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

          String quantityText = String.valueOf(quantity);
          if (inShippingBin) {
            quantityText += " (" + shippingQuantity + " selected)";
          }
            
          Label quantityLabel = new Label(quantityText);
          quantityLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; " + "-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 1 3 1 3; " + "-fx-background-radius: 3;");
            
          itemContainer.getChildren().addAll(spriteView, quantityLabel);
          grid.getChildren().add(itemContainer);

          if (inShippingBin) {
            grid.setStyle("-fx-border-color: #00FF00; -fx-border-width: 2; -fx-background-color: rgba(0, 255, 0, 0.2);");
          }
        } 
        
        javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(inShippingBin ? itemID + " (In Shipping Bin)" : itemID);
        javafx.scene.control.Tooltip.install(grid, tooltip);
      }
      
      grid.setOnMouseClicked(e -> {handleItemSelection(idxGrid);});

      // if (player.getOnHandInventoryIndex() == idxGrid) {
      //     grid.setStyle("-fx-border-color: #FFD700; -fx-border-width: 3;"); // Gold border for selected
      // } else {
      //   grid.setStyle("-fx-border-color: transparent; -fx-border-width: 1;");
      // }
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

              if (!player.isBinOpen()) {
                player.setOnHandInventoryIndex(idxItemGlobal);
                player.setOnHandItem(selectedItemID);
              }

              else {
                handleBinItemSelection(selectedItemID, quantity);
              }

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
        if (player != null && player.isStoreOpen()) {
          createStoreSlot(); 
        } else {
          createInventorySlot();
        }
      }
      if (player != null && player.isBinOpen()) {
        removeShippingControls();
        addShippingBinControls();
      } else if (player != null && player.isStoreOpen()) {
        removeShippingControls();
        addShoppingControls();
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
      prevPageButton.setDisable(currentPage <= 0);
    }

    if (nextPageButton != null) {
      nextPageButton.setDisable(currentPage >= totalPages - 1); 
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
    if (player != null  && player.isBinOpen()) {
      player.binClose();
    }
    if(player != null && player.isStoreOpen()) {
      player.storeClose();
    }

    if (farmController != null) {
      animationTimer.stop();
      farmController.toggleInventory();
    }
  }

  public void setFarmController(FarmController controller) {
    this.farmController = controller;
  }


  /* -------------------------------------------------------------------------- */
  /*                                 Bin Logics                                 */
  /* -------------------------------------------------------------------------- */

  private void handleBinItemSelection(Item item, int availableQuantity) {
      if (player.getShippingBinItems().containsKey(item)) {
          player.removeItemFromShippingBin(item, player.getShippingBinItems().get(item));
          System.out.println("Removed " + item.getItemName() + " from shipping bin");
      } 
      
      else {
          if (player.getShippingBinItemCount() >= player.getMaxShippingItems()) {
              System.out.println("Shipping bin is full! Maximum " + player.getMaxShippingItems() + " different items allowed.");
              return;
          }
          showQuantitySelectionDialog(item, availableQuantity);
      }

      updateShippingStatus();
  }

  private void showQuantitySelectionDialog(Item item, int maxQuantity) {
      List<String> quantityOptions = new ArrayList<>();
      for (int i = 1; i <= Math.min(maxQuantity, 10); i++) { 
          quantityOptions.add(String.valueOf(i));
      }
      
      farmController.getPanelController().multipleOptionPanel(quantityOptions, (choice) -> {
          try {
              int selectedQuantity = Integer.parseInt(choice);
              if (player.addItemToShippingBin(item, selectedQuantity)) {
                  System.out.println("Added " + selectedQuantity + "x " + item.getItemName() + " to shipping bin");
              } else {
                  System.out.println("Failed to add item to shipping bin");
              }
              updateShippingStatus();
              updateInventoryDisplay();
          } catch (NumberFormatException e) {
              System.out.println("Invalid quantity selected");
          }
      });
  }

  private void addShippingBinControls() {
    if (shippingStatusLabel == null) {
      shippingStatusLabel = new Label();
    }
    updateShippingStatus();
    shippingStatusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");
    
    Button shipButton = new Button("SHIP ITEMS");
    shipButton.setStyle("-fx-background-color: #FF6B35; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-font-size: 12px; -fx-background-radius: 10;");
    shipButton.setOnAction(e -> {
    if (player.getShippingBinItemCount() > 0) {
        farmController.getPanelController().multipleOptionPanel(
            List.of("Confirm Ship", "Cancel"),
            (choice) -> {
                if (choice.equals("Confirm Ship")) {
                    int pendingValue = player.getShippingBinItems().entrySet().stream()
                        .mapToInt(entry -> {Item item = entry.getKey(); int amount = entry.getValue();
                            if (item instanceof Valuable) {
                                return ((Valuable) item).getSellPrice() * amount;
                            }
                            return 0;
                        }).sum();
                    
                    player.shipItems();             
                    farmController.getPanelController().showDialog("Items shipped successfully! You will receive " + pendingValue + "g tomorrow morning.");
                    closeInventory();
                }
            }
        );
    } 
    
    else {
        farmController.getPanelController().showDialog("No items selected for shipping!");
    }
  });

    VBox shippingControls = new VBox(5);
    shippingControls.setAlignment(javafx.geometry.Pos.TOP_CENTER);
    shippingControls.getChildren().addAll(shippingStatusLabel, shipButton);
    
    shippingControls.setStyle("-fx-padding: 20; -fx-background-color: rgba(0, 0, 0, 0.); -fx-background-radius: 15; -fx-border-color: #FFD700; -fx-border-width: 3; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    
    StackPane.setAlignment(shippingControls, javafx.geometry.Pos.TOP_CENTER);
    StackPane.setMargin(shippingControls, new javafx.geometry.Insets(25, 0, 0, 550));
    shippingControls.setViewOrder(-1);    
    inventoryPane.getChildren().add(shippingControls);
  }

  private void updateShippingStatus() {
    if (shippingStatusLabel != null) {
      shippingStatusLabel.setText("");
    
      if (player != null && player.isBinOpen()) {
          int selectedItems = player.getShippingBinItemCount();
          int maxItems = player.getMaxShippingItems();
          shippingStatusLabel.setText("Shipping Bin: " + selectedItems + "/" + maxItems);
      }
    }
  }

  private void removeShippingControls() {
    List<Node> nodesToRemove = new ArrayList<>();
    
    for (Node node : inventoryPane.getChildren()) {
        if (node instanceof VBox) {
            VBox vbox = (VBox) node;
            for (Node child : vbox.getChildren()) {
                if (child instanceof Button && ((Button) child).getText().equals("SHIP ITEMS")) {
                    nodesToRemove.add(vbox);
                    break;
                }
            }
        }
    }
    inventoryPane.getChildren().removeAll(nodesToRemove);
    System.out.println("Removed " + nodesToRemove.size() + " shipping control panels");    
    shippingStatusLabel = null;
  }


  /* -------------------------------------------------------------------------- */
  /*                                Store Logics                                */
  /* -------------------------------------------------------------------------- */
  private void createStoreSlot() {
      inventoryGrid.getChildren().clear();
      
      inventoryGrid.setHgap(7.5);
      inventoryGrid.setVgap(13.3);
      inventoryGrid.setPadding(new javafx.geometry.Insets(0, 0, 39, -2));

      List<Item> storeItems = StoreItemRegistry.getStoreItems();
      totalPages = Math.max(1, (int) Math.ceil((double) storeItems.size() / ITEMS_PER_PAGE));
      currentPage = Math.max(0, Math.min(currentPage, totalPages - 1));

      int idxStart = currentPage * ITEMS_PER_PAGE;
      int idxEnd = Math.min(idxStart + ITEMS_PER_PAGE, storeItems.size());

      for (int i = 0; i < 6; i++) { 
          for (int j = 0; j < 3; j++) { 
              StackPane storeSlot = new StackPane();
              storeSlot.setPrefSize(69, 69);
              storeSlot.setMinSize(69, 69);
              storeSlot.setMaxSize(69, 69);
              storeSlot.setPickOnBounds(true);
              storeSlot.setStyle("-fx-background-color: transparent; -fx-border-color: rgb(139, 69, 19); -fx-border-width: 0;");
              
              int column = i;
              int row = j;
              int idxGrid = row * 6 + column;
              int idxItemGlobal = idxStart + idxGrid;

              if (idxItemGlobal < idxEnd) {
                  insertStoreItemToGrid(storeSlot, idxItemGlobal, storeItems);
              }

              storeSlot.setOnMouseEntered(e -> {
                  if (idxItemGlobal < storeItems.size()) {
                      Item item = storeItems.get(idxItemGlobal);
                      boolean inCart = player.getShoppingCartItems().containsKey(item);
                      if (inCart) {
                          storeSlot.setStyle("-fx-border-color: #00FF00; -fx-border-width: 2;");
                      } else {
                          storeSlot.setStyle("-fx-border-color: #FFD700; -fx-border-width: 2;");
                      }
                  }
              });

              storeSlot.setOnMouseExited(e -> {
                  if (idxItemGlobal < storeItems.size()) {
                      Item item = storeItems.get(idxItemGlobal);
                      boolean inCart = player.getShoppingCartItems().containsKey(item);
                      if (inCart) {
                          storeSlot.setStyle("-fx-border-color: #00FF00; -fx-border-width: 2; -fx-background-color: rgba(0, 255, 0, 0.2);");
                      } else {
                          storeSlot.setStyle("-fx-border-color: rgba(139, 69, 19, 0); -fx-border-width: 0;");
                      }
                  }
              });

              storeSlot.setOnMouseClicked(e -> { 
                  if (idxItemGlobal < storeItems.size()) {
                      System.out.println("Store Slot: " + (row + 1) + "," + (column + 1) + " Selected");
                      handleStoreItemSelection(idxItemGlobal, storeItems);
                  }
              });
              
              inventoryGrid.add(storeSlot, i, j);
          }
      }
      updatePageControl();
  }

  private void insertStoreItemToGrid(StackPane grid, int idxGrid, List<Item> storeItems) {
      if (idxGrid >= storeItems.size()) return;
      
      Item storeItem = storeItems.get(idxGrid);
      String itemID = storeItem.getItemID();
      
      boolean inCart = player.getShoppingCartItems().containsKey(storeItem);
      int cartQuantity = inCart ? player.getShoppingCartItems().get(storeItem) : 0;
      
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

          String labelText = "";
          if (storeItem instanceof sti.oop.interfaces.Valuable) {
              int price = ((sti.oop.interfaces.Valuable) storeItem).getBuyPrice();
              labelText = price + "g";
              if (inCart) {
                  labelText += " (" + cartQuantity + " in cart)";
              }
          }
          
          Label priceLabel = new Label(labelText);
          priceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-color: rgba(0,0,0,0.7); -fx-padding: 1 3 1 3; -fx-background-radius: 3;");
          
          itemContainer.getChildren().addAll(spriteView, priceLabel);
          grid.getChildren().add(itemContainer);

          if (inCart) {
              grid.setStyle("-fx-border-color: #00FF00; -fx-border-width: 2; -fx-background-color: rgba(0, 255, 0, 0.2);");
          }
      }
      
      String tooltipText = itemID;
      if (storeItem instanceof sti.oop.interfaces.Valuable) {
          tooltipText += " - " + ((sti.oop.interfaces.Valuable) storeItem).getBuyPrice() + "g";
      }
      if (inCart) {
          tooltipText += " (In Cart)";
      }
      
      javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(tooltipText);
      javafx.scene.control.Tooltip.install(grid, tooltip);
  }

  private void handleStoreItemSelection(int idxItemGlobal, List<Item> storeItems) {
      if (idxItemGlobal >= storeItems.size()) return;
      
      Item selectedItem = storeItems.get(idxItemGlobal);
      
      if (player.getShoppingCartItems().containsKey(selectedItem)) {
          player.removeItemFromShoppingCart(selectedItem, player.getShoppingCartItems().get(selectedItem));
          System.out.println("Removed " + selectedItem.getItemName() + " from cart");
      } else {
          if (player.getShoppingCartItemCount() >= player.getMaxShoppingItems()) {
              farmController.getPanelController().showDialog("Cart is full! Maximum " + player.getMaxShoppingItems() + " different items allowed.");
              return;
          }
          showPurchaseQuantityDialog(selectedItem);
      }
      
      updateShoppingStatus();
      updateInventoryDisplay();
  }

  private void showPurchaseQuantityDialog(Item item) {
      List<String> quantityOptions = new ArrayList<>();
      for (int i = 1; i <= 10; i++) { 
          quantityOptions.add(String.valueOf(i));
      }
      
      farmController.getPanelController().multipleOptionPanel(quantityOptions, (choice) -> {
          try {
              int selectedQuantity = Integer.parseInt(choice);
              if (player.addItemToShoppingCart(item, selectedQuantity)) {
                  System.out.println("Added " + selectedQuantity + "x " + item.getItemName() + " to cart");
              } else {
                  System.out.println("Failed to add item to cart");
              }
              updateShoppingStatus();
              updateInventoryDisplay();
          } catch (NumberFormatException e) {
              System.out.println("Invalid quantity selected");
          }
      });
  }

  private void addShoppingControls() {
      Button floatingBuyButton = new Button("🛒");
      floatingBuyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5; -fx-font-size: 12px; -fx-background-radius: 50%; -fx-min-width: 35px; -fx-min-height: 35px; -fx-max-width: 35px; -fx-max-height: 35px;");
      
      javafx.scene.control.Tooltip cartTooltip = new javafx.scene.control.Tooltip();
      updateCartTooltip(cartTooltip);
      javafx.scene.control.Tooltip.install(floatingBuyButton, cartTooltip);
      
      floatingBuyButton.setOnAction(e -> {
          if (player.getShoppingCartItemCount() > 0) {
              int totalCost = player.getShoppingCartTotalCost();
              farmController.getPanelController().showDialog("Cart: " + player.getShoppingCartItemCount() + " items, Total: " + totalCost + "g");
              
              farmController.getPanelController().multipleOptionPanel(
                  List.of("Buy All (" + totalCost + "g)", "Cancel"),
                  (choice) -> {
                      if (choice.startsWith("Buy All")) {
                          if (player.getGold() >= totalCost) {
                              player.purchaseItems();
                              farmController.getPanelController().showDialog("Purchased for " + totalCost + "g!");
                              closeInventory();
                          } else {
                              farmController.getPanelController().showDialog("Not enough gold!");
                          }
                      }
                  }
              );
          } else {
              farmController.getPanelController().showDialog("Cart is empty! Click items to add them.");
          }
      });
      
      StackPane.setAlignment(floatingBuyButton, javafx.geometry.Pos.TOP_RIGHT);
      StackPane.setMargin(floatingBuyButton, new javafx.geometry.Insets(5, 5, 0, 0));
      floatingBuyButton.setViewOrder(-1);
      
      inventoryPane.getChildren().add(floatingBuyButton);
  }

  private void updateCartTooltip(javafx.scene.control.Tooltip tooltip) {
      if (player != null && player.isStoreOpen()) {
          int items = player.getShoppingCartItemCount();
          int cost = player.getShoppingCartTotalCost();
          tooltip.setText("Cart: " + items + " items\nTotal: " + cost + "g\nYour Gold: " + player.getGold() + "g");
      }
  }

  private void updateShoppingStatus() {
    if (shoppingStatusLabel != null) {
      shoppingStatusLabel.setText("");
      
      if (player != null && player.isStoreOpen()) {
          int selectedItems = player.getShoppingCartItemCount();
          int maxItems = player.getMaxShoppingItems();
          int totalCost = player.getShoppingCartTotalCost();
          shoppingStatusLabel.setText("Cart: " + selectedItems + "/" + maxItems + " (Total: " + totalCost + "g)");
      }
    }
  }

  public void openStoreMode() {
    if (player != null) {
      player.storeOpen();
      updateInventoryDisplay();
    }
  }

}
