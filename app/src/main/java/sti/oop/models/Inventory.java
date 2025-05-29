package sti.oop.models;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Collections;
import sti.oop.models.Item.Item;

public class Inventory {
    private Map<Item, Integer> items;

    public Inventory() {
        items = new LinkedHashMap<>();
    }

    public int getItemCount(Item itemName) {
        return items.getOrDefault(itemName, 0);
    }
    
    public boolean hasItem(Item itemName) {
        return items.containsKey(itemName);
    }
    
    public Set<Item> getAllItem() {
        return Collections.unmodifiableSet(items.keySet());
    }

    public void addItem(Item item, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity is not valid! Quantity must be over 0");
        
        if (items.containsKey(item)) {
            items.put(item, getItemCount(item) + quantity);
        }
        
        else {
            items.put(item, quantity);
        }
    }

    public void removeItem(Item item, int quantity) {
        if (getItemCount(item) < quantity) throw new IllegalArgumentException("Not enough of item: " + item);
        int newQuantity = getItemCount(item) - quantity;
        if (newQuantity == 0) {
            items.remove(item);
        } else {
            items.put(item, newQuantity);
        }
    }
}