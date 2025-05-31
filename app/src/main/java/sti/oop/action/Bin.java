package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.Player;

public class Bin {
    public String doBin(Player player, FarmController farmController) {
        player.clearShippingBin();

        player.binOpen();
        farmController.toggleInventory();

        return "Select up to 16 unique items to ship. Press 'Ship Items' when ready.";
    }
}
