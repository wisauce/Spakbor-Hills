package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.controllers.PanelController;
import sti.oop.interfaces.Actor;
import sti.oop.interfaces.Edible;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Player;
import sti.oop.models.Farm;
import sti.oop.models.Item.Item;
import sti.oop.models.assets.FishingArea;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.utils.Constants;

public class Action implements Actor {
  FarmController farmController;
  PanelController panelController;

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
    return farmController.getPlayerController().getPlayer().getEnergy() == farmController.getPlayerController().getPlayer().getMIN_ENERGY();
  }

  @Override
  public void act(Teleporter acted) {
    farmController.changeMap(acted.getDestination());
    farmController.getPlayerController().getPlayer().setY(acted.getDestinationY());
    farmController.getPlayerController().getPlayer().setX(acted.getDestinationX());
  }

  @Override
  public void act(NPCArea acted) {
    panelController.showDialog(new NPCInteractionHandler(farmController.getPlayerController()).handleInteraction(acted));
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
    panelController.showFishing(inputValue -> {
      panelController.showDialog(inputValue.toString());
    });
  }

  @Override
  public void act(Edible acted) {
    Player player = farmController.getPlayerController().getPlayer();
    player.setEnergy(player.getEnergy()+acted.getEnergy());
    player.getInventory().removeItem((Item)acted, 1);
  }

}