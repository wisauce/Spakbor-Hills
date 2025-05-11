package sti.oop.models;

import javafx.scene.image.Image;

public class Player {
  private final int maxEnergy = 100;
  private String name;
  public enum Gender {
    MALE,
    FEMALE
  }
  private Gender gender;
  private int energy;
  private String farmName;
  private String partner;
  private int gold;
  private OwnedItem inventory;
  private Point location;
  private enum CurrentMap{
    FARM,
    HOUSE,
    WORLD
  }
  private CurrentMap currentMap;
  private int speed = 4;
  private boolean run = false;
  
  public static Image playerSpriteSheet = new Image(Player.class.getResource("/sprites/spritePlayer.png").toExternalForm());
  public static final int playerFrameWidth = 256;
  public static final int playerFrameHeight = 256;
  public static int frameX = 0;
  public static int frameY = 0;
  // public static int sourceX = frameX * playerFrameWidth;
  // public static int sourceY = frameY * playerFrameHeight;
  
  // private Image currentsprite;
  public static int sourceX() {
    return frameX * playerFrameWidth;
  }

  public static int sourceY() {
    return frameY * playerFrameHeight;
  }

  public Player(String name, Gender gender, String farmName) {
    this.name = name;
    this.gender = gender;
    this.farmName = farmName;
    this.gold = 0;
    partner = null;
    inventory = new OwnedItem();
    inventory.addItem("Hoe", 1);
    inventory.addItem("Watering Can", 1);
    inventory.addItem("Pickaxe", 1);
    inventory.addItem("Fishing Rod", 1);
    inventory.addItem("Parsnip Seeds", 15);
    currentMap = CurrentMap.FARM;
    location = new Point(0,0);
    energy = maxEnergy;
    // currentsprite = new Image(getClass().getResource("/images/chibisprite.png").toExternalForm());
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getEnergy() {
    return energy;
  }
  public void setEnergy(int energy) {
    this.energy = energy > maxEnergy ? maxEnergy : energy < 0 ? 0 : energy;
  }
  public Gender getGender() {
    return gender;
  }
  public String getFarmName() {
    return farmName;
  }
  public void setFarmName(String farmName) {
    this.farmName = farmName;
  }
  public String getPartner() {
    return partner;
  }
  public void setPartner(String partner) {
    if (getPartner() == null) {
        this.partner = partner;
    }
    else {
      throw new IllegalStateException("Player already has a partner! This game doesn't support polygamy!");
    }
  }
  public int getGold() {
    return gold;
  }
  public void setGold(int gold) {
    this.gold = gold;
  }
  public CurrentMap getCurrentMap() {
    return currentMap;
  }
  public void setCurrentMap(CurrentMap currentMap) {
    this.currentMap = currentMap;
  }
  public Point getLocation() {
    return location;
  }
  public void setLocation(Point location) {
    this.location = location;
  }
  public int getSpeed(){
    return speed;
  }
  public boolean getRun() {
    return run;
  }
  public void setRun(boolean state) {
    run = state;
  }
  public void moveRight() {
    if (run) {
      location.x += speed*1.5;
    }
    else {
      location.x +=speed;
    }
  }
  public void moveLeft() {
    if (run) {
      location.x -= speed*1.5;
    }
    else {
      location.x -=speed;
    }
  }
  public void moveUp() {
    if (run) {
      location.y -= speed*1.5;
    }
    else {
      location.y -=speed;
    }
  }
  public void moveDown() {
    if (run) {
      location.y += speed*1.5;
    }
    else {
      location.y +=speed;
    }
  }
  // public void sellFish(String itemName, int itemQuantity) throws IllegalArgumentException{
  //   if (!inventory.getListOwnedItems().contains(itemName)) {
  //     throw new IllegalStateException("You don't have " + itemName + " in your inventory!");
  //   }
  //   if (!inventory.getListCount().c)
  // }
}
