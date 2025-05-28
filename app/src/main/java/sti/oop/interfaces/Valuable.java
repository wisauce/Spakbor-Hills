package sti.oop.interfaces;

public interface Valuable {

  public int getBuyPrice();
  public int getSellPrice();

  default int getProfit() {
    return getSellPrice() - getBuyPrice();
  }
}