package sti.oop.models.item;

import sti.oop.interfaces.Valuable;
import sti.oop.interfaces.Edible;

public class Food extends Item implements Valuable, Edible {
    private int energy;
    private int buyPrice;
    private int sellPrice;

    public Food (String name, int energy, int buyPrice, int sellPrice) {
        super(name, "FOOD");
        this.energy = energy;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    @Override
    public void eaten(){}

    @Override
    public int getEnergy() {
        return energy;
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