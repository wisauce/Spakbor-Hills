package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.Farm;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Seed;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;

public class Farming {
  public String doFarm(Player player, Land land, FarmController farmController) {
    String actionResult = null;
    if (player.getEnergy() - land.getEnergyRequired() >= player.getMIN_ENERGY()) {
      Item onHandItem = player.getOnHandItem();
      if (land.getState().equals(LandState.TILLABLE_LAND)) {
        if (player.hasItemInHand("Hoe")) {
          player.setEnergy(player.getEnergy() - land.getEnergyRequired());
          land.changeLandState(LandState.TILLED_LAND);
          updateTimeFarming(farmController.getFarm());
        } else {
          actionResult =  "You need a Hoe to till the land!";
        }
        // belum implement time
      }

      else if (land.getState().equals(LandState.TILLED_LAND)) {
        if (player.hasItemTypeInHand("SEED")) { // nanti true ganti sama getitemOnHand, IF yang dipegang seed
          player.setEnergy(player.getEnergy() - land.getEnergyRequired());
          land.changeLandState(LandState.PLANTED_LAND);
          Seed seed = (Seed) onHandItem;
          land.setSeed(seed); // nanti ganti jadi seed on hand
          land.setDaysToHarvest(seed.getDayToHarvest());
          player.getInventory().removeItem(onHandItem, 1);
          player.updateOnHandItem();
          farmController.updateHotbar();
          updateTimeFarming(farmController.getFarm());
          actionResult =  "You just planted " + seed.getItemName() + ", it can be harvested in " + seed.getDayToHarvest() + " day. Do not forget to water it every two days so it does not wither.";
        }
        
        else if (player.hasItemInHand("Pickaxe")) {
          player.setEnergy(player.getEnergy() - land.getEnergyRequired());;
          land.changeLandState(LandState.TILLABLE_LAND);
          updateTimeFarming(farmController.getFarm());
        }
        
        else {
          actionResult =  "You need seeds to plant or pickaxe to recover land!";
        }
      }

      else if (land.getState().equals(LandState.PLANTED_LAND)) { 
        if (player.hasItemInHand("WateringCan")) {
          if (!land.isTodayWatered()) {
            player.setEnergy(player.getEnergy() - land.getEnergyRequired());
            land.setTodayWatered(true);
            updateTimeFarming(farmController.getFarm());
          } else {
            actionResult = "This plant is already watered";
          }
        } else {
          actionResult =  "You need a watering can to water the plant";
        }
      }

      else if (land.getState().equals(LandState.HARVESTABLE_LAND)) {
        player.setEnergy(player.getEnergy() - land.getEnergyRequired());
        String seedName = land.getSeed().getItemName();
        String cropName = seedName.replaceAll("(?i)Seeds", "");
        player.getInventory().addItemByName(cropName, 1);
        land.changeLandState(LandState.TILLED_LAND);
        land.setSeed(null);
        if (!player.getEverHarvest()) {
          player.wasEverHarvest();
          player.getInventory().addItem(ItemRegistry.createItem("VeggieSoupRecipe"), 1);
        }
        if (!player.getEverHotPepper() && cropName.equals("HotPepper")) {
          player.wasEverHotPepper();
          player.getInventory().addItem("FishStewRecipe", 1);
        }
        updateTimeFarming(farmController.getFarm());
        actionResult =  "Harvested " + cropName + ", it is now in your inventory";
      }
    } else {
      actionResult =  "you are too tired to farm today";
    }
    return actionResult;
  } 

  private void updateTimeFarming(Farm farm) {
      int currentHour = farm.getInGameHour();
      int currentMinute = farm.getInGameMinute();
      String timeOfDay = farm.getTimeOfDay();

      int newMinute = currentMinute + 5;
      int newHour = currentHour;
      String newTimeOfDay = timeOfDay;

      if (newMinute >= 60) {
        newMinute -= 60;
        newHour++;
        if (newHour == 12 && timeOfDay.equals("AM")) {
          newTimeOfDay = "PM";
        }
        else if (newHour == 12 && timeOfDay.equals("PM")) {
          newTimeOfDay = "AM";
        }

        else if (newHour > 12) {
          newHour = 1;
        }
      }

      farm.setTime(newHour, newMinute);

      if (!newTimeOfDay.equals(timeOfDay)) {
        farm.setTimeOfDay(newTimeOfDay);
        if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
          farm.nextDay();
        }
      }
  }
}
