package sti.oop.action;

import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Gifting implements EnergyConsuming {
  private int energyRequired = 5;

  public String doGifting(Player player, NPC npc) {
    // npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems(new
    // Item(null, null)));
    if (player.getEnergy() >= energyRequired) {
      npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems(player.getOnHandItem()));
      player.setEnergy(player.getEnergy() - 10);
      player.getInventory().removeItem(player.getOnHandItem(), 1);
      player.updateOnHandItem();
      return npc.getName() + " is happy with your gift!";
    } else {
      return "i think sleeping is a better idea";
    }
  }

  @Override
    public int getEnergyRequired() {
      return energyRequired;
    }

}
