package sti.oop.models;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import sti.oop.controllers.HealthBarUpdater;
import sti.oop.models.Item.Item;
import sti.oop.models.NPC.NPC;
import sti.oop.utils.Constants;

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
  private double runBoost = 4;
  private final int runningSpeed = (int) (walkingSpeed * runBoost);
  private int x = 10 * Constants.TILE_SIZE;
  private int y = 42 * Constants.TILE_SIZE;

  private HealthBarUpdater healthBarUpdater;

  /* Recipe Unlock Barrier */
  private int amountOfFishReeled = 0;
  private boolean everPufferfish = false;
  private boolean everHarvest = false;
  private boolean everHotPepper = false;
  private boolean everLegendaryFish = false;
  //TODO: Develop with other logics!

  /* Bin Barrier */
  private List<Item> shippingBin = new ArrayList<>();
  private boolean isBinOpen = false;
  
  
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

  }

private void giveStarterItems() { //TODO: CHANGE LATER!
    /* Equipment */
    inventory.addItem(ItemRegistry.createItem("Hoe"), 1);
    inventory.addItem(ItemRegistry.createItem("Pickaxe"), 1);
    inventory.addItem(ItemRegistry.createItem("WateringCan"), 1);
    inventory.addItem(ItemRegistry.createItem("FishingRod"), 1);

    /* Seeds */
    inventory.addItem(ItemRegistry.createItem("ParsnipSeeds"), 15);
    inventory.addItem(ItemRegistry.createItem("CauliflowerSeeds"), 10);
    inventory.addItem(ItemRegistry.createItem("PotatoSeeds"), 12);
    inventory.addItem(ItemRegistry.createItem("WheatSeeds"), 8);
    inventory.addItem(ItemRegistry.createItem("BlueberrySeeds"), 5);
    inventory.addItem(ItemRegistry.createItem("TomatoSeeds"), 8);
    inventory.addItem(ItemRegistry.createItem("HotPepperSeeds"), 3);
    inventory.addItem(ItemRegistry.createItem("MelonSeeds"), 4);
    inventory.addItem(ItemRegistry.createItem("CranberrySeeds"), 5);
    inventory.addItem(ItemRegistry.createItem("PumpkinSeeds"), 3);
    inventory.addItem(ItemRegistry.createItem("GrapeSeeds"), 4);

    /* Fish */
    // Common Fish
    inventory.addItem(ItemRegistry.createItem("Bullhead"), 2);
    inventory.addItem(ItemRegistry.createItem("Carp"), 3);
    inventory.addItem(ItemRegistry.createItem("Chub"), 2);
    
    // Regular Fish
    inventory.addItem(ItemRegistry.createItem("LargemouthBass"), 1);
    inventory.addItem(ItemRegistry.createItem("RainbowTrout"), 1);
    inventory.addItem(ItemRegistry.createItem("Sturgeon"), 1);
    inventory.addItem(ItemRegistry.createItem("MidnightCarp"), 1);
    inventory.addItem(ItemRegistry.createItem("Flounder"), 1);
    inventory.addItem(ItemRegistry.createItem("Halibut"), 1);
    inventory.addItem(ItemRegistry.createItem("Octopus"), 1);
    inventory.addItem(ItemRegistry.createItem("Pufferfish"), 1);
    inventory.addItem(ItemRegistry.createItem("Sardine"), 4);
    inventory.addItem(ItemRegistry.createItem("SuperCucumber"), 1);
    inventory.addItem(ItemRegistry.createItem("Catfish"), 1);
    inventory.addItem(ItemRegistry.createItem("Salmon"), 1);
    
    // Legendary Fish
    inventory.addItem(ItemRegistry.createItem("Angler"), 1);
    inventory.addItem(ItemRegistry.createItem("Crimsonfish"), 1);
    inventory.addItem(ItemRegistry.createItem("Glacierfish"), 1);
    inventory.addItem(ItemRegistry.createItem("Legend"), 1);

    /* Crop */
    inventory.addItem(ItemRegistry.createItem("Parsnip"), 8);
    inventory.addItem(ItemRegistry.createItem("Cauliflower"), 5);
    inventory.addItem(ItemRegistry.createItem("Potato"), 10);
    inventory.addItem(ItemRegistry.createItem("Wheat"), 12);
    inventory.addItem(ItemRegistry.createItem("Blueberry"), 6);
    inventory.addItem(ItemRegistry.createItem("Tomato"), 7);
    inventory.addItem(ItemRegistry.createItem("HotPepper"), 4);
    inventory.addItem(ItemRegistry.createItem("Melon"), 2);
    inventory.addItem(ItemRegistry.createItem("Cranberry"), 8);
    inventory.addItem(ItemRegistry.createItem("Pumpkin"), 3);
    inventory.addItem(ItemRegistry.createItem("Grape"), 10);

    /* Food */
    inventory.addItem(ItemRegistry.createItem("FishnChips"), 2);
    inventory.addItem(ItemRegistry.createItem("Baguette"), 3);
    inventory.addItem(ItemRegistry.createItem("Sashimi"), 2);
    inventory.addItem(ItemRegistry.createItem("Fugu"), 1);
    inventory.addItem(ItemRegistry.createItem("Wine"), 2);
    inventory.addItem(ItemRegistry.createItem("PumpkinPie"), 2);
    inventory.addItem(ItemRegistry.createItem("VeggieSoup"), 2);
    inventory.addItem(ItemRegistry.createItem("FishStew"), 2);
    inventory.addItem(ItemRegistry.createItem("SpakborSalad"), 1);
    inventory.addItem(ItemRegistry.createItem("FishSandwich"), 2);
    inventory.addItem(ItemRegistry.createItem("TheLegendsOfSpakbor"), 1);
    inventory.addItem(ItemRegistry.createItem("CookedPigsHead"), 1);

    /* Misc */
    inventory.addItem(ItemRegistry.createItem("Coal"), 10);
    inventory.addItem(ItemRegistry.createItem("Firewood"), 15);
    inventory.addItem(ItemRegistry.createItem("Gift"), 2);
    inventory.addItem(ItemRegistry.createItem("WeddingRing"), 1);

    /* Recipe */
    inventory.addItem(ItemRegistry.createItem("FishnChipsRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("BaguetteRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("SashimiRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("FuguRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("WineRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("PumpkinPieRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("VeggieSoupRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("FishStewRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("SpakborSaladRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("FishSandwichRecipe"), 1);
    inventory.addItem(ItemRegistry.createItem("TheLegendsOfSpakborRecipe"), 1);
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

    if (onHandItem != null) {
      int currentQuantity = inventory.getItemCount(onHandItem);
      if (currentQuantity <= 0) {
        onHandItem = null;
        onHandInventoryIndex = -1;
        return;
      }
    }
    if (onHandInventoryIndex >= 0 && onHandInventoryIndex < allItems.length) {
        Item itemAtIndex = allItems[onHandInventoryIndex];
        if (inventory.getItemCount(itemAtIndex) > 0) {
            onHandItem = itemAtIndex;
        } 
        else {
            onHandItem = null;
            onHandInventoryIndex = -1;
        }
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

  /* -------------------------------------------------------------------------- */
  /*                            Recipe Blocker Logics                           */
  /* -------------------------------------------------------------------------- */

  public int getAmountOfFishReeled() {
    return amountOfFishReeled;
  }
  public void addAmountOfFishReeled() {
    amountOfFishReeled++;
  }

  public boolean getEverPufferfish() {
    return everPufferfish;
  }
  public void wasEverPufferfish() {
    this.everPufferfish = true;
  }

  public boolean getEverHarvest() {
    return everHarvest;
  }
  public void wasEverHarvest() {
    this.everHarvest = true;
  }

  public boolean getEverHotPepper() {
    return everHotPepper;
  }
  public void wasEverHotPepper() {
    this.everHotPepper = true;
  }

  public boolean getEverLegendaryFish() {
    return everLegendaryFish;
  }
  public void wasEverLegendaryFish() {
    this.everLegendaryFish = true;
  }


  /* -------------------------------------------------------------------------- */
  /*                                 Bin Logics                                 */
  /* -------------------------------------------------------------------------- */

  public boolean isBinOpen() {
    return isBinOpen;
  }

  public void binOpen() {
    isBinOpen = true;
  }

  public void binClose() {
    isBinOpen = false;
  }

  private void addItemToShippingBin(Item item) {
    shippingBin.add(item);
  }
}
