package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.interfaces.Actor;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Seed;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.utils.Constants;

public class Action implements Actor {
  FarmController farmController;

  public Action(FarmController farmController) {
    this.farmController = farmController;
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
  public String act(Teleporter acted) {
    farmController.changeMap(acted.getDestination());
    farmController.getPlayerController().getPlayer().setY(acted.getDestinationY());
    farmController.getPlayerController().getPlayer().setX(acted.getDestinationX());
    return null;
  }

  @Override
  public String act(NPCArea acted) {
    return new NPCInteractionHandler(farmController.getPlayerController()).handleInteraction(acted);
  }

  @Override
  public String act(Land acted) {
    String actionResult = null;
    Farming farming = new Farming();
    actionResult = farming.doFarm(farmController.getPlayerController().getPlayer(), acted);
    if (hasEnergyRanOut()) {
      sleepImmediately();
      actionResult = "you are too tired from yesterday farming";
    } 
    return actionResult;
  }

  @Override
  public String act(SleepingArea acted) {
    Sleep sleep = new Sleep();
    sleep.sleep(farmController, acted.getSpawnAreaX(), acted.getSpawnAreaY());
    return "Good morning";
  }

}