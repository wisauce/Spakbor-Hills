package sti.oop.action;

import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.NPC.NPC;

public class Gifting {
    public void doGifting(Player player, NPC npc){
        //npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems(new Item(null, null)));
        npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems("anjay"));
        player.setEnergy(player.getEnergy()-10);
        System.out.println("berhasil gift");
    }
}
