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
  private String currentMap;
  private int x = Constants.TILE_SIZE * 10;
  private int y = Constants.TILE_SIZE * 10;
  private int energy;
  private int speed = 20;
  private final int screenX = Constants.SCREEN_WIDTH / 2 - Constants.TILE_SIZE/2;
  private final int screenY = Constants.SCREEN_HEIGHT / 2 - Constants.TILE_SIZE/2;
  
  private final Image playerSpriteSheet = new Image(Player.class.getResource("/sprites/spritePlayer.png").toExternalForm());
  public Image getPlayerSpriteSheet() {
    return playerSpriteSheet;
  }
  private final int playerFrameWidth = 256;
  private final int playerFrameHeight = 256;
  private int frameX = 0;
  private int frameY = 0;
  
  // private Image currentsprite;
  public int sourceX() {
    return frameX * playerFrameWidth;
  }

  public int getPlayerFrameWidth() {
    return playerFrameWidth;
  }

  public int getPlayerFrameHeight() {
    return playerFrameHeight;
  }

  public int getScreenX() {
    return screenX;
  }
  
  public int getScreenY() {
    return screenY;
  }

  public int getFrameX() {
    return frameX;
  }

  public void setFrameX(int frameX) {
    this.frameX = frameX;
  }

  public int getFrameY() {
    return frameY;
  }

  public void setFrameY(int frameY) {
    this.frameY = frameY;
  }

  public int sourceY() {
    return frameY * playerFrameHeight;
  }

  public Player(String name, Gender gender, String farmName) {
    this.name = name;
    this.gender = gender;
    this.farmName = farmName;
    this.gold = 0;
    currentMap = "Farm";
    x = 0;
    y = 0;
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
}
