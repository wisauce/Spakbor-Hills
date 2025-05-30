package sti.oop.models.NPC;

import javafx.scene.image.Image;
import sti.oop.models.Player;
import sti.oop.models.Point;
import sti.oop.models.Item.*;

import java.util.List;

public class NPC{
  private final int MAX_HEART_POINTS = 150;
  private String name;
  private Point location;
  private int heartPoints;
  private List<Item> lovedItems;
  private List<Item> likedItems;
  private List<Item> hatedItems;
  private String relationshipStatus;

  // attributes for rendering
  public Image NPCSpriteSheet;

  private String conversation;

  public static final int NPCFrameWidth = 256;
  public static final int NPCFrameHeight = 256;
  public static int frameX = 0;
  public static int frameY = 0;
  // public static int sourceX = frameX * playerFrameWidth;
  // public static int sourceY = frameY * playerFrameHeight;

  // private Image currentsprite;
  public static int sourceX() {
    return frameX * NPCFrameWidth;
  }

  public static int sourceY() {
    return frameY * NPCFrameHeight;
  }

  // attribute for NPC hitbox
  // private Rectangle2D hitbox;
  // private int hitboxWidth;
  // private int hitboxHeight;

  public NPC(String name, List<Item> lovedItems, List<Item> likedItems, List<Item> hatedItems, String conversation) {
    this.name = name;
    heartPoints = 0; 
    this.lovedItems = lovedItems;
    this.likedItems = likedItems;
    this.hatedItems = hatedItems;
    relationshipStatus = "Single";
    loadSprite();
    this.conversation = conversation;

  }

  private void loadSprite() {
    if (getName().equals("MayorTadi")) {
      try {
        NPCSpriteSheet = new Image(Player.class.getResource("/sprites/mayorTadi.gif").toExternalForm());
      } catch (NullPointerException e) {
        System.err.println("[Error] can't load sprite for Mayor Tadi: " + e.getMessage());
        NPCSpriteSheet = null;
      }
    } else if (getName().equals("Caroline")) {
      try {
        NPCSpriteSheet = new Image(Player.class.getResource("/sprites/carolineCarpenter.gif").toExternalForm());
      } catch (NullPointerException e) {
        System.err.println("[Error] can't load sprite for Caroline: " + e.getMessage());
        NPCSpriteSheet = null;
      }
    } else if (getName().equals("Perry")) {
      try {
        NPCSpriteSheet = new Image(Player.class.getResource("/sprites/perryAuthor.gif").toExternalForm());
      } catch (NullPointerException e) {
        System.err.println("[Error] can't load sprite for Perry: " + e.getMessage());
        NPCSpriteSheet = null;
      }
    } else if (getName().equals("Dasco")) {
      try {
        NPCSpriteSheet = new Image(Player.class.getResource("/sprites/dascoDealer.gif").toExternalForm());
      } catch (NullPointerException e) {
        System.err.println("[Error] can't load sprite for Dasco: " + e.getMessage());
        NPCSpriteSheet = null;
      }
    } else if (getName().equals("Emily")) {
      try {
        NPCSpriteSheet = new Image(Player.class.getResource("/sprites/emilyCooker.gif").toExternalForm());
      } catch (NullPointerException e) {
        System.err.println("[Error] can't load sprite for Emily: " + e.getMessage());
        NPCSpriteSheet = null;
      }
    } else if (getName().equals("Abigail")) {
      try {
        NPCSpriteSheet = new Image(Player.class.getResource("/sprites/abigailExplorer.gif").toExternalForm());
      } catch (NullPointerException e) {
        System.err.println("[Error] can't load sprite for Abigail: " + e.getMessage());
        NPCSpriteSheet = null;
      }
    } else {
      NPCSpriteSheet = null;
      System.out.println("[Error] no NPC is named " + getName());
    }
  }

  public String getName() {
    return name;
  }

  public Point getLocation() {
    return location;
  }

  public int getHeartPoints() {
    return heartPoints;
  }

  public void setHeartPoints(int heartPoints) {
    if (heartPoints > MAX_HEART_POINTS) {
      this.heartPoints = MAX_HEART_POINTS;
    } else if (heartPoints < 0) {
      this.heartPoints = 0;
    } else {
      this.heartPoints = heartPoints;
    }
  }

  public void increaseHeartPoints(int heartPointsValue) {
    setHeartPoints(getHeartPoints() + heartPointsValue);
  }

  public void decreaseHeartPoints(int heartPointsValue) {
    setHeartPoints(getHeartPoints() - heartPointsValue);
  }

  public List<Item> getLovedItems() {
    return lovedItems;
  }

  public List<Item> getLikedItems() {
    return likedItems;
  }

  public List<Item> getHatedItems() {
    return hatedItems;
  }

  public String getRelationshipStatus() {
    return relationshipStatus;
  }

  public void setRelationshipStatus(String relationshipStatus) {
    this.relationshipStatus = relationshipStatus;
  }

  public boolean hatedItems(Item item) {
    return hatedItems.contains(item);
  }

  public int getHeartPointsforItems(Item item) {
    if (lovedItems.contains(item)) {
      return 25;
    } else if (likedItems.contains(item)) {
      return 20;
    } else if (hatedItems.contains(item)) {
      return -20;
    } else {
      return 0;
    }
  }

  public String getConversation() {
    return conversation;
  }

  

}
