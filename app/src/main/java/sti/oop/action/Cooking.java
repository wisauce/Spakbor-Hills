package sti.oop.action;


import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Item.Recipe;

public class Cooking {
    public static class CookingResult {
        private String message;
        private int remainingFuel;

        public CookingResult(String message, int remainingFuel) {
            this.message = message;
            this.remainingFuel = remainingFuel;
        }

        public String getMessage() {
            return message;
        }

        public int getRemainingFuel() {
            return remainingFuel;
        }
    }
    public CookingResult doCookingWithFuel(Player player, Recipe recipe, Farm farm, int fuel){
        if (fuel == 0) {
            if (player.getInventory().hasItemByName("Coal")) {
                player.getInventory().removeItemByName("Coal", 1);
                fuel += 2;
            } 

            else if (player.getInventory().hasItemByName("Firewood")){
                player.getInventory().removeItemByName("Firewood", 1);
                fuel += 1;
            }
        }

        if (fuel > 0) {

            int currentHour = farm.getInGameHour();
            int currentMinute = farm.getInGameMinute();
            String timeOfDay = farm.getTimeOfDay();

            int newHour = currentHour + 1;
            String newTimeOfDay = timeOfDay;

            if (newHour == 12 && timeOfDay.equals("AM")) {
                newTimeOfDay = "PM";
            } 
            
            else if (newHour == 12 && timeOfDay.equals("PM")) {
                newTimeOfDay = "AM";
            } 
            
            else if (newHour > 12) {
                newHour = 1;
            }

            String actionResult;
        
            if (recipe.getItemName().equals("FishnChipsRecipe")) {
                if (player.getInventory().getItemCountByName("Wheat") >= 1 && player.getInventory().getItemCountByName("Potato") >= 1 && player.getInventory().hasEnoughFish(2)) {
                    player.getInventory().removeItemByName("Wheat", 1);
                    player.getInventory().removeItemByName("Potato", 1);
                    player.getInventory().removeAnyFishWithPriority(2);
                    player.getInventory().addItemByName("FishnChips", 1);

                    fuel--;
                    actionResult = "Success! Fish n' Chips has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Wheat" + " 1x, " + "Potato" + " 1x.";
                }
            } 

            else if (recipe.getItemName().equals("BaguetteRecipe")) {
                if (player.getInventory().getItemCountByName("Wheat") >= 3 ) {
                    player.getInventory().removeItemByName("Wheat", 3);
                    
                    player.getInventory().addItemByName("Baguette", 1);

                    fuel--;
                    actionResult = "Success! Baguette has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Wheat" + " 3x.";
                }
            }

            else if (recipe.getItemName().equals("SashimiRecipe")) {

                if (player.getInventory().getItemCountByName("Salmon") >= 3) {
                    player.getInventory().removeItemByName("Salmon", 3);
                    
                    player.getInventory().addItemByName("Sashimi", 1);
                    
                    fuel--;
                    actionResult = "Success! Sashimi has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Salmon" + " 3x.";
                }
            }

            else if (recipe.getItemName().equals("FuguRecipe")) {

                if (player.getInventory().getItemCountByName("Pufferfish") >= 1) {
                    player.getInventory().removeItemByName("Pufferfish", 1);
                    
                    player.getInventory().addItemByName("Fugu", 1);
                    
                    fuel--;
                    actionResult = "Success! Fugu has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Pufferfish" + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("WineRecipe")) {
                if (player.getInventory().getItemCountByName("Grape") >= 2) {
                    player.getInventory().removeItemByName("Grape", 2);
                    
                    player.getInventory().addItemByName("Wine", 1);
                    
                    fuel--;
                    actionResult = "Success! Wine has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Grape" + " 2x.";
                }
            }

            else if (recipe.getItemName().equals("PumpkinPieRecipe")) {

                if (player.getInventory().getItemCountByName("Wheat") >= 1 && player.getInventory().getItemCountByName("Egg") >= 1 && player.getInventory().getItemCountByName("Pumpkin") >= 1) {
                    player.getInventory().removeItemByName("Wheat", 1);
                    player.getInventory().removeItemByName("Egg", 1);
                    player.getInventory().removeItemByName("Pumpkin", 1);
                    
                    player.getInventory().addItemByName("PumpkinPie", 1);
                    
                    fuel--;
                    actionResult = "Success! PumpkinPie has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Wheat"+ " 1x, " + "Egg" + " 1x, " + "Pumpkin" + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("VeggieSoupRecipe")) {
                if (player.getInventory().getItemCountByName("Cauliflower") >= 1 && player.getInventory().getItemCountByName("Parsnip") >= 1 && player.getInventory().getItemCountByName("Potato") >= 1 && player.getInventory().getItemCountByName("Tomato") >= 1) {
                    player.getInventory().removeItemByName("Cauliflower", 1);
                    player.getInventory().removeItemByName("Parsnip", 1);
                    player.getInventory().removeItemByName("Potato", 1);
                    player.getInventory().removeItemByName("Tomato", 1);
                    
                    player.getInventory().addItemByName("VeggieSoup", 1);
                    
                    fuel--;
                    actionResult = "Success! VeggieSoup has been Added to Inventory";

                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Cauliflower" + " 1x, " + "Parsnip" + " 1x, " + "Potato" + " 1x, " + "Tomato" + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("FishStewRecipe")) { 
                if (player.getInventory().getItemCountByName("HotPepper") >= 1 && player.getInventory().getItemCountByName("Cauliflower") >= 2 && player.getInventory().hasEnoughFish(2)) {
                    player.getInventory().removeItemByName("HotPepper", 1);
                    player.getInventory().removeItemByName("Cauliflower", 2);
                    player.getInventory().removeAnyFishWithPriority(2);
                    player.getInventory().addItemByName("FishStew", 1);
                    
                    fuel--;
                    actionResult = "Success! FishStew has been Added to Inventory";
                }

                else {
                    actionResult = "You don't have enough ingredient : " + "HotPepper"+ " 1x, " + "Cauliflower" + " 2x.";
                }
            }
            
            else if (recipe.getItemName().equals("SpakborSaladRecipe")) {

                if (player.getInventory().getItemCountByName("Melon") >= 1 && player.getInventory().getItemCountByName("Cranberry") >= 1 && player.getInventory().getItemCountByName("Blueberry") >= 1 && player.getInventory().getItemCountByName("Tomato") >= 1) {
                    player.getInventory().removeItemByName("Melon", 1);
                    player.getInventory().removeItemByName("Cranberry", 1);
                    player.getInventory().removeItemByName("Blueberry", 1);
                    player.getInventory().removeItemByName("Tomato", 1);
                    
                    player.getInventory().addItemByName("SpakborSalad", 1);
                    
                    fuel--;
                    actionResult = "Success! SpakborSalad has been Added to Inventory";

                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Melon" + " 1x, " + "Cranberry" + " 1x, " + "Blueberry" + " 1x, " + "Tomato" + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("FishSandwichRecipe")) { 

                if (player.getInventory().getItemCountByName("Wheat") >= 2 && player.getInventory().getItemCountByName("HotPepper") >= 1 && player.getInventory().getItemCountByName("Tomato") >= 1 && player.getInventory().hasEnoughFish(1)) {
                    player.getInventory().removeItemByName("Wheat", 2);
                    player.getInventory().removeItemByName("HotPepper", 1);
                    player.getInventory().removeItemByName("Tomato", 1);
                    player.getInventory().removeAnyFishWithPriority(1);
                    
                    player.getInventory().addItemByName("FishSandwich", 1);
                    
                    fuel--;
                    actionResult = "Success! FishSandwich has been Added to Inventory";

                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Wheat" + " 1x, " + "HotPepper" + " 1x, " + "Tomato" + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("TheLegendsOfSpakborRecipe")) { 

                if (player.getInventory().getItemCountByName("Potato") >= 2 && player.getInventory().getItemCountByName("Parsnip") >= 1 && player.getInventory().getItemCountByName("Tomato") >= 1 && player.getInventory().hasEnoughLegendaryFish(1)) {
                    player.getInventory().removeItemByName("Potato", 2);
                    player.getInventory().removeItemByName("Parsnip", 1);
                    player.getInventory().removeItemByName("Tomato", 1);
                    player.getInventory().removeAnyLegendaryFish(1);
                    
                    player.getInventory().addItemByName("TheLegendsOfSpakbor", 1);
                    
                    fuel--;
                    actionResult = "Success! TheLegendsOfSpakbor has been Added to Inventory";

                }

                else {
                    actionResult = "You don't have enough ingredient : " + "Potato" + " 1x, " + "Parsnip" + " 1x, " + "Tomato" + " 1x.";
                }
            }

            else {
                actionResult = "You don't have the correct recipe for " + recipe.getItemName();
            }


            if (!actionResult.startsWith("You don't have")){
                farm.setTime(newHour, currentMinute);

                if (!newTimeOfDay.equals(timeOfDay)) {
                    farm.setTimeOfDay(newTimeOfDay);
                    if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
                        farm.nextDay();
                    }
                }
            }

            return new CookingResult(actionResult, fuel);
        }

        else {
            return new CookingResult("You don't have any fuel!", fuel);
        }

    }

    public String doCooking(Player player, Recipe recipe, Farm farm) {
        CookingResult cookingResult = doCookingWithFuel(player, recipe, farm, 0);
        return cookingResult.getMessage();
    }
}