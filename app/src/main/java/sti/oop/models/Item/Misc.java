package sti.oop.models.Item;

import sti.oop.interfaces.Valuable;

public class Misc extends Item implements Valuable {
    private int buyPrice;
    private int sellPrice;

    public Misc(String name, int buyPrice, int sellPrice) {
        super(name, "MISC");
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