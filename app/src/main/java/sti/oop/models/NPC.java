package sti.oop.models;

import java.util.List;

public class NPC {
    private final int maxHeartPoints = 150;
    private String name;
    private Point location;
    private int heartPoints;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private String relationshipStatus;

    public NPC(String name, Point location, List<String> lovedItems, List<String> likedItems, List<String> hatedItems){
        this.name = name;
        this.location = location;
        heartPoints = 0;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
        relationshipStatus = "Single";
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

}
