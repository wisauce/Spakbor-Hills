package sti.oop.models;

import javafx.scene.paint.*;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sti.oop.App;

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
  // private housemap
  // private worldmap
  // day nanti dulu juga
  // season
  // weather

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

  private boolean is31Days(int worldMonth) {
    return 
    worldMonth == 1 || worldMonth == 3 || 
    worldMonth == 5 || worldMonth == 7 || worldMonth == 8 || 
    worldMonth == 10 || worldMonth == 12; 
  }

  private void nextDay() {
    worldDay++;
    boolean newMonth = false;

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

  public void setTimeFrozen(boolean freeze) {
    timeFrozen = freeze;
  }

  public boolean isTimeFrozen() {
    return timeFrozen;
  }

  public String getFormatTimeNow() {
    return String.format("%d:%02d %s", inGameHour, inGameMinute, timeOfDay);
  }

  public String getFormatDateMonthSeason() {
    return String.format("Day %d, Month %d \n%s", worldDay, worldMonth, getSeason());
  }

  // public FarmMap getFarmMap() {
  //   return farmMap;
  // }

  // public void setFarmMap(FarmMap farmMap) {
  //   this.farmMap = farmMap;
  // }

}