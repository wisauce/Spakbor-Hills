package sti.oop.models;

import javafx.scene.image.Image;
import sti.oop.models.Item.*;

public class Player {
  public enum Gender {
    MALE,
    FEMALE
  }
  
  private enum CurrentMap{
    FARM,
    HOUSE,
    WORLD
  }

  private final int maxEnergy = 100;
  
  private String name;
  private Gender gender;
  private int energy;
  private String farmName;
  private String partner;
  private int gold;
  private CurrentMap currentMap;
  private Inventory inventory;
  private final int walkingSpeed = 4;
  private int speed = walkingSpeed;
  private double runBoost = 1.5;
  private final int runningSpeed = (int) (walkingSpeed * runBoost);
  private int x = 0;
  private int y = 0;
  
  public static Image playerSpriteSheet = new Image(Player.class.getResource("/sprites/spritePlayer.png").toExternalForm());

  public Player(String name, Gender gender, String farmName) {
    this.name = name;
    this.gender = gender;
    this.farmName = farmName;
    this.gold = 50;
    partner = null;
    inventory = new Inventory();
    
    inventory.addItem(new Equipment("Hoe"), 1);
    inventory.addItem(new Equipment("Pickaxe"), 1);

    for (int i = 1; i <= 30; i++) {
      inventory.addItem(new Equipment("TestTool" + i), i);
    }


    currentMap = CurrentMap.FARM;
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
    if (this.partner != null) throw new IllegalStateException("Player already has a partner! This game doesn't support polygamy!");
    this.partner = partner;
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

  public Inventory getInventory() {
    return inventory;
  }

  public int getSpeed(){
    return speed;
  }

  public void setRun(boolean state) {
    if (state) {
      speed = runningSpeed;
    } else {
      speed = walkingSpeed;
    }
  }

  public void moveRight() {
    x += speed;
  }

  public void moveLeft() {
    x -= speed;
  }

  public void moveUp() {
    y -= speed;
  }

  public void moveDown() {
    y += speed;
  }
  
  // public void sellFish(String itemName, int itemQuantity) throws IllegalArgumentException{
  //   if (!inventory.getListInventorys().contains(itemName)) {
  //     throw new IllegalStateException("You don't have " + itemName + " in your inventory!");
  //   }
  //   if (!inventory.getListCount().c)
  // }
}
