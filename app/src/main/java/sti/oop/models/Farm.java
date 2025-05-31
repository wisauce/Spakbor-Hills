package sti.oop.models;

import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import sti.oop.controllers.PlayerController;
import sti.oop.models.assets.Asset;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;

public class Farm {
  private String name;
  private PlayerController playerController;

  /* Time Attributes */
  private int inGameHour = 6;
  private int inGameMinute = 0;
  private String timeOfDay = "AM";
  private boolean timeFrozen = false;
  private final int MINUTES_PER_SECOND = 5; 

  private List<Asset> lands;

  private int worldDay = 1;
  private int worldMonth = 1;

  // private enum Season {
  //   SPRING,
  //   SUMMER,
  //   AUTUMN,
  //   WINTER
  // }

  private Season season = Season.SPRING;

  // public enum Weather {
  //   SUNNY,
  //   RAINY
  // }
  // // private housemap
  // // private worldmap
  
  private Weather weather = Weather.SUNNY;

  private Random random = new Random();

  public Farm(PlayerController playerController, List<Asset> lands) {
    this.playerController = playerController;
    name = playerController.getPlayer().getFarmName();
    this.lands = lands;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Player getPlayer() {
    return playerController.getPlayer();
  }

  /* -------------------------------------------------------------------------- */
  /*                                 TIME LOGICS                                */
  /* -------------------------------------------------------------------------- */


  public void updateTime() {
    if (timeFrozen) return;
    if (inGameHour == 2 && timeOfDay.equals("AM")) playerController.getAction().sleepImmediately();
    inGameMinute += MINUTES_PER_SECOND;
    if (inGameMinute >= 60) {
      inGameHour += inGameMinute / 60;
      inGameMinute %= 60;

      if (inGameHour == 12) {
        if (timeOfDay.equals("AM")) {
          timeOfDay = "PM";
        } else {
          timeOfDay = "AM";

          nextDay();
        }
      } else if (inGameHour > 12) {
        inGameHour %= 12;
        if (inGameHour == 0) {
          inGameHour = 12;
        }
      }
    }
  }

  /* -------------------------------------------------------------------------- */
  /*                                 DAY LOGICS                                 */
  /* -------------------------------------------------------------------------- */

  private boolean is31Days(int worldMonth) {
    return 
    worldMonth == 1 || worldMonth == 3 || 
    worldMonth == 5 || worldMonth == 7 || worldMonth == 8 || 
    worldMonth == 10 || worldMonth == 12; 
  }

  public void nextDay() {
    worldDay++;
    boolean newMonth = false;

    randomizedDailyWeather();

    Player player = getPlayer();
    int shippingGold = player.processPendingShipments();

    if (shippingGold > 0) {
      System.out.println("You received " + shippingGold + "g from yesterday's shipments!");
    }

    for (Asset asset : lands) {
      Land land = (Land) asset;
      if (land.getState() == LandState.PLANTED_LAND) {
        land.updateStateOfPlantedLand(weather);
      }
    }

    if (worldMonth == 2) {
        if (worldDay > 28) {
          worldDay = 1;
          worldMonth++;

          newMonth = true;
        }

    }

    else if (is31Days(worldMonth)) {
      if (worldDay > 31) {
        if (worldMonth == 12) {
          worldDay = 1;
          worldMonth = 1;

          newMonth = true;
        } 

        else {
          worldDay = 1;
          worldMonth++;

          newMonth = true;
        }
      }
    }

    else {
      if (worldDay > 30) {
        worldDay = 1;
        worldMonth++;

        newMonth = true;
      }
    }

    if (newMonth) {
      nextSeason();
    }
  }
  
  public int getWorldDay() {
    return worldDay;
  }

  public int getWorldMonth() {
    return worldMonth;
  }

  public int getInGameHour() {
    return inGameHour;
  }
  
  public int getInGameMinute() {
    return inGameMinute;
  }

  public String getTimeOfDay() {
    return timeOfDay;
  }

  /* -------------------------------------------------------------------------- */
  /*                               SEASONS LOGICS                               */
  /* -------------------------------------------------------------------------- */

  private void nextSeason() {
    switch (worldMonth) {
      case 3: case 4: case 5:
        season = Season.SPRING;
        break;
      
      case 6 : case 7 : case 8:
        season = Season.SUMMER;
        break;
      
      case 9 : case 10: case 11:
        season = Season.FALL;
        break;

      case 12: case 1: case 2:
        season = Season.WINTER;
        break;
    }
  }

  public Season getSeason(){
    return season;
  }

  /* -------------------------------------------------------------------------- */
  /*                               WEATHER LOGICS                               */
  /* -------------------------------------------------------------------------- */

  private void randomizedDailyWeather() {
    double rainChance = 0.183;

    if (random.nextDouble() < rainChance) {
      weather = Weather.RAINY;
    }
    else {
      weather = Weather.SUNNY;
    }
  }

  public Weather getWeather() {
    return weather;
  }

  /* -------------------------------------------------------------------------- */
  /*                         COLORING TIME OF DAY LOGICS                        */
  /* -------------------------------------------------------------------------- */

  public Color timeOfDayColor() {
    int hour = inGameHour;
    String period = timeOfDay;

    double hour_24 = hour;
    if (period.equals("AM") && hour == 12) {
        hour_24 = 0;
    }
    else if (period.equals("PM") && hour != 12) {
        hour_24 += 12;
    } 

    hour_24 += inGameMinute / 60.0;

    if (hour_24 >= 6 && hour_24 < 8) {
        return colorTransitionator(Color.rgb(72, 52, 117, 0.6), Color.rgb(173, 51, 4, 0.2), (hour_24 - 5.0) / 3.0);
    }
    
    else if (hour_24 >= 8 && hour_24 < 17) {
        return colorTransitionator(Color.rgb(173, 51, 4, 0.2),Color.rgb(255, 255, 255, 0.0), (hour_24 - 8.0) / 9.0);
    }
    
    else if (hour_24 >= 17 && hour_24 < 19) {
        return colorTransitionator(Color.rgb(255, 255, 255, 0.0), Color.rgb(231, 90, 10, 0.3), (hour_24 - 17.0) / 2.0);
    }
    else if (hour_24 >= 19 && hour_24 < 22) {
        return colorTransitionator(Color.rgb(231, 90, 10, 0.3), Color.rgb(72, 52, 117, 0.6), (hour_24 - 19.0) / 3.0);
    }
    else {
      return Color.rgb(72, 52, 117, 0.6);
    }
  }

  public Color weatherColor() {
    if (weather == Weather.RAINY) {
        return Color.rgb(81, 95, 122, 0.44);
    }
    return Color.TRANSPARENT;
  }

  public String getNameOfTime() {
    int hour = inGameHour;
    String period = timeOfDay;

    int hour_24 = hour;
    if (period.equals("AM") && hour == 12) {
        hour_24 = 0;
    }
    else if (period.equals("PM") && hour != 12) {
        hour_24 += 12;
    } 

    if (hour_24 >= 6 && hour_24 < 8) {
        return "Morning";
    }
    
    else if (hour_24 >= 8 && hour_24 < 17) {
        return "Day";
    }
    
    else if (hour_24 >= 17 && hour_24 < 19) {
        return "Evening";
    }
    else {
        return "Night";
    }
  }

  private double smoothProgression(double progression) {
    /* https://stackoverflow.com/questions/13462001/ease-in-and-ease-out-animation-formula */

    return progression * progression * (3.0 - 2.0 * progression);
  }

  private Color colorTransitionator(Color color1, Color color2, double progression) {
    progression = smoothProgression(Math.max(0, Math.min(1, progression))); // 0 -> 1  

    double red1 = color1.getRed();
    double green1 = color1.getGreen();
    double blue1 = color1.getBlue();
    double alpha1 = color1.getOpacity();

    double red2 = color2.getRed();
    double green2 = color2.getGreen();
    double blue2 = color2.getBlue();
    double alpha2 = color2.getOpacity();

    /* https://stackoverflow.com/questions/19841477/java-smooth-color-transition */
    /* Use Formula --> Old + (Delta * i) / steps  --> i / steps = progression (0 -> 1)*/

    double red = red1 + (red2 - red1) * progression;
    double green = green1 + (green2 - green1) * progression;
    double blue = blue1 + (blue2 - blue1) * progression;
    double alpha = alpha1 + (alpha2 - alpha1) * progression;

    return Color.color(red, green, blue, alpha);
  }

  public void setTimeFrozen(boolean freeze) {
    timeFrozen = freeze;
  }

  public boolean isTimeFrozen() {
    return timeFrozen;
  }

  /* -------------------------------------------------------------------------- */
  /*                               STRING FORMATS                               */
  /* -------------------------------------------------------------------------- */
  
  public String getFormatTimeNow() {
    return String.format("%d:%02d %s", inGameHour, inGameMinute, timeOfDay);
  }

  public String getFormatDateMonthSeason() {
    return String.format("Day %d, Month %d \n%s, %s", worldDay, worldMonth, getSeason(), getWeather());
  }

  // public FarmMap getFarmMap() {
  //   return farmMap;
  // }

  // public void setFarmMap(FarmMap farmMap) {
  //   this.farmMap = farmMap;
  // }
  public void setTime(int hour, int minute) {
    this.inGameHour = hour;
    this.inGameMinute = minute;
  }

  public void setTimeOfDay(String timeOfDay) {
    this.timeOfDay = timeOfDay;
  }

}