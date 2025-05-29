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

  public void sleepImmediately() {
    new Sleep().sleep(farmController, 3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE);
  }

  public String autoSleep(int energyLeft) {
    if (energyLeft == farmController.getPlayerController().getPlayer().getMIN_ENERGY()) {
      sleepImmediately();
      return "You were exhausted! You immediately went to sleep.";
    }
    return null;
  }

  @Override
  public String act(Teleporter acted) {
    farmController.changeMap(acted.getDestination());
    farmController.getPlayerController().getPlayer().setY(acted.getDestinationY());
    farmController.getPlayerController().getPlayer().setX(acted.getDestinationX());
    return null;
  }

  @Override
  public String act(NPCArea acted) {
    return new NPCInteractionHandler(farmController.getPlayerController()).handleInteraction(acted);
  }

  @Override
  public String act(Land acted) {
    if (isActionDoable(acted)) {
      Player player = farmController.getPlayerController().getPlayer();
      Item onHandItem = player.getOnHandItem();
      if (acted.getState().equals(LandState.TILLABLE_LAND)) {
        if (player.hasItemInHand("Hoe")) {
          player.setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - acted.getEnergyRequired());
          acted.changeLandState(LandState.TILLED_LAND);
          return autoSleep(player.getEnergy());
        } else {
          return "You need a Hoe to till the land!";
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
          return "You just planted " + onHandItem;
        }

        else if (player.hasItemInHand("pickaxe")) {
          return null;
        }

        else {
          return "You need seeds to plant or pickaxe to recover land!";
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
          return autoSleep(player.getEnergy());
        } else {
          return "You need a watering can to water the plant";
        }
      }

      else if (acted.getState().equals(LandState.HARVESTABLE_LAND)) {
        player.getInventory().addItem(acted.getCrop(), 1);
        acted.setCrop(null);
        acted.changeLandState(LandState.TILLED_LAND);
        return "Harvested " + acted.getCrop() + ", it is now in your inventory";
      }
      return null;

    } else {
      return "you are too tired to farm today";
    }

  }

  @Override
  public String act(SleepingArea acted) {
    Sleep sleep = new Sleep();
    sleep.sleep(farmController, acted.getSpawnAreaX(), acted.getSpawnAreaY());
    return "Good morning";
  }

  

}