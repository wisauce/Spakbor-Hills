package sti.oop.models.Item;

import sti.oop.interfaces.Valuable;

public class Seed extends Item implements Valuable{
    private int daysToHarvest;
    private int buyPrice;
    private int sellPrice;
    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }
    private Season season;

    public Seed (String itemName, int daysToHarvest, int buyPrice, Season season) {
        super(itemName, "SEED");
        this.daysToHarvest = daysToHarvest;
        this.buyPrice = buyPrice;
        sellPrice = buyPrice / 2;
        this.season = season;
    }

    public int getDayToHarvest() {
        return daysToHarvest;
    }

    @Override
    public int getBuyPrice() {
        return buyPrice;
    }

    @Override
    public int getSellPrice() {
        return sellPrice;
    }

    public Season getSeason() {
        return season;
    }


}