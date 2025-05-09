package sti.oop.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InventoryController {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label inventoryLabel;
    
    private FarmController farmController;

    public void initialize() {
        inventoryLabel.setText("Press E to Close");
        
        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.E) {
            if (farmController != null) {
                farmController.toggleInventory();
            }
            event.consume();
        }
    }
    
    public void setFarmController(FarmController controller) {
        this.farmController = controller;
    }
}
