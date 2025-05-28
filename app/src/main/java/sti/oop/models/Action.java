package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.interfaces.Actor;
import sti.oop.models.Land.LandState;
import sti.oop.models.Land;
import sti.oop.models.NPCArea;
import sti.oop.models.Player;
import sti.oop.models.Teleporter;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Seed;
import sti.oop.models.Item.Seed.Season;

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
    if (acted.getChoosen_act().equals("Chat")) {
      Chatting chatting = new Chatting();
      chatting.doChatting(farmController.getPlayerController().getPlayer(), acted.getNpc());
    }
    if (acted.getChoosen_act().equals("Gift")) {
      Gifting gift = new Gifting();
      gift.doGifting(farmController.getPlayerController().getPlayer(), acted.getNpc());
    }
  }

  @Override
  public void act(Land acted) {
    Player player = farmController.getPlayerController().getPlayer();
    Item onHandItem = player.getOnHandItem();

    if (acted.getState().equals(LandState.TILLABLE_LAND)) { //&& farmController.getPlayerController().getItemOnHand().equals(acted.getRequiredblablalbla)) { // tunggu di implement ardi
      
      if (player.hasItemInHand("Hoe")) {
        player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy()-5);
        acted.changeLandState(LandState.TILLED_LAND);
      }

      else {
        System.out.println("You need a Hoe to till the land!");
      }
      // belum implement time
    } 
    
    else if (acted.getState().equals(LandState.TILLED_LAND)) {
      if (player.hasItemTypeInHand("SEED")) { // nanti true ganti sama getitemOnHand, IF yang dipegang seed 
        player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy()-5);
        acted.changeLandState(LandState.PLANTED_LAND);
        acted.setSeed((Seed) onHandItem); // nanti ganti jadi seed on hand

        player.getInventory().removeItem(onHandItem, 1);
        player.updateOnHandItem();
      }

      else {
        System.out.println("You need seeds to plant!");
      }
      // if (item di tangan adalah pickaxe) {
      //     balik ke state TILLED LAND
      // }
    } 
    
    else if (acted.getState().equals(LandState.PLANTED_LAND)) { // && PUNYA WATERING CAN
      if (player.hasItemInHand("WateringCan")) {
        player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy()-5);
        acted.setDaysNotWatered(0);
        acted.changeLandState(LandState.HARVESTABLE_LAND); // NANTI DIKOMEN, CUMA BUAT TESTING
      } 
      else {
        System.out.println("You need a watering can to water the plant");
      }
    } 
    
    else if (acted.getState().equals(LandState.HARVESTABLE_LAND)) { 
        player.getInventory().addItem(acted.getCrop(), 1);
        acted.setCrop(null);
        acted.changeLandState(LandState.TILLED_LAND);
    }
    
  }

  
}