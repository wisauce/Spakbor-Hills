package sti.oop.models;

import java.util.EnumSet;

import javafx.scene.image.Image;
import sti.oop.controllers.HealthBarUpdater;
import sti.oop.models.Item.Equipment;
import sti.oop.models.Item.Fish;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Misc;
import sti.oop.models.Item.Seed;
import sti.oop.models.NPC.NPC;

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

  private final int MAX_ENERGY = 100;
  private final int MIN_ENERGY = -20;
  
  private String name;
  private Gender gender;
  private int energy;
  private String farmName;
  private NPC partner;
  private int gold;
  private CurrentMap currentMap;

  private Inventory inventory;
  private final int walkingSpeed = 6;
  private Item onHandItem;
  private int onHandInventoryIndex = 0;
  private int speed = walkingSpeed;
  private double runBoost = 1.5;
  private final int runningSpeed = (int) (walkingSpeed * runBoost);
  private int x = 0;
  private int y = 0;

  private HealthBarUpdater healthBarUpdater;
  
  
  public static Image playerSpriteSheet = new Image(Player.class.getResource("/sprites/spritePlayer.png").toExternalForm());

  public Player(String name, Gender gender, String farmName) {
    this.name = name;
    this.gender = gender;
    this.farmName = farmName;
    this.gold = 50;
    partner = null;
    inventory = new Inventory();

    currentMap = CurrentMap.FARM;
    energy = MAX_ENERGY;

    onHandItem = null;
    

    giveStarterItems();

    inventory.addItem(new Misc("Coal", 100, 50), 1);
    inventory.addItem(new Misc("Firewood", 150, 75), 1);
    inventory.addItem(new Misc("Gift", 250, 125), 1);
    inventory.addItem(new Misc("WeddingRing", 1500, 50), 1);
    inventory.addItem(new Fish("Angler", EnumSet.of(Fish.Season.SPRING), new int[]{6,18}, EnumSet.of(Fish.Weather.SUNNY), EnumSet.of(Fish.Location.OCEAN), "COMMON"),  1);


    for (int i = 1; i <= 30; i++) {
      inventory.addItem(new Equipment("TestTool" + i), i);
    }
  }

  private void giveStarterItems() {
    inventory.addItem(new Equipment("Hoe"), 1);
    inventory.addItem(new Equipment("Pickaxe"), 1);
    inventory.addItem(new Equipment("WateringCan"), 1);
    inventory.addItem(new Equipment("FishingRod"), 1);
    inventory.addItem(new Seed("ParsnipSeeds", 1, 20, Seed.Season.SPRING), 15);
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
    this.energy = Math.clamp(energy, MIN_ENERGY, MAX_ENERGY);
    healthBarUpdater.updateHealthBar(energy, MAX_ENERGY);
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

  public NPC getPartner() {
    return partner;
  }

  public void setPartner(NPC partner) {
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

  public Item getOnHandItem() {
    return onHandItem;
  }

  public void setOnHandItem(Item onHandItem) {
    this.onHandItem = onHandItem;
  }

  public int getOnHandInventoryIndex() {
    return onHandInventoryIndex;
  }

  public void setOnHandInventoryIndex(int onHandInventoryIndex) {
    this.onHandInventoryIndex = onHandInventoryIndex;
    updateOnHandItem();
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

  public int getMAX_ENERGY() {
    return MAX_ENERGY;
  }

  public void setHealthBarUpdater(HealthBarUpdater healthBarUpdater) {
    this.healthBarUpdater = healthBarUpdater;
  }

  public void putItemInventory(String itemName, int quantity) {
    Item item = ItemRegistry.createItem(itemName);

    inventory.addItem(item, quantity);
  }

  public int getMIN_ENERGY() {
    return MIN_ENERGY;
  }


  
  public void updateOnHandItem() {
    Item[] allItems = inventory.getAllItem().toArray(new Item[0]);
    if (onHandInventoryIndex >= 0 && onHandInventoryIndex < allItems.length) {
      onHandItem = allItems[onHandInventoryIndex];
    }
    else {
      onHandItem = null;
    }
  }

  public boolean hasItemTypeInHand(String itemType) {
    return onHandItem != null && onHandItem.getItemType().equals(itemType);
  }

  public boolean hasItemInHand(String itemName) {
    return onHandItem != null && onHandItem.getItemName().equals(itemName);
  }

  
  
  // public void sellFish(String itemName, int itemQuantity) throws IllegalArgumentException{
  //   if (!inventory.getListInventorys().contains(itemName)) {
  //     throw new IllegalStateException("You don't have " + itemName + " in your inventory!");
  //   }
  //   if (!inventory.getListCount().c)
  // }
}
