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
  private String farmName;
  private String partner;
  private int gold;
  private String currentMap;
  private Point location;
  private int energy;
  private int speed = 4;
  
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
    currentMap = "Farm";
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
    this.partner = partner;
  }
  public int getGold() {
    return gold;
  }
  public void setGold(int gold) {
    this.gold = gold;
  }
  public String getCurrentMap() {
    return currentMap;
  }
  public void setCurrentMap(String currentMap) {
    this.currentMap = currentMap;
  }
  public Point getLocation() {
    return location;
  }
  public void setLocation(Point location) {
    this.location = location;
  }
  public void moveRight() {
    location.x+=speed;
  }
  public void moveLeft() {
    location.x-=speed;
  }
  public void moveUp() {
    location.y-=speed;
  }
  public void moveDown() {
    location.y+=speed;
  }
}
