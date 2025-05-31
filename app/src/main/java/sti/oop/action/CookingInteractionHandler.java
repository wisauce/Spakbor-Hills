package sti.oop.action;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import sti.oop.controllers.PanelController;
import sti.oop.models.Farm;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Player;
import sti.oop.models.Item.Recipe;
import sti.oop.models.assets.CookingArea;
public class CookingInteractionHandler {
  
  public void handleInteraction(CookingArea cookingArea, Player player, Farm farm, PanelController panelController, Action action) {
    handleCookingWithFuelCheck(cookingArea, player, farm, panelController, action, 0);
  }
  
  private void handleCookingWithFuelCheck(CookingArea cookingArea, Player player, Farm farm, PanelController panelController, Action action, int remainingFuel) {
    String message = remainingFuel > 0 ? 
      "Fuel remaining: " + remainingFuel + ". Cook another recipe?" : 
      "What would you like to cook?";
      
    panelController.showDialog(message);
    
    panelController.multipleOptionPanel(cookingArea.getOptions(), (cookingChoice) -> {
      final Cooking.CookingResult result;
      
      switch (cookingChoice) {
        case "FishnChips":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("FishnChipsRecipe"), farm, remainingFuel);
          break;
        case "Baguette":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("BaguetteRecipe"), farm, remainingFuel);
          break;
        case "Sashimi":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("SashimiRecipe"), farm, remainingFuel);
          break;
        case "Fugu":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("FuguRecipe"), farm, remainingFuel);
          break;
        case "Wine":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("WineRecipe"), farm, remainingFuel);
          break;
        case "Pumpkin Pie":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("PumpkinPieRecipe"), farm, remainingFuel);
          break;

        case "Veggie Soup":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("VeggieSoupRecipe"), farm, remainingFuel);
          break;
        case "Fish Stew":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("FishStewRecipe"), farm, remainingFuel);
          break;
        case "Spakbor Salad":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("SpakborSaladRecipe"), farm, remainingFuel);
          break;
        case "Fish Sandwich":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("FishSandwichRecipe"), farm, remainingFuel);
          break;
        case "The Legends of Spakbor":
          result = new Cooking().doCookingWithFuel(player, (Recipe) ItemRegistry.createItem("TheLegendsOfSpakborRecipe"), farm, remainingFuel);
          break;
        default:
          result = new Cooking.CookingResult("Unknown recipe selected: " + cookingChoice, remainingFuel);
          break;
      }
      panelController.showDialog(result.getMessage());

      if (result.getRemainingFuel() > 0 && result.getMessage().startsWith("Success!")) {
        Timeline cookingTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
          panelController.multipleOptionPanel(java.util.List.of("Cook more", "Stop cooking"), 
          (choice) -> {
            if (choice.equals("Cook more")) {
              handleCookingWithFuelCheck(cookingArea, player, farm, panelController, action, result.getRemainingFuel());
            } else {
              farm.setTimeFrozen(false);
              action.toggleStoveOpen();
            }
          }
        );
        }));
        cookingTimeline.play();
      } else {
        farm.setTimeFrozen(false);
        action.toggleStoveOpen();
      }
      
    });
  }
}