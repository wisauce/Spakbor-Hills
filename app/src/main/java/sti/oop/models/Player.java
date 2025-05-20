package sti.oop.models;

import javafx.scene.image.Image;

public class Player {
  public enum Gender {
    MALE,
    FEMALE
  }

  private final int maxEnergy = 100;
  
  private String name;
  private Gender gender;
  private String farmName;
  private String partner;
  private int gold;
  private int energy;
  private OwnedItem inventory;
  private enum CurrentMap{
    FARM,
    HOUSE,
    WORLD
  }

  private int speed;
  private int x;
  private int y;
  private CurrentMap currentMap;
  private boolean run = false;
  

  public Player(String name, Gender gender, String farmName, int gold, int speed) {
    this.name = name;
    this.gender = gender;
    this.farmName = farmName;
    this.gold = gold;
    this.speed = speed;

    partner = null;
    inventory = new OwnedItem();
    inventory.addItem("Hoe", 1);
    inventory.addItem("Watering Can", 1);
    inventory.addItem("Pickaxe", 1);
    inventory.addItem("Fishing Rod", 1);
    inventory.addItem("Parsnip Seeds", 15);
    
    currentMap = CurrentMap.FARM;
    x = 0;
    y = 0;
    energy = maxEnergy;
    // currentsprite = new Image(getClass().getResource("/images/chibisprite.png").toExternalForm());
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  private final Image playerSpriteSheet = new Image(Player.class.getResource("/sprites/spritePlayer.png").toExternalForm());

  public Image getPlayerSpriteSheet() {
    return playerSpriteSheet;
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
    this.energy = Math.clamp(energy, 0, maxEnergy);  
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

  public void moveRight() {
    if (run) {
      x += speed * 1.5;
    }
    else {
      x += speed;
    }
  }

  public void moveLeft() {
    if (run) {
      x -= speed * 1.5;
    }
    else {
      x -= speed;
    }
  }

  public void moveUp() {
    if (run) {
      y -= speed * 1.5;
    }
    else {
      y -= speed;
    }
  }

  public void moveDown() {
    if (run) {
      y += speed * 1.5;
    }
    else {
      y += speed;
    }
  }

  public void move(boolean left, boolean up, boolean down, boolean right) {
    if (left) moveLeft();
    if (up) moveUp();
    if (down) moveDown();
    if (right) moveRight();
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
  
  // public void sellFish(String itemName, int itemQuantity) throws IllegalArgumentException{
  //   if (!inventory.getListOwnedItems().contains(itemName)) {
  //     throw new IllegalStateException("You don't have " + itemName + " in your inventory!");
  //   }
  //   if (!inventory.getListCount().c)
  // }
}
