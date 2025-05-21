package sti.oop.models;

import java.util.List;
import javafx.scene.image.Image;

public class NPC {
    private final int maxHeartPoints = 150;
    private String name;
    private Point location;
    private int heartPoints;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private String relationshipStatus;

    // attributes for rendering
    public Image NPCSpriteSheet;

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

    public NPC(String name, Point location, List<String> lovedItems, List<String> likedItems, List<String> hatedItems){
        this.name = name;
        this.location = location;
        heartPoints = 0;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
        relationshipStatus = "Single";
        loadSprite();

        // hitboxWidth = 128;
        // hitboxHeight = 128;
        // hitbox = new Rectangle2D(getLocation().x, getLocation().y, hitboxWidth, hitboxHeight);
    }

    private void loadSprite() {
        if (getName().equals("MayorTadi")){
            try{
                NPCSpriteSheet = new Image(Player.class.getResource("/sprites/mayorTadi.gif").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("[Error] can't load sprite for Mayor Tadi: " + e.getMessage());
                NPCSpriteSheet = null;
            }
        }
        else if (getName().equals("Caroline")) {
            try{
                NPCSpriteSheet = new Image(Player.class.getResource("/sprites/carolineCarpenter.gif").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("[Error] can't load sprite for Caroline: " + e.getMessage());
                NPCSpriteSheet = null;
            }
        }
        else if (getName().equals("Perry")) {
            try{
                NPCSpriteSheet = new Image(Player.class.getResource("/sprites/perryAuthor.gif").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("[Error] can't load sprite for Perry: " + e.getMessage());
                NPCSpriteSheet = null;
            }
        }
        else if (getName().equals("Dasco")) {
            try{
                NPCSpriteSheet = new Image(Player.class.getResource("/sprites/dascoDealer.gif").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("[Error] can't load sprite for Dasco: " + e.getMessage());
                NPCSpriteSheet = null;
            }
        }
        else if (getName().equals("Emily")) {
            try{
                NPCSpriteSheet = new Image(Player.class.getResource("/sprites/emilyCooker.gif").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("[Error] can't load sprite for Emily: " + e.getMessage());
                NPCSpriteSheet = null;
            }
        }
        else if (getName().equals("Abigail")) {
            try{
                NPCSpriteSheet = new Image(Player.class.getResource("/sprites/abigailExplorer.gif").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("[Error] can't load sprite for Abigail: " + e.getMessage());
                NPCSpriteSheet = null;
            }
        }
        else{
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

    public void setHeartPoints(int heartPoints){
        if (heartPoints > maxHeartPoints) {
            this.heartPoints = maxHeartPoints;
        }
        else if (heartPoints < 0) {
            this.heartPoints = 0;
        }
        else{
            this.heartPoints = heartPoints;
        }
    }

    public void increaseHeartPoints(int heartPointsValue) {
        setHeartPoints(getHeartPoints() + heartPointsValue);
    }

    public void decreaseHeartPoints(int heartPointsValue) {
        setHeartPoints(getHeartPoints() - heartPointsValue);
    }

    public List<String> getLovedItems() {
        return lovedItems;
    }

    public List<String> getLikedItems() {
        return likedItems;
    }

    public List<String> getHatedItems() {
        return hatedItems;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public boolean hatedItems(String itemName) {
        if (this.getName().equals("MayorTadi")) {
            return !lovedItems.contains(itemName) && !likedItems.contains(itemName);
        }
        else {
            return hatedItems.contains(itemName);
        }
    }

    public int getHeartPointsforItems(String itemName) {
        if (lovedItems.contains(itemName)) {
            return 25;
        }
        else if (likedItems.contains(itemName)) {
            return 20;
        }
        else if (hatedItems.contains(itemName)) {
            return -20;
        }
        else {
            return 0;
        }
    }


}
