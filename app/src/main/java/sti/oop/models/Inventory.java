package sti.oop.models;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;

public class Inventory {
    // Change all of String to Items whenever is available
    private Map<String, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public int getItemCount(String itemName) {
        return items.getOrDefault(itemName, 0); //if itemName is not in items, return 0. Else, return Value of Key.
    }

    public boolean hasItem(String itemName) {
        return items.containsKey(itemName);
    }
    
    public Set<String> getAllItemName() {
        return Collections.unmodifiableSet(items.keySet());
    }

    public void addItem(String itemName, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity is not valid! Quantity must be over 0");
        items.put(itemName, getItemCount(itemName) + quantity);
    }

    public void removeItem(String itemName, int quantity) {
        if (getItemCount(itemName) < quantity) throw new IllegalArgumentException("Not enough of item: " + itemName);
        int newQuantity = getItemCount(itemName) - quantity;
        if (newQuantity == 0) {
            items.remove(itemName);
        } else {
            items.put(itemName, newQuantity);
        }
    }
}