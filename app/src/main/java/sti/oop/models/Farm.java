package sti.oop.models;

import java.util.Random;

import javafx.scene.paint.Color;

public class Farm {
  private String name;
  private Player player;

  /* Time Attributes */
  private int inGameHour = 6;
  private int inGameMinute = 0;
  private String timeOfDay = "AM";
  private boolean timeFrozen = false;
  private final int MINUTES_PER_SECOND = 5; 

  private int worldDay = 1;
  private int worldMonth = 1;

  private enum Season {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER
  }

  private Season season = Season.SPRING;

  private enum Weather {
    SUNNY,
    RAINY
  }
  // private housemap
  // private worldmap
  
  private Weather weather = Weather.SUNNY;

  private Random random = new Random();

  public Farm(Player player) {
    this.player = player;
    name = player.getFarmName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  /* -------------------------------------------------------------------------- */
  /*                                 TIME LOGICS                                */
  /* -------------------------------------------------------------------------- */

  public void updateTime() {
    if (timeFrozen) return;
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

  private void nextDay() {
    worldDay++;
    boolean newMonth = false;

    randomizedDailyWeather();

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
        season = Season.AUTUMN;
        break;

      case 12: case 1: case 2:
        season = Season.WINTER;
        break;
    }
  }

  public String getSeason(){
    switch (season) {
      case SPRING:
        return "Spring";
      
      case SUMMER:
        return "Summer";
      
      case AUTUMN:
        return "Autumn";

      case WINTER:
        return "Winter";

      default:
        return "NULL";
    }
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

  public String getWeather() {
    switch (weather) {
      case SUNNY:
        return "Sunny";
      
      case RAINY:
        return "Rainy";
        
      default:
        return "NULL";
    }
  }

  /* -------------------------------------------------------------------------- */
  /*                         COLORING TIME OF DAY LOGICS                        */
  /* -------------------------------------------------------------------------- */

  public Color timeOfDayColor() {
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
        return Color.rgb(173, 51, 4, 0.2);
    }
    
    else if (hour_24 >= 8 && hour_24 < 17) {
        return Color.rgb(255, 255, 255, 0.0);
    }
    
    else if (hour_24 >= 17 && hour_24 < 19) {
        return Color.rgb(231, 90, 10, 0.3);
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

}