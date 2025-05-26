package sti.oop.action;
import sti.oop.models.Farm;
// import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Proposing {
    private static final int REQUIRED_HEART_POINTS = 150;
    private static final int ENERGY_COST_SUCCESS = 10;
    private static final int ENERGY_COST_FAIL = 20;

    public boolean hasRequiredItems(Player player){
        return player.getInventory().hasItem("Proposal Ring");
    }
    public void doProposing(Player player, Farm farm, NPC npc){
        if (npc.getHeartPoints() == REQUIRED_HEART_POINTS && hasRequiredItems(player) && npc.getRelationshipStatus().equalsIgnoreCase("Single")){
            int currEnergy = player.getEnergy() - ENERGY_COST_SUCCESS;
            player.setEnergy(currEnergy);
            npc.setRelationshipStatus("Fiance");
            System.out.println("Congratulations! You and " + npc.getName() + " are now engaged!");
        }
        else{
            int currEnergy = player.getEnergy() - ENERGY_COST_FAIL;
            player.setEnergy(currEnergy);
            System.out.println("Dont Be Sad! even though " + npc.getName() + " rejects you, you're still the G.O.A.T of love!");
        }
    }
}