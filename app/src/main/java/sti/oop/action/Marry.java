package sti.oop.action;
import sti.oop.models.Farm;
// import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Marry {
    private static final int ENERGY_COST_MARRY = 80;
      
    public boolean hasRequiredItems(Player player){
        return player.getInventory().hasItem("Proposal Ring");
    }

    public void doMarry(Player player, Farm farm, NPC npc){
        if (hasRequiredItems(player) && npc.getRelationshipStatus().equalsIgnoreCase("Single") && player.getPartner() == null){
            int currEnergy = player.getEnergy() - ENERGY_COST_MARRY;
            player.setEnergy(currEnergy);
            npc.setRelationshipStatus("Married");
            // harusnya partner di player itu bukan string, tapi class NPC ??
            player.setPartner(npc.getName());
            System.out.println("Congratulations! You and " + npc.getName() + " are now partner in life!");
        }
    }
}
