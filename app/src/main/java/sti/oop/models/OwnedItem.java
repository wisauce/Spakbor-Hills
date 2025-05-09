package sti.oop.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;

import javafx.scene.image.Image;

public class OwnedItem {
    // Change all of String to Items whenever is available
    private Map<String, Integer> inventory;

    public OwnedItem() {
        inventory = new HashMap<>();
    }

    public int getItemCount(String itemName) {
        return inventory.getOrDefault(itemName, 0); //if itemName is not in Inventory, return 0. Else, return Value of Key.
    }

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName);
    }
    
    public Set<String> getAllItemName() {
        return Collections.unmodifiableSet(inventory.keySet());
    }

    public void addItem(String itemName, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity is not valid! Quantity must be over 0");
        }

        inventory.put(itemName, getItemCount(itemName) + quantity);
    }

    public void removeItem(String itemName, int quantity) {
        int currentQuantity = getItemCount(itemName);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough of item: " + itemName);
        }
        
        int newQuantity = currentQuantity - quantity;
        if (newQuantity == 0) {
            inventory.remove(itemName);
        }
        else {
            inventory.put(itemName, newQuantity);
        }
    }
}