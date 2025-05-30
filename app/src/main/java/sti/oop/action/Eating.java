package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.interfaces.Edible;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Food;
import sti.oop.models.Item.Seed;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;

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

            }

            else {
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
            actionResult = "You have eaten " + item.getItemName();

        }
        
        else {
          actionResult =  "You need a food to eat!";
        }

    return actionResult;

  } 
}
