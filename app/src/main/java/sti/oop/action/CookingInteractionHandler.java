package sti.oop.action;

import sti.oop.controllers.PanelController;
import sti.oop.models.Farm;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Player;
import sti.oop.models.Item.Recipe;
import sti.oop.models.assets.CookingArea;

public class CookingInteractionHandler {
  public void handleInteraction(CookingArea cookingArea, Player player, Farm farm, PanelController panelController) {
    panelController.multipleOptionPanel(cookingArea.getOptions(), (CookingChoice) -> {
      switch (CookingChoice) {
        case "Fish n Chips":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("FishnChipsRecipe"), farm));
          break;
        case "Baguette":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("BaguetteRecipe"), farm));
          break;
        case "Sashimi":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("SashimiRecipe"), farm));
          break;
        case "Fugu":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("FuguRecipe"), farm));
          break;
        case "Wine":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("WineRecipe"), farm));
          break;
        case "Pumpkin Pie":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("PumpkinPieRecipe"), farm));
          break;

        case "Veggie Soup":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("VeggieSoupRecipe"), farm));
          break;
        case "Fish Stew":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("FishStewRecipe"), farm));
          break;
        case "Spakbor Salad":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("SpakborSaladRecipe"), farm));
          break;
        case "Fish Sandwich":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("FishSandwichRecipe"), farm));
          break;
        case "The Legends of Spakbor":
          panelController.showDialog(new Cooking().doCooking(player, (Recipe) ItemRegistry.createItem("TheLegendsOfSpakborRecipe"), farm));
          break;
        default:
          break;
      }
    });
  }
}