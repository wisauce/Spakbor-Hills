package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.models.Farm;
import sti.oop.models.Player;

public class Watching {
    private final int ENERGY_COST_WATCH = 5;
    private final int TIME_COST_WATCH = 15;

    public String doWatching(Player player, FarmController farmController){
        player.setEnergy(player.getEnergy()-ENERGY_COST_WATCH);
        updateTimeWatching(farmController.getFarm());
        return "You Are Watching TV!";
    }

    private void updateTimeWatching(Farm farm) {
      int currentHour = farm.getInGameHour();
      int currentMinute = farm.getInGameMinute();
      String timeOfDay = farm.getTimeOfDay();

      int newMinute = currentMinute + 15;
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

      farm.setTime(newHour, newMinute);

      if (!newTimeOfDay.equals(timeOfDay)) {
        farm.setTimeOfDay(newTimeOfDay);
        if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
          farm.nextDay();
        }
      }
  }
}
