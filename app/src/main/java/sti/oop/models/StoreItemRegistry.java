package sti.oop.models;

import java.util.ArrayList;
import java.util.List;
import sti.oop.models.Item.Item;

public class StoreItemRegistry {
    
    public static List<Item> getStoreItems() {
        List<Item> storeItems = new ArrayList<>();

        storeItems.add(ItemRegistry.createItem("ParsnipSeeds"));
        storeItems.add(ItemRegistry.createItem("CauliflowerSeeds"));
        storeItems.add(ItemRegistry.createItem("PotatoSeeds"));
        storeItems.add(ItemRegistry.createItem("WheatSeeds"));
        storeItems.add(ItemRegistry.createItem("BlueberrySeeds"));
        storeItems.add(ItemRegistry.createItem("TomatoSeeds"));
        storeItems.add(ItemRegistry.createItem("HotPepperSeeds"));
        storeItems.add(ItemRegistry.createItem("MelonSeeds"));
        storeItems.add(ItemRegistry.createItem("CranberrySeeds"));
        storeItems.add(ItemRegistry.createItem("PumpkinSeeds"));
        storeItems.add(ItemRegistry.createItem("GrapeSeeds"));

        storeItems.add(ItemRegistry.createItem("Coal"));
        storeItems.add(ItemRegistry.createItem("Firewood"));
        storeItems.add(ItemRegistry.createItem("Gift"));
        storeItems.add(ItemRegistry.createItem("WeddingRing"));
        
        storeItems.add(ItemRegistry.createItem("FishnChipsRecipe"));
        storeItems.add(ItemRegistry.createItem("FishSandwichRecipe"));
        
        return storeItems;
    }
}