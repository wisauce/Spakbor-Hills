package sti.oop.action;

import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Chatting {
    private static final int ENERGY_COST_CHAT = 10;
    private static final int HEART_POINTS_SUPP = 10;

    public void doChatting(Player player, NPC npc){
        int chatEnergy = player.getEnergy() - ENERGY_COST_CHAT;
        player.setEnergy(chatEnergy);
        int chatHeartsPoints = npc.getHeartPoints() + HEART_POINTS_SUPP;
        npc.setHeartPoints(chatHeartsPoints);
        System.out.println("You're Now Chatting With " +npc.getName());
    }
}
