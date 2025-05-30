package sti.oop.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.utils.ItemSpriteManager;

public class HotbarController {
    
    private ImageView onHandItemDisplay;
    private Label onHandItemName;
    private HBox hotbarContainer;
    private Player player;
    
    public HotbarController(Player player) {
        this.player = player;
        createHotbar();
    }
    
    private void createHotbar() {
        onHandItemDisplay = new ImageView();
        onHandItemDisplay.setFitHeight(64.0);
        onHandItemDisplay.setFitWidth(64.0);
        onHandItemDisplay.setPreserveRatio(true);
        
        onHandItemName = new Label("Empty");
        onHandItemName.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        
        VBox itemContainer = new VBox();
        itemContainer.setAlignment(Pos.CENTER);
        itemContainer.setSpacing(5.0);
        itemContainer.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 10;");
        itemContainer.setPadding(new Insets(10));
        itemContainer.getChildren().addAll(onHandItemDisplay, onHandItemName);

        itemContainer.setOnMouseEntered(e -> {
            if (player.getOnHandItem() != null) {
                itemContainer.setStyle("-fx-background-color: rgba(255,0,0,0.3); -fx-background-radius: 10;");
            }
        });

        itemContainer.setOnMouseExited(e -> {
            itemContainer.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 10;");
        });

        itemContainer.setOnMouseClicked(e -> {
            deselectOnHandItem();
        });

        hotbarContainer = new HBox();
        hotbarContainer.getChildren().add(itemContainer);
    }
    
    public HBox getHotbarContainer() {
        return hotbarContainer;
    }
    
    public void updateOnHandDisplay(Player player) {
        Item onHandItem = player.getOnHandItem();
        
        if (onHandItem != null) {
            int quantity = player.getInventory().getItemCount(onHandItem);

            if (quantity <= 0) {
                deselectOnHandItem();
            }
        }

        if (onHandItem != null) {
            Image sprite = ItemSpriteManager.getItemSprite(onHandItem.getItemID());
            onHandItemDisplay.setImage(sprite);
            onHandItemName.setText(onHandItem.getItemName());
        } 
        else {
            onHandItemDisplay.setImage(null);
            onHandItemName.setText("Empty");
        }
    }

    private void deselectOnHandItem() {
        if (player.getOnHandItem() != null) {
            player.setOnHandItem(null);
            player.setOnHandInventoryIndex(-1);

            updateOnHandDisplay(player);
        }
    }
}