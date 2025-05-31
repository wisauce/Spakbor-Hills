package sti.oop.action;

import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Gifting implements EnergyConsuming {
  private int energyRequired = 5;

  public String doGifting(Player player, NPC npc, Farm farm) {
    if (player.getOnHandItem() != null) {
      if (player.getEnergy() >= energyRequired) {
        int currentHour = farm.getInGameHour();
        int currentMinute = farm.getInGameMinute();
        String timeOfDay = farm.getTimeOfDay();
  
        int newMinute = currentMinute + 10;
        int newHour = currentHour;
        String newTimeOfDay = timeOfDay;
  
        if (newMinute >= 60) {
          newMinute -= 60;
          newHour++;
          if (newHour == 12 && timeOfDay.equals("AM")) {
            newTimeOfDay = "PM";
          }
          else if (newHour == 12 && timeOfDay.equals("PM")) {
            newTimeOfDay = "AM";
          }
  
          else if (newHour > 12) {
            newHour = 1;
          }
        }
        npc.setHeartPoints(npc.getHeartPoints() + npc.getHeartPointsforItems(player.getOnHandItem()));
        player.setEnergy(player.getEnergy() - 10);
        player.getInventory().removeItem(player.getOnHandItem(), 1);
        player.updateOnHandItem();
        
        farm.setTime(newHour, currentMinute);
  
        if (!newTimeOfDay.equals(timeOfDay)) {
          farm.setTimeOfDay(newTimeOfDay);
          if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
            farm.nextDay();
          }
        }
        return npc.getName() + " is happy with your gift!";
      } 
      
      else {
        return "i think sleeping is a better idea";
      }
    } else {
      return "you should hold the gift you want to give";
    }
  }

  @Override
    public int getEnergyRequired() {
      return energyRequired;
    }

}
