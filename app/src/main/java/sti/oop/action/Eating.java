package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.interfaces.Edible;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Item.Food;
import sti.oop.models.Item.Item;

public class Eating {
  public String doEating(Player player, FarmController farmController) {
    String actionResult = null;
        Item item = player.getOnHandItem();
        int newEnergy = 0;
        
        if (item instanceof Edible) {
            if (player.hasItemTypeInHand("FOOD")) {
                Food food = (Food) item;
                newEnergy = player.getEnergy() + food.getEnergy();
                if (newEnergy > player.getMAX_ENERGY()) {
                    newEnergy = player.getMAX_ENERGY();
                }
            } 

            else if (player.hasItemTypeInHand("FISH")) {
                newEnergy = player.getEnergy() + 1;
                if (newEnergy > player.getMAX_ENERGY()) {
                    newEnergy = player.getMAX_ENERGY();
                }

            } else {
                newEnergy = player.getEnergy() + 3;
                if (newEnergy > player.getMAX_ENERGY()) {
                    newEnergy = player.getMAX_ENERGY();
                }
            }

            player.setEnergy(newEnergy);
            player.getInventory().removeItem(item, 1);
            if (farmController != null) {
                    farmController.updateHotbar();
            }
            updateTimeEating(farmController.getFarm());
            actionResult = "You have eaten " + item.getItemName();

        } else {
          actionResult =  "You need a food to eat!";
        }

    return actionResult;
  } 

  private void updateTimeEating(Farm farm) {
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
