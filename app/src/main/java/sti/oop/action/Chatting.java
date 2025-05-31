package sti.oop.action;

import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.NPC.NPC;

public class Chatting implements EnergyConsuming {
  private int energyRequired = 10;
  private static final int HEART_POINTS_SUPP = 10;

  public String doChatting(Player player, NPC npc, Farm farm) {
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
      int chatEnergy = player.getEnergy() - energyRequired;
      player.setEnergy(chatEnergy);
      int chatHeartsPoints = npc.getHeartPoints() + HEART_POINTS_SUPP;
      npc.setHeartPoints(chatHeartsPoints);

      farm.setTime(newHour, newMinute);

      if (!newTimeOfDay.equals(timeOfDay)) {
        farm.setTimeOfDay(newTimeOfDay);
        if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
          farm.nextDay();
        }
      }

      return npc.getName() + ": " + npc.getConversation();
    } 
    
    else {
      return "your social battery ran out. Sleep might be a good idea";
    }
  }

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }

  
}
