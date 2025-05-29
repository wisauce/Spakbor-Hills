package sti.oop.action;
import sti.oop.interfaces.EnergyConsuming;
// import sti.oop.models.Inventory;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

// ada kurang di bagian jam jam an
public class Proposing implements EnergyConsuming{
    private final int REQUIRED_HEART_POINTS = 150;
    private final int ENERGY_COST_SUCCESS = 10;
    private final int ENERGY_COST_FAIL = 20;
    private int energyRequired = ENERGY_COST_FAIL;

    public boolean hasRequiredItems(Player player){
        return true; //player.getInventory().hasItem(new Item("Proposal Ring", "EQUIPMENT"));
    }
    public String doProposing(Player player, NPC npc){
      if (player.getEnergy() >= energyRequired) {
        if (npc.getHeartPoints() == REQUIRED_HEART_POINTS && hasRequiredItems(player) && npc.getRelationshipStatus().equalsIgnoreCase("Single") && player.getPartner() == null){
            int currEnergy = player.getEnergy() - ENERGY_COST_SUCCESS;
            player.setEnergy(currEnergy);
            npc.setRelationshipStatus("Fiance");
            
            return "Congratulations! You and " + npc.getName() + " are now engaged!";
        }
        else{
            int currEnergy = player.getEnergy() - ENERGY_COST_FAIL;
            player.setEnergy(currEnergy);
            return "Dont Be Sad! even though " + npc.getName() + " rejects you, you're still the G.O.A.T of love!";
        }
      } else {
        return "you can not propose if you're don't even have enough energy";
      }
    }
    @Override
    public int getEnergyRequired() {
      return energyRequired;
    }
}