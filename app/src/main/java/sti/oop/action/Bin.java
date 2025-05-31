package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.Player;

public class Bin {
    public String doBin(Player player, FarmController farmController) {
        player.binOpen();
        farmController.toggleInventory();
        player.binClose();
        return "TEST";
    }
}
