package sti.oop.models.Item;

import sti.oop.interfaces.Valuable;
import sti.oop.interfaces.Edible;

public class Crop extends Item implements Valuable, Edible {
    private int buyPrice;
    private int sellPrice;
    private int amountHarvest;



    public Crop(String name, int buyPrice, int sellPrice, int amountHarvest){
        super(name, "CROP");
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.amountHarvest = amountHarvest;
    }

    public int amountHarvest() {
        return amountHarvest;
    }

    @Override
    public void eaten(){}

    @Override
    public int getEnergy() {
        return 5;
    }

    @Override
    public int getBuyPrice() {
        return buyPrice;
    }
    
    @Override
    public int getSellPrice() {
        return sellPrice;
    }
}