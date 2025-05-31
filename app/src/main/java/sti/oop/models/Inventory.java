package sti.oop.models;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import sti.oop.models.Item.Fish;
import sti.oop.models.Item.Item;

public class Inventory {
    private Map<Item, Integer> items;

    public Inventory() {
        items = new LinkedHashMap<>();
    }

    /* -------------------------------------------------------------------------- */
    /*                                 Item Logics                                */
    /* -------------------------------------------------------------------------- */

    /* By Name */

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

    /* By Item */
        
    public Set<Item> getAllItem() {
        return Collections.unmodifiableSet(items.keySet());
    }

    public void addItem(Item item, int quantity) {
        Item existingItem = findItemByName(item.getItemName());

        if (existingItem != null) {
            int currentQuantity = items.get(existingItem);
            items.put(existingItem, currentQuantity + quantity);
        } 
        else {
            items.put(item, quantity);
        }
    }

    public int getItemCount(Item item) {
        Item existingItem = findItemByName(item.getItemName());
        if (existingItem != null) {
            return items.get(existingItem);
        }
        return 0;
    }

    public void removeItem(Item item, int quantity) {
        Item existingItem = findItemByName(item.getItemName());
        
        if (existingItem == null) {
            throw new IllegalArgumentException("Item not found in inventory: " + item.getItemName());
        }
        
        int currentQuantity = items.get(existingItem);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough of item: " + item.getItemName() + 
                " (have " + currentQuantity + ", need " + quantity + ")");
        }
        
        int newQuantity = currentQuantity - quantity;
        if (newQuantity == 0) {
            items.remove(existingItem);
        } else {
            items.put(existingItem, newQuantity);
        }
    }

    private Item findItemByName (String itemName) {
        for (Item inventoryItem : items.keySet()) {
            if (inventoryItem.getItemName().equals(itemName)) {
                return inventoryItem;
            }
        }
        return null;
    }

    /* Item Name Wrapper */

    public int getItemCount(String itemName) {
        return getItemCountByName(itemName); 
    }

    public boolean hasItem(String itemName) {
        return hasItemByName(itemName); 
    }

    public void removeItem(String itemName, int quantity) {
        removeItemByName(itemName, quantity); 
    }

    public void addItem(String itemName, int quantity) {
        addItemByName(itemName, quantity);
    }

    /* -------------------------------------------------------------------------- */
    /*                              Fish Item Logics                              */
    /* -------------------------------------------------------------------------- */

    public int getTotalFish() {
        return items.entrySet().stream()
            .filter(entry -> entry.getKey().getItemType().equals("FISH"))
            .mapToInt(Map.Entry::getValue).sum();
    }

    public int getTotalLegendaryFish() {
        return items.entrySet().stream()
            .filter(entry -> entry.getKey().getItemType().equals("FISH"))
            .filter(entry -> ((Fish) entry.getKey()).getGrade().equals("LEGENDARY"))
            .mapToInt(Map.Entry::getValue).sum();
    }

    public boolean hasEnoughFish(int requiredAmount) {
        return getTotalFish() >= requiredAmount;
    }

    public boolean hasEnoughLegendaryFish(int requiredAmount) {
        return getTotalLegendaryFish() >= requiredAmount;
    }

    public void removeAnyFishWithPriority(int quantity) {
        int remaining = quantity;

        String[] priorities = {"COMMON", "REGULAR", "LEGENDARY"};

        for (String grade : priorities) {
            if (remaining <= 0) {
                break;
            }

            List<Item> fishGrade = items.keySet().stream()
                .filter(item -> item.getItemType().equals("FISH"))
                .filter(item -> ((Fish) item).getGrade().equals(grade))
                .collect(Collectors.toList());
            
            for (Item fish : fishGrade) {
                if (remaining <= 0) {
                    break;
                }

                int available = items.get(fish);
                int toRemove = Math.min(available, remaining);
                removeItem(fish, toRemove);
                remaining -= toRemove;
            }
        }
    }

    public void removeAnyLegendaryFish(int quantity) {
        int remaining = quantity;

        List<Item> fishGrade = items.keySet().stream()
            .filter(item -> item.getItemType().equals("FISH"))
            .filter(item -> ((Fish) item).getGrade().equals("LEGENDARY"))
            .collect(Collectors.toList());
        
        for (Item fish : fishGrade) {
            if (remaining <= 0) {
                break;
            }

            int available = items.get(fish);
            int toRemove = Math.min(available, remaining);
            removeItem(fish, toRemove);
            remaining -= toRemove;
        }
        
    }
}