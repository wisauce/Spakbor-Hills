package sti.oop.action;

import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Gifting implements EnergyConsuming {
  private int energyRequired = 5;

  public String doGifting(Player player, NPC npc) {
    // npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems(new
    // Item(null, null)));
    npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems(null));
    player.setEnergy(player.getEnergy() - 10);
    return npc.getName() + " is happy with your gift!";
  }

  @Override
    public int getEnergyRequired() {
      return energyRequired;
    }

}
