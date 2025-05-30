package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.Player;

public class Watching {
    private final int ENERGY_COST_WATCH = 5;
    private final int TIME_COST_WATCH = 15;

    public void doWatching(Player player, FarmController farmController){
        player.setEnergy(player.getEnergy()-ENERGY_COST_WATCH);
        int minutePlus = farmController.getTimeController().getFarm().getInGameMinute();
        int hourPlus = farmController.getTimeController().getFarm().getInGameHour();
        
        farmController.getTimeController().getFarm().setTime(hourPlus, minutePlus+TIME_COST_WATCH);
        System.out.println("You Are Watching TV!");
    }
}
