package sti.oop.action;

import sti.oop.controllers.PlayerController;
import sti.oop.controllers.FarmController;
import sti.oop.models.assets.CookingArea;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Item.Recipe;

public class CookingInteractionHandler {
  private PlayerController playerController;
  private FarmController farmController;
  private boolean hasInstructionShowed = false;

  public CookingInteractionHandler(PlayerController playerController, FarmController farmController) {
    this.playerController = playerController;
    this.farmController = farmController;
  }

  public String handleInteraction(CookingArea cookingArea) {
    if (playerController.isToggled()) {
      String result = null;

      if (playerController.isKey1Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("FishnChipsRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey2Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("BaguetteRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey3Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("SashimiRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey4Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("FuguRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey5Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("WineRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey6Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("PumpkinPieRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey7Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("VeggieSoupRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey8Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("FishStewRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey9Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("SpakborSaladRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKey0Pressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("FishSandwichRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      else if (playerController.isKeyBackQuotePressed()) {
        Recipe recipe = (Recipe) ItemRegistry.createItem("TheLegendsOfSpakborRecipe");
        result = new Cooking().doCooking(playerController.getPlayer(), recipe, farmController);
      }

      if (result != null) {
        playerController.toggle(); // keluar dari interaction mode
        hasInstructionShowed = false;
        return result;
      }

      if (!hasInstructionShowed) {
        hasInstructionShowed = true;
        return "COOKING RECIPES:\n" +
            "1: Fish n' Chips    2: Baguette    3: Sashimi\n" +
            "4: Fugu    5: Wine    6: Pumpkin Pie\n" +
            "7: Veggie Soup    8: Fish Stew    9: Spakbor Salad\n" +
            "0: Fish Sandwich    `: Legends of Spakbor\n" +
            "Press number keys to cook!";
      }
    }
    return null;
  }

}