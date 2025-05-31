package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.Player;

public class Store {
    public String doStore(Player player, FarmController farmController) {
        player.clearShoppingCart();
        player.storeOpen();
        farmController.toggleInventory();

        if (farmController.getInventoryController() != null) {
            farmController.getInventoryController().openStoreMode();
        }
        return "Welcome to Emily's Store";
    }
}
