package sti.oop.models.Item;

import sti.oop.interfaces.Valuable;
import sti.oop.interfaces.Edible;

import java.util.EnumSet;

public class Fish extends Item implements Valuable, Edible {
    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }
    private EnumSet<Season> season;

    public enum Location {
        POND,
        OCEAN,
        FOREST_RIVER,
        MOUNTAIN_LAKE
    }

    private EnumSet<Location> location;

    public enum Weather {
        RAINY,
        SUNNY
    }

    private EnumSet<Weather> weather;

    private int[] availableTime;
    private String grade;
    
    public Fish(String name, EnumSet<Season> season, int[] availableTime, EnumSet<Weather> weather, EnumSet<Location> location, String grade) {
        super(name, "FISH");
        this.season = season;
        this.availableTime = availableTime;
        this.location = location;
        this.weather = weather;
        this.grade = grade;
    }

    @Override
    public void eaten(){}

    @Override
    public int getEnergy() {
        return 1;
    }

    public EnumSet<Season> getSeason() {
        return season;
    }

    public EnumSet<Location> getLocation() {
        return location;
    } 

    public EnumSet<Weather> getWeather() {
        return weather;
    }

    public int[] getAvailableTime() {
        return availableTime;
    }
    
    public String getGrade() {
        return grade;
    }


    /* -------------------------------------------------------------------------- */
    /*                          Interface Implementation                          */
    /* -------------------------------------------------------------------------- */

    @Override
    public int getBuyPrice() {
        return 0;
    }
    

    /* -------------------------------------------------------------------------- */
    /*                              Sell Price Logics                             */
    /* -------------------------------------------------------------------------- */

    @Override 
    public int getSellPrice() {
        int seasonMultiplier = 4 / (season.size() == 0? 1 : season.size());
        int timeMultiplier = 24 / (totalAvailableHours(availableTime) == 0? 1 : totalAvailableHours(availableTime));
        int weatherMultiplier = 2 / (weather.size() == 0? 1 : weather.size());
        int locationMultiplier = 4 / (location.size() == 0? 1: location.size());

        int gradeMultiplier;
        if ((getGrade().toUpperCase()).equals("REGULAR")) {
            gradeMultiplier = 5;
        }
        else if ((getGrade().toUpperCase()).equals("COMMON")) {
            gradeMultiplier = 10;
        }
        else if ((getGrade().toUpperCase()).equals("LEGENDARY")) {
            gradeMultiplier = 25;
        }
        else {
            gradeMultiplier = 1;
        }

        return seasonMultiplier * timeMultiplier * weatherMultiplier * locationMultiplier * gradeMultiplier;

    }

    private int totalAvailableHours(int[] availableTime) {
        if (availableTime == null || availableTime.length < 2) {
            return 0;
        }

        int result = 0;

        for (int i = 0 ; i < availableTime.length;  i += 2) {
            if (i+1 >= availableTime.length) {
                break;
            }

            int startHour = availableTime[i];
            int endHour = availableTime[i+1];

            if (endHour > startHour) {
                result += endHour - startHour;
            } 
            else{
                result += (24 - startHour) + endHour;
            }
        }

        return result;
    }
}