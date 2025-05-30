package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Recipe;

public class Cooking {
    //TODO: Change String agar Notification!
    public String doCooking(Player player, Recipe recipe, FarmController farmController){

        int fuel = 0;

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

            int currentHour = farmController.getTimeController().getFarm().getInGameHour();
            int currentMinute = farmController.getTimeController().getFarm().getInGameMinute();
            String timeOfDay = farmController.getTimeController().getFarm().getTimeOfDay();

            int newHour = currentHour + 1;
            String newTimeOfDay = timeOfDay;

            if (newHour > 12) {
                newHour = 1;
                if (timeOfDay.equals("AM")) {
                    newTimeOfDay = "PM";
                } else { 
                    newTimeOfDay = "AM";
                }
            }

            farmController.getTimeController().getFarm().setTime(newHour, currentMinute);

            if (!newTimeOfDay.equals(timeOfDay)) {
                if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
                    farmController.getTimeController().getFarm().nextDay();
                }
            }
        
            if (recipe.getItemName().equals("FishnChipsRecipe")) {
                Item Wheat = ItemRegistry.createItem("Wheat");
                Item Potato = ItemRegistry.createItem("Potato");
                if (player.getInventory().getItemCount(Wheat) >= 1 && player.getInventory().getItemCount(Potato) >= 1) {
                    player.getInventory().removeItem(Wheat, 1);
                    player.getInventory().removeItem(Potato, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("FishnChips"), 1);

                    fuel--;
                    return "Sucess! Fish n' Chips has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + Wheat.getItemName() + " 1x, " + Potato.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Wheat.getItemName() + " 1x, " + Potato.getItemName() + " 1x.";
                }
            } 

            else if (recipe.getItemName().equals("BaguetteRecipe")) {
                Item Wheat = ItemRegistry.createItem("Wheat");
                if (player.getInventory().getItemCount(Wheat) >= 3 ) {
                    player.getInventory().removeItem(Wheat, 3);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("Baguette"), 1);

                    fuel--;
                    return "Sucess! Baguette has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + Wheat.getItemName() + " 3x.");
                    return "You don't have enough ingredient : " + Wheat.getItemName() + " 3x.";
                }
            }

            else if (recipe.getItemName().equals("SashimiRecipe")) {
                Item Salmon = ItemRegistry.createItem("Salmon");

                if (player.getInventory().getItemCount(Salmon) >= 3) {
                    player.getInventory().removeItem(Salmon, 3);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("Sashimi"), 1);
                    
                    fuel--;
                    return "Sucess! Sashimi has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + Salmon.getItemName() + " 3x.");
                    return "You don't have enough ingredient : " + Salmon.getItemName() + " 3x.";
                }
            }

            else if (recipe.getItemName().equals("FuguRecipe")) {
                Item Pufferfish = ItemRegistry.createItem("Pufferfish");

                if (player.getInventory().getItemCount(Pufferfish) >= 1) {
                    player.getInventory().removeItem(Pufferfish, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("Fugu"), 1);
                    
                    fuel--;
                    return "Sucess! Fugu has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + Pufferfish.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Pufferfish.getItemName() + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("WineRecipe")) {
                Item Grape = ItemRegistry.createItem("Grape");

                if (player.getInventory().getItemCount(Grape) >= 2) {
                    player.getInventory().removeItem(Grape, 2);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("Wine"), 1);
                    
                    fuel--;
                    return "Sucess! Wine has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + Grape.getItemName() + " 2x.");
                    return "You don't have enough ingredient : " + Grape.getItemName() + " 2x.";
                }
            }

            else if (recipe.getItemName().equals("PumpkinPieRecipe")) {
                Item Wheat = ItemRegistry.createItem("Wheat");
                Item Egg = ItemRegistry.createItem("Egg");
                Item Pumpkin = ItemRegistry.createItem("Pumpkin");

                if (player.getInventory().getItemCount(Wheat) >= 1 && player.getInventory().getItemCount(Egg) >= 1 && player.getInventory().getItemCount(Pumpkin) >= 1) {
                    player.getInventory().removeItem(Wheat, 1);
                    player.getInventory().removeItem(Egg, 1);
                    player.getInventory().removeItem(Pumpkin, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("PumpkinPie"), 1);
                    
                    fuel--;
                    return "Sucess! PumpkinPie has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + Wheat.getItemName() + " 1x, " + Egg.getItemName() + " 1x, " + Pumpkin.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Wheat.getItemName() + " 1x, " + Egg.getItemName() + " 1x, " + Pumpkin.getItemName() + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("VeggieSoupRecipe")) {
                Item Cauliflower = ItemRegistry.createItem("Cauliflower");
                Item Parsnip = ItemRegistry.createItem("Parsnip");
                Item Potato = ItemRegistry.createItem("Potato");
                Item Tomato = ItemRegistry.createItem("Tomato");

                if (player.getInventory().getItemCount(Cauliflower) >= 1 && player.getInventory().getItemCount(Parsnip) >= 1 && player.getInventory().getItemCount(Potato) >= 1 && player.getInventory().getItemCount(Tomato) >= 1) {
                    player.getInventory().removeItem(Cauliflower, 1);
                    player.getInventory().removeItem(Parsnip, 1);
                    player.getInventory().removeItem(Potato, 1);
                    player.getInventory().removeItem(Tomato, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("VeggieSoup"), 1);
                    
                    fuel--;
                    return "Sucess! VeggieSoup has been Added to Inventory";

                }

                else {
                    System.out.println("You don't have enough ingredient : " + Cauliflower.getItemName() + " 1x, " + Parsnip.getItemName() + " 1x, " + Potato.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Cauliflower.getItemName() + " 1x, " + Parsnip.getItemName() + " 1x, " + Potato.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("FishStewRecipe")) { //TODO: FISH ANJING!
                // Item Fish = ItemRegistry.createItem("Wheat");
                Item HotPepper = ItemRegistry.createItem("HotPepper");
                Item Cauliflower = ItemRegistry.createItem("Cauliflower");

                if (player.getInventory().getItemCount(HotPepper) >= 1 && player.getInventory().getItemCount(Cauliflower) >= 2) {
                    // player.getInventory().removeItem(Fish, 1);
                    player.getInventory().removeItem(HotPepper, 1);
                    player.getInventory().removeItem(Cauliflower, 2);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("FishStew"), 1);
                    
                    fuel--;
                    return "Sucess! FishStew has been Added to Inventory";
                }

                else {
                    System.out.println("You don't have enough ingredient : " + HotPepper.getItemName() + " 1x, " + Cauliflower.getItemName() + " 2x.");
                    return "You don't have enough ingredient : " + HotPepper.getItemName() + " 1x, " + Cauliflower.getItemName() + " 2x.";
                }
            }
            
            else if (recipe.getItemName().equals("SpakborSaladRecipe")) {
                Item Melon = ItemRegistry.createItem("Melon");
                Item Cranberry = ItemRegistry.createItem("Cranberry");
                Item Blueberry = ItemRegistry.createItem("Blueberry");
                Item Tomato = ItemRegistry.createItem("Tomato");

                if (player.getInventory().getItemCount(Melon) >= 1 && player.getInventory().getItemCount(Cranberry) >= 1 && player.getInventory().getItemCount(Blueberry) >= 1 && player.getInventory().getItemCount(Tomato) >= 1) {
                    player.getInventory().removeItem(Melon, 1);
                    player.getInventory().removeItem(Cranberry, 1);
                    player.getInventory().removeItem(Blueberry, 1);
                    player.getInventory().removeItem(Tomato, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("SpakborSalad"), 1);
                    
                    fuel--;
                    return "Sucess! SpakborSalad has been Added to Inventory";

                }

                else {
                    System.out.println("You don't have enough ingredient : " + Melon.getItemName() + " 1x, " + Cranberry.getItemName() + " 1x, " + Blueberry.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Melon.getItemName() + " 1x, " + Cranberry.getItemName() + " 1x, " + Blueberry.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("FishSandwichRecipe")) { //TODO: FISH ANJING!
                // Item Fish = ItemRegistry.createItem("Melon");
                Item Wheat = ItemRegistry.createItem("Wheat");
                Item HotPepper = ItemRegistry.createItem("HotPepper");
                Item Tomato = ItemRegistry.createItem("Tomato");

                if (player.getInventory().getItemCount(Wheat) >= 2 && player.getInventory().getItemCount(HotPepper) >= 1 && player.getInventory().getItemCount(Tomato) >= 1) {
                    // player.getInventory().removeItem(Melon, 1);
                    player.getInventory().removeItem(Wheat, 2);
                    player.getInventory().removeItem(HotPepper, 1);
                    player.getInventory().removeItem(Tomato, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("FishSandwich"), 1);
                    
                    fuel--;
                    return "Sucess! FishSandwich has been Added to Inventory";

                }

                else {
                    System.out.println("You don't have enough ingredient : " + Wheat.getItemName() + " 1x, " + HotPepper.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Wheat.getItemName() + " 1x, " + HotPepper.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.";
                }
            }

            else if (recipe.getItemName().equals("TheLegendsOfSpakborRecipe")) { //TODO: FISH ANJING!
                // Item Fish = ItemRegistry.createItem("Melon");
                Item Potato = ItemRegistry.createItem("Potato");
                Item Parsnip = ItemRegistry.createItem("Parsnip");
                Item Tomato = ItemRegistry.createItem("Tomato");

                

                if (player.getInventory().getItemCount(Potato) >= 2 && player.getInventory().getItemCount(Parsnip) >= 1 && player.getInventory().getItemCount(Tomato) >= 1) {
                    // player.getInventory().removeItem(Melon, 1);
                    player.getInventory().removeItem(Potato, 2);
                    player.getInventory().removeItem(Parsnip, 1);
                    player.getInventory().removeItem(Tomato, 1);
                    
                    player.getInventory().addItem(ItemRegistry.createItem("TheLegendsOfSpakbor"), 1);
                    
                    fuel--;
                    return "Sucess! TheLegendsOfSpakbor has been Added to Inventory";

                }

                else {
                    System.out.println("You don't have enough ingredient : " + Potato.getItemName() + " 1x, " + Parsnip.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.");
                    return "You don't have enough ingredient : " + Potato.getItemName() + " 1x, " + Parsnip.getItemName() + " 1x, " + Tomato.getItemName() + " 1x.";
                }
            }

            else {
                System.out.println("You don't have the correct recipe for " + recipe.getItemName());
                return "You don't have the correct recipe for " + recipe.getItemName();
            }
        }

        else {
            return "You don't have any fuel!";
        }

    }
}