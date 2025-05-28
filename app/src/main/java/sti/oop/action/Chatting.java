package sti.oop.action;

import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Chatting implements EnergyConsuming {
  private int energyRequired = 10;
  private static final int HEART_POINTS_SUPP = 10;

  public void doChatting(Player player, NPC npc) {
    int chatEnergy = player.getEnergy() - energyRequired;
    player.setEnergy(chatEnergy);
    int chatHeartsPoints = npc.getHeartPoints() + HEART_POINTS_SUPP;
    npc.setHeartPoints(chatHeartsPoints);
    System.out.println("You're Now Chatting With " + npc.getName());
  }

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }

  
}
