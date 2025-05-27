package sti.oop.action;
// import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.Item.Item;
import sti.oop.models.NPC.NPC;

public class Marry {
    private static final int ENERGY_COST_MARRY = 80;
      
    public boolean hasRequiredItems(Player player){
        return true;//player.getInventory().hasItem(new Item("Proposal Ring", "EQUIMENT"));
    }

    public void doMarry(Player player, NPC npc){
        if (hasRequiredItems(player) && npc.getRelationshipStatus().equalsIgnoreCase("Fiance") && player.getPartner() == null){
            int currEnergy = player.getEnergy() - ENERGY_COST_MARRY;
            player.setEnergy(currEnergy);
            npc.setRelationshipStatus("Married");
            // harusnya partner di player itu bukan string, tapi class NPC ??
            player.setPartner(npc.getName());
            System.out.println("Congratulations! You and " + npc.getName() + " are now partner in life!");
        }
        else {
            if(!npc.getRelationshipStatus().equalsIgnoreCase("Fiance")){
                System.out.println("Tunangan dulu anjeng");
            }
        };
    }
}