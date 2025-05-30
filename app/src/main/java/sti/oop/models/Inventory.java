package sti.oop.models;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;

import sti.oop.models.Item.Item;

import java.util.Collections;

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

    public boolean hasItemByName(String itemName) {
        return items.keySet().stream().anyMatch(item -> item.getItemName().equals(itemName));
    }

    public int getItemCountByName(String itemName) {
        return items.entrySet().stream().filter(entry -> entry.getKey().getItemName().equals(itemName)).mapToInt(Map.Entry::getValue).sum();
    }

    public Item getItemByName(String itemName) {
        return items.keySet().stream().filter(item -> item.getItemName().equals(itemName)).findFirst().orElse(null);
    }

    public void removeItemByName(String itemName, int quantity) {
        Item item = getItemByName(itemName);
        if (item != null) {
            removeItem(item, quantity);
        } else {
            throw new IllegalArgumentException("Item not found: " + itemName);
        }
    }
    
    public void addItemByName(String itemName, int quantity) {
        Item existingItem = getItemByName(itemName);
        if (existingItem != null) {
            addItem(existingItem, quantity);
        } else {
            Item newItem = ItemRegistry.createItem(itemName);
            addItem(newItem, quantity);
        }
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