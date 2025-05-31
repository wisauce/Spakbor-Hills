package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.interfaces.EnergyConsuming;
// import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;
import sti.oop.utils.Constants;

public class Marry implements EnergyConsuming {
  private int energyRequired = 80;

  public String doMarry(Player player, NPC npc, FarmController farmController) {
    if (isEnergySufficient(player.getEnergy(), energyRequired)) {
      if (!player.getInventory().hasItemByName("WeddingRing")) {
        return "Where is your wedding ring?";
      } else if (!npc.getRelationshipStatus().equalsIgnoreCase("Fiance")) {
        return "You have to be engaged first dude";
      } else if (player.getPartner() != null) {
        return "You are alread married to " + player.getPartner() + ", this game does not support poligamy";
      } else {
        int currEnergy = player.getEnergy() - energyRequired;
        player.setEnergy(currEnergy);
        npc.setRelationshipStatus("Married");
        player.setPartner(npc);
        farmController.changeMap(MapName.HOUSE);
        farmController.getPlayerController().getPlayer().setX(3 * Constants.TILE_SIZE);
        farmController.getPlayerController().getPlayer().setY(4 * Constants.TILE_SIZE);
        farmController.getTimeController().getFarm().setTime(10, 0);
        farmController.getTimeController().getFarm().setTimeOfDay("PM");
        return "Congratulations! You and " + npc.getName() + " are now partner in life!";
      }
    } else {
      return "your energy is not enough to marry";
    }
  }
  
  public boolean isEnergySufficient(int playerEnergy, int requiredEnergy) {
    return playerEnergy >= requiredEnergy;
  }

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }
}