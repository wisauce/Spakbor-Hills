package sti.oop.models;

public class Farm {
  private String name;
  private Player player;
  private FarmMap farmMap;
  // private housemap
  // private worldmap
  // time kayanya nanti dulu
  // day nanti dulu juga
  // season
  // weather

  public Farm(Player player) {
    this.player = player;
    name = player.getFarmName();
    farmMap = new FarmMap();
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

  public FarmMap getFarmMap() {
    return farmMap;
  }

  public void setFarmMap(FarmMap farmMap) {
    this.farmMap = farmMap;
  }

}