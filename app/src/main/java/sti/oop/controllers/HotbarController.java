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
    
    public HotbarController() {
        createHotbar();
    }
    
    private void createHotbar() {
        // Create UI elements programmatically
        onHandItemDisplay = new ImageView();
        onHandItemDisplay.setFitHeight(64.0);
        onHandItemDisplay.setFitWidth(64.0);
        onHandItemDisplay.setPreserveRatio(true);
        
        onHandItemName = new Label("Empty");
        onHandItemName.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        
        // Create container
        VBox itemContainer = new VBox();
        itemContainer.setAlignment(Pos.CENTER);
        itemContainer.setSpacing(5.0);
        itemContainer.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 10;");
        itemContainer.setPadding(new Insets(10));
        itemContainer.getChildren().addAll(onHandItemDisplay, onHandItemName);
        
        hotbarContainer = new HBox();
        hotbarContainer.getChildren().add(itemContainer);
    }
    
    public HBox getHotbarContainer() {
        return hotbarContainer;
    }
    
    public void updateOnHandDisplay(Player player) {
        Item onHandItem = player.getOnHandItem();
        
        if (onHandItem != null) {
            // Show item sprite
            Image sprite = ItemSpriteManager.getItemSprite(onHandItem.getItemID());
            onHandItemDisplay.setImage(sprite);
            onHandItemName.setText(onHandItem.getItemName());
        } else {
            // Show empty hand
            onHandItemDisplay.setImage(null);
            onHandItemName.setText("Empty");
        }
    }
}