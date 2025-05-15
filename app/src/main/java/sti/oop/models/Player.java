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
  private int speed;
  private int x;
  private int y;
  private String currentMap;

  public Player(String name, Gender gender, String farmName, int gold, int speed) {
    this.name = name;
    this.gender = gender;
    this.farmName = farmName;
    this.gold = gold;
    this.speed = speed; 
    currentMap = "Farm";
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

  public void move(boolean left, boolean up, boolean down, boolean right) {
    if (left) moveLeft();
    if (up) moveUp();
    if (down) moveDown();
    if (right) moveRight();
  }
}
