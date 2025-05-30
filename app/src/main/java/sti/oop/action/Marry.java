package sti.oop.action;

import sti.oop.interfaces.EnergyConsuming;
// import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.NPC.NPC;

public class Marry implements EnergyConsuming {
  private int energyRequired = 80;

  public String doMarry(Player player, NPC npc) {
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