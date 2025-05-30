package sti.oop.models.Item;

import sti.oop.interfaces.Valuable;

public class Recipe extends Item implements Valuable {
    private int buyPrice;
    private int sellPrice;

    public Recipe(String name, int buyPrice, int sellPrice) {
        super(name, "RECIPE");
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
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