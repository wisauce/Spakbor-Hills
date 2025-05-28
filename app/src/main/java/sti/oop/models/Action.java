package sti.oop.models;

import sti.oop.action.Marry;
import sti.oop.action.Proposing;
import sti.oop.controllers.FarmController;
import sti.oop.models.Item.Seed;
import sti.oop.models.Item.Seed.Season;
import sti.oop.models.Land.LandState;

public class Action implements Actor {
  FarmController farmController;

  public Action(FarmController farmController) {
    this.farmController = farmController;
  }

  @Override
  public void act(Teleporter visited) {
    farmController.changeMap(visited.getDestination());
  }

  @Override
  public void act(NPCArea acted) {
    if (acted.getChoosen_act() == null) return;
    if (acted.getChoosen_act().equals("Marry")) {
      Marry marry = new Marry();
      marry.doMarry(farmController.getPlayerController().getPlayer(), acted.getNpc());
    }
    if (acted.getChoosen_act().equals("Propose")) {
      Proposing proposing = new Proposing();
      proposing.doProposing(farmController.getPlayerController().getPlayer(), acted.getNpc());
    }
  }

  @Override
  public void act(Land acted) {
    if (acted.getState().equals(LandState.TILLABLE_LAND)) { //&& farmController.getPlayerController().getItemOnHand().equals(acted.getRequiredblablalbla)) { // tunggu di implement ardi
      farmController.getPlayerController().getPlayer().setEnergy(farmController.getPlayerController().getPlayer().getEnergy()-5);
      acted.changeLandState(LandState.TILLED_LAND);
      // belum implement time
    } else if (acted.getState().equals(LandState.TILLED_LAND)) {
      if (true) { // nanti true ganti sama getitemOnHand, IF yang dipegang seed 
        farmController.getPlayerController().getPlayer().setEnergy(farmController.getPlayerController().getPlayer().getEnergy()-5);
        acted.changeLandState(LandState.PLANTED_LAND);
        acted.setSeed(new Seed("Testing", 1, 2, Season.AUTUMN)); // nanti ganti jadi seed on hand
      }
      // if (item di tangan adalah pickaxe) {
      //     balik ke state TILLED LAND
      // }
    } else if (acted.getState().equals(LandState.PLANTED_LAND)) { // && PUNYA WATERING CAN
      farmController.getPlayerController().getPlayer().setEnergy(farmController.getPlayerController().getPlayer().getEnergy()-5);
      acted.setDaysNotWatered(0);
      acted.changeLandState(LandState.HARVESTABLE_LAND); // NANTI DIKOMEN, CUMA BUAT TESTING
    } else if (acted.getState().equals(LandState.HARVESTABLE_LAND)) { 
      farmController.getPlayerController().getPlayer().getInventory().addItem(acted.getCrop(), 1);
      acted.setCrop(null);      
    }
    
  }

  
}