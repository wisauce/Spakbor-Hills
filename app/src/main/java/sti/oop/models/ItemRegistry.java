package sti.oop.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.EnumSet;
import java.util.function.Supplier;

import sti.oop.models.Item.*;

public class ItemRegistry {
    private static final Map<String, Supplier<Item>> ITEM_CREATORS = new HashMap<>();

    static {
        itemRegisterEquipment() ;
        itemRegisterFish();
        itemRegisterCrops();
        itemRegisterMisc();
    }

    //TODO: Finish all other items
    private static void itemRegisterEquipment() {
        ITEM_CREATORS.put("Hoe", () -> new Equipment("Hoe"));
    }

    private static void itemRegisterFish() {
        ITEM_CREATORS.put("Angler", () -> new Fish("Angler", EnumSet.of(Fish.Season.SPRING), EnumSet.of(Fish.Location.OCEAN), EnumSet.of(Fish.Weather.SUNNY), new int[]{6, 18}, "COMMON"));
    }

    private static void itemRegisterCrops() {

    }
    
    private static void itemRegisterMisc() {

    }

    public static Item createItem(String itemName){
        Supplier<Item> creator = ITEM_CREATORS.get(itemName);
        
        if (creator == null) {
            throw new IllegalArgumentException("Item is Unkown by System, Item Name is " + itemName);
        }
        else {
            return creator.get();
        }
    }

    public static Set<String> getAllItemNames() {
        return ITEM_CREATORS.keySet();
    }

}
