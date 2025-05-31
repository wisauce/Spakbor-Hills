package sti.oop.action;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Farm;
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
      return player.hasItemInHand("WeddingRing");
    }
    public String doProposing(Player player, NPC npc, Farm farm){
      if (player.getEnergy() >= energyRequired) {
        int currentHour = farm.getInGameHour();
        int currentMinute = farm.getInGameMinute();
        String timeOfDay = farm.getTimeOfDay();

        int newHour = currentHour + 1;
        String newTimeOfDay = timeOfDay;

        if (newHour == 12 && timeOfDay.equals("AM")) {
            newTimeOfDay = "PM";
        } 
        
        else if (newHour == 12 && timeOfDay.equals("PM")) {
            newTimeOfDay = "AM";
        } 
        
        else if (newHour > 12) {
            newHour = 1;
        }

        farm.setTime(newHour, currentMinute);

        if (!newTimeOfDay.equals(timeOfDay)) {
          farm.setTimeOfDay(newTimeOfDay);
          if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
            farm.nextDay();
          }
        }

        if (npc.getHeartPoints() == REQUIRED_HEART_POINTS && hasRequiredItems(player) && npc.getRelationshipStatus().equalsIgnoreCase("Single") && player.getPartner() == null){
            int currEnergy = player.getEnergy() - ENERGY_COST_SUCCESS;
            player.setEnergy(currEnergy);
            npc.setRelationshipStatus("Fiance");
            
            return "Congratulations! You and " + npc.getName() + " are now engaged!";
        } else {
            int currEnergy = player.getEnergy() - ENERGY_COST_FAIL;
            player.setEnergy(currEnergy);
            return "Dont Be Sad! even though " + npc.getName() + " rejects you, you're still the G.O.A.T of love!";
        }
      } 
      else {
        return "you can not propose if you're don't even have enough energy";
      }
    }
    @Override
    public int getEnergyRequired() {
      return energyRequired;
    }
}