package sti.oop.models;

import sti.oop.action.Chatting;
import sti.oop.action.Marry;
import sti.oop.action.Proposing;
import sti.oop.action.Sleep;
import sti.oop.controllers.FarmController;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Item.Seed;
import sti.oop.models.Item.Seed.Season;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;
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

  public void autoSleep(int energyLeft) {
    if (energyLeft == -20) {
      new Sleep().sleep(farmController,3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE);
    }
  }

  @Override
  public void act(Teleporter acted) {
    if (isActionDoable(acted)) {
      farmController.changeMap(acted.getDestination());

    } else {

    }
  }

  @Override
  public void act(NPCArea acted) {
    Marry marry = new Marry();
    Proposing proposing = new Proposing();
    Chatting chatting = new Chatting();

    if (acted.getChoosen_act() == null)
      return;
    if (acted.getChoosen_act().equals("Marry")) {
      if (isActionDoable(marry)) {
        marry.doMarry(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }
    }
    if (acted.getChoosen_act().equals("Propose")) {
      if (isActionDoable(proposing)) {
        proposing.doProposing(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }
    }
    if (acted.getChoosen_act().equals("Chat")) {
      if (isActionDoable(chatting)) {
        chatting.doChatting(farmController.getPlayerController().getPlayer(), acted.getNpc());
        autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
      } else {

      }
    }
  }

  @Override
  public void act(Land acted) {
    if (isActionDoable(acted)) {
      if (acted.getState().equals(LandState.TILLABLE_LAND)) { // &&
                                                              // farmController.getPlayerController().getItemOnHand().equals(acted.getRequiredblablalbla))
                                                              // { // tunggu di implement ardi
        farmController.getPlayerController().getPlayer()
            .setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - 5);
        acted.changeLandState(LandState.TILLED_LAND);
        // belum implement time
      } else if (acted.getState().equals(LandState.TILLED_LAND)) {
        if (true) { // nanti true ganti sama getitemOnHand, IF yang dipegang seed
          farmController.getPlayerController().getPlayer()
              .setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - 5);
          acted.changeLandState(LandState.PLANTED_LAND);
          acted.setSeed(new Seed("Testing", 1, 2, Season.AUTUMN)); // nanti ganti jadi seed on hand
        }
        // if (item di tangan adalah pickaxe) {
        // balik ke state TILLED LAND
        // }
      } else if (acted.getState().equals(LandState.PLANTED_LAND)) { // && PUNYA WATERING CAN
        farmController.getPlayerController().getPlayer()
            .setEnergy(farmController.getPlayerController().getPlayer().getEnergy() - 5);
        acted.setDaysNotWatered(0);
        acted.changeLandState(LandState.HARVESTABLE_LAND); // NANTI DIKOMEN, CUMA BUAT TESTING
      } else if (acted.getState().equals(LandState.HARVESTABLE_LAND)) {
        farmController.getPlayerController().getPlayer().getInventory().addItem(acted.getCrop(), 1);
        acted.setCrop(null);
        acted.changeLandState(LandState.TILLED_LAND);
      }
      autoSleep(farmController.getPlayerController().getPlayer().getEnergy());
    } else {

    }

  }

  @Override
  public void act(SleepingArea acted) {
    Sleep sleep = new Sleep();
    sleep.sleep(farmController,
        acted.getSpawnAreaX(), acted.getSpawnAreaY());
  }
}