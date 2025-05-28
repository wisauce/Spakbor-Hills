package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.interfaces.Actor;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Seed;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.utils.Constants;

public class Action implements Actor {
  FarmController farmController;

  public Action(FarmController farmController) {
    this.farmController = farmController;
  }

  public boolean isActionDoable(EnergyConsuming acted) {
    return farmController.getPlayerController().getPlayer().getEnergy() - acted.getEnergyRequired() >= farmController
        .getPlayerController().getPlayer().getMIN_ENERGY();
  }

  public void autoSleep(int energyLeft) {
    if (energyLeft == -20) {
      new Sleep().sleep(farmController, 3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE);
    }
  }

  @Override
  public void act(Teleporter acted) {
    if (isActionDoable(acted)) {
      farmController.changeMap(acted.getDestination());

    } else {

    }
  }

  @Override
  public void act(NPCArea acted) {
    Marry marry = new Marry();
    Proposing proposing = new Proposing();
    Gifting gift = new Gifting();
    Chatting chatting = new Chatting();

    if (acted.getChoosen_act() == null)
      return;
    if (acted.getChoosen_act().equals("Marry")) {
      if (isActionDoable(marry)) {
        marry.doMarry(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }
    } else if (acted.getChoosen_act().equals("Propose")) {
      if (isActionDoable(proposing)) {
        proposing.doProposing(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }
    } else if (acted.getChoosen_act().equals("Chat")) {
      if (isActionDoable(proposing)) {
        chatting.doChatting(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }
    } else if (acted.getChoosen_act().equals("Gift")) {
      if (isActionDoable(proposing)) {
        gift.doGifting(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }

    }
  }

  @Override
  public void act(Land acted) {
    if (isActionDoable(acted)) { 
      Player player = farmController.getPlayerController().getPlayer();
      Item onHandItem = player.getOnHandItem();
      if (player.hasItemInHand("Hoe") || player.hasItemTypeInHand("SEED") || player.hasItemInHand("pickaxe") || player.hasItemInHand("WateringCan")) {
        if (acted.getState().equals(LandState.TILLABLE_LAND)) { 
          if (player.hasItemInHand("Hoe")) {
            player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - acted.getEnergyRequired());
            acted.changeLandState(LandState.TILLED_LAND);
          }
    
          else {
            System.out.println("You need a Hoe to till the land!");
          }
          // belum implement time
        }
    
        else if (acted.getState().equals(LandState.TILLED_LAND)) {
          if (player.hasItemTypeInHand("SEED")) { // nanti true ganti sama getitemOnHand, IF yang dipegang seed
            player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - acted.getEnergyRequired());
            acted.changeLandState(LandState.PLANTED_LAND);
            acted.setSeed((Seed) onHandItem); // nanti ganti jadi seed on hand
    
            player.getInventory().removeItem(onHandItem, 1);
            player.updateOnHandItem();
          }
    
          else {
            System.out.println("You need seeds to plant!");
          }
  
          if (player.hasItemInHand("pickaxe")) {
  
          }
          // if (item di tangan adalah pickaxe) {
          // balik ke state TILLED LAND
          // }
        }
    
        else if (acted.getState().equals(LandState.PLANTED_LAND)) { // && PUNYA WATERING CAN
          if (player.hasItemInHand("WateringCan")) {
            player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - acted.getEnergyRequired());
            acted.setDaysNotWatered(0);
            acted.changeLandState(LandState.HARVESTABLE_LAND); // NANTI DIKOMEN, CUMA BUAT TESTING
          } else {
            System.out.println("You need a watering can to water the plant");
          }
        }
    
        else if (acted.getState().equals(LandState.HARVESTABLE_LAND)) {
          player.getInventory().addItem(acted.getCrop(), 1);
          acted.setCrop(null);
          acted.changeLandState(LandState.TILLED_LAND);
        }
        autoSleep(player.getEnergy());
      } else {
  
      }
        
      }
  }

  @Override
  public void act(SleepingArea acted) {
    Sleep sleep = new Sleep();
    sleep.sleep(farmController,
        acted.getSpawnAreaX(), acted.getSpawnAreaY());
  }

}