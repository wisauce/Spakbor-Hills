package sti.oop.action;

import java.text.NumberFormat.Style;
import java.util.List;

import sti.oop.controllers.FarmController;
import sti.oop.controllers.PanelController;
import sti.oop.interfaces.Actor;
import sti.oop.interfaces.Edible;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Player;
import sti.oop.models.Item.Fish;
import sti.oop.models.assets.BinArea;
import sti.oop.models.assets.CookingArea;
import sti.oop.models.assets.FishingArea;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.StoreArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.utils.Constants;

public class Action implements Actor {
  FarmController farmController;
  PanelController panelController;

  private boolean stoveOpen = false;
  private boolean isFishing = false;

  public Action(FarmController farmController) {
    this.farmController = farmController;
    this.panelController = farmController.getPanelController();
  }

  public boolean isActionDoable(EnergyConsuming acted) {
    return farmController.getPlayerController().getPlayer().getEnergy() - acted.getEnergyRequired() >= farmController
        .getPlayerController().getPlayer().getMIN_ENERGY();
  }

  public void sleepImmediately() {
    new Sleep().sleep(farmController, 3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE);
  }

  public boolean hasEnergyRanOut() {
    return farmController.getPlayerController().getPlayer().getEnergy() == farmController.getPlayerController()
        .getPlayer().getMIN_ENERGY();
  }

  @Override
  public void act(Teleporter acted) {
    farmController.changeMap(acted.getDestination());
    farmController.getPlayerController().getPlayer().setY(acted.getDestinationY());
    farmController.getPlayerController().getPlayer().setX(acted.getDestinationX());
  }

  @Override
  public void act(NPCArea acted) {
    new NPCInteractionHandler().handleInteraction(acted, farmController.getPlayerController().getPlayer(), panelController, farmController);
    farmController.updateHotbar();
  }

  @Override
  public void act(Land acted) {
    String actionResult = null;
    Farming farming = new Farming();
    actionResult = farming.doFarm(farmController.getPlayerController().getPlayer(), acted, farmController);
    if (hasEnergyRanOut()) {
      sleepImmediately();
      actionResult = "you are too tired from yesterday farming";
    }
    panelController.showDialog(actionResult);
  }

  @Override
  public void act(SleepingArea acted) {
    Sleep sleep = new Sleep();
    sleep.sleep(farmController, acted.getSpawnAreaX(), acted.getSpawnAreaY());
    panelController.showDialog("Good morning. Did you sleep well?");
  }

  public void act(FishingArea acted) {
    if (farmController.getPlayerController().getPlayer().getOnHandItem() != null && farmController.getPlayerController().getPlayer().getOnHandItem().getItemName().equals("FishingRod")) {
      if (isFishing) {
        isFishing = false;
        farmController.getTimeController().setTimeFrozen(false);
      } else {
        isFishing = true;
        farmController.getTimeController().setTimeFrozen(true);
        Fishing fishing = new Fishing();
        List<Fish> availableFishes = fishing.availableFishList(acted.getFishes(),farmController.getFarm());
        Fish randomizedFish = fishing.randomizeFish(availableFishes);
        fishing.startInteractiveFishing(farmController.getPlayerController().getPlayer(), randomizedFish, panelController, farmController, this);
      }
    } else {
      panelController.showDialog("You need a Fishing Rod to fish!");
    }
  }

  public void closeFishingInterface() {
    if (isFishing) {
      isFishing = false;
      farmController.getTimeController().setTimeFrozen(false);
    }
  }

  public boolean isFishing() {
    return isFishing;
  }

  public void toggleIsFishing() {
    isFishing = false;
  }

  public String handleEating() {
    Player player = farmController.getPlayerController().getPlayer();

    if (player.getOnHandItem() instanceof Edible) {
      Eating eating = new Eating();
      String actionResult = eating.doEating(player, farmController);
      farmController.updateHotbar();
      return actionResult;
    } else {
      return "You need food in your hand to eat!";
    }
  }

  @Override
  public void act(CookingArea acted) {
  System.out.println("StoveOpen: " + stoveOpen); 
  if (stoveOpen) {
    stoveOpen = false;
    farmController.getTimeController().setTimeFrozen(false);
  } else {
      stoveOpen = true;
      farmController.getTimeController().setTimeFrozen(true);
      try {
        new CookingInteractionHandler().handleInteraction(acted, farmController.getPlayerController().getPlayer(),farmController.getFarm(), panelController, this);
      } catch (Exception e) {
        System.err.println("Error in cooking action: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void closeCookingInterface() {
    if (stoveOpen) {
      stoveOpen = false;
      farmController.getTimeController().setTimeFrozen(false);
    }
  }

  public boolean isStoveOpen() {
    return stoveOpen;
  }

  public void toggleStoveOpen() {
    System.out.println("Test123");
    stoveOpen = false;
  }

  @Override
  public void act(BinArea acted) {
    String actionResult = null;
    Bin bin = new Bin();
    actionResult = bin.doBin(farmController.getPlayerController().getPlayer(), farmController);
    panelController.showDialog(actionResult);
  }

  @Override
  public void act(StoreArea acted) {
    String actionResult = null;
    Store store = new Store();
    actionResult = store.doStore(farmController.getPlayerController().getPlayer(), farmController);
    panelController.showDialog(actionResult);
  }

}
