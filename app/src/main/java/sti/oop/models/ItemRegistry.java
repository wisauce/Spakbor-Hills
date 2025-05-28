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
        itemRegisterCrop();
        itemRegisterMisc();
        itemRegisterSeed();
        itemRegisterFurniture();
        itemRegisterFood();
    }

    //TODO: Finish all other items
    private static void itemRegisterEquipment() {
        ITEM_CREATORS.put("Hoe", () -> new Equipment("Hoe"));
        ITEM_CREATORS.put("Pickaxe", () -> new Equipment("Pickaxe"));
        ITEM_CREATORS.put("WateringCan", () -> new Equipment("WateringCan"));
        ITEM_CREATORS.put("FishingRod", () -> new Equipment("FishingRod"));
    }

    private static void itemRegisterFish() {

        /* -------------------------------------------------------------------------- */
        /*                                 Common Fish                                */
        /* -------------------------------------------------------------------------- */

        ITEM_CREATORS.put(
        "Bullhead", () -> new Fish("Bullhead", 
        EnumSet.allOf(Fish.Season.class), 
        new int[] {0, 24}, 
        EnumSet.allOf(Fish.Weather.class), 
        EnumSet.of(Fish.Location.MOUNTAIN_LAKE), 
        "COMMON")
        );

        ITEM_CREATORS.put("Carp", () -> new Fish("Carp", 
        EnumSet.allOf(Fish.Season.class),
        new int[] {0, 24},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.MOUNTAIN_LAKE, Fish.Location.POND),
        "COMMON")
        );

        ITEM_CREATORS.put("Chub", () -> new Fish("Chub",
        EnumSet.allOf(Fish.Season.class),
        new int[] {0, 24},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.FOREST_RIVER, Fish.Location.MOUNTAIN_LAKE),
        "COMMON")
        );


        /* -------------------------------------------------------------------------- */
        /*                                Regular Fish                                */
        /* -------------------------------------------------------------------------- */

        ITEM_CREATORS.put("LargemouthBass", () -> new Fish("LargemouthBass",
        EnumSet.allOf(Fish.Season.class),
        new int[] {6, 18},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.MOUNTAIN_LAKE),
        "REGULAR")
        );

        ITEM_CREATORS.put("RainbowTrout", () -> new Fish("RainbowTrout",
        EnumSet.of(Fish.Season.SUMMER),
        new int[] {6, 18},
        EnumSet.of(Fish.Weather.SUNNY),
        EnumSet.of(Fish.Location.FOREST_RIVER, Fish.Location.MOUNTAIN_LAKE),
        "REGULAR")
        );

        ITEM_CREATORS.put("Sturgeon", () -> new Fish("Sturgeon",
        EnumSet.of(Fish.Season.SUMMER, Fish.Season.WINTER),
        new int[] {6, 18},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.MOUNTAIN_LAKE),
        "REGULAR")
        );

        ITEM_CREATORS.put("MidnightCarp", () -> new Fish("MidnightCarp",
        EnumSet.of(Fish.Season.FALL, Fish.Season.WINTER),
        new int[] {20, 2},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.POND, Fish.Location.MOUNTAIN_LAKE),
        "REGULAR")
        );

        ITEM_CREATORS.put("Flounder", () -> new Fish("Flounder",
        EnumSet.of(Fish.Season.SUMMER, Fish.Season.SPRING),
        new int[] {6, 22},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.OCEAN),
        "REGULAR")
        );

        ITEM_CREATORS.put("Halibut", () -> new Fish("Halibut",
        EnumSet.allOf(Fish.Season.class),
        new int[] {6, 11, 19, 2},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.OCEAN),
        "REGULAR")
        );

        ITEM_CREATORS.put("Octopus", () -> new Fish("Octopus",
        EnumSet.of(Fish.Season.SUMMER),
        new int[] {6, 22},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.OCEAN),
        "REGULAR")
        );

        ITEM_CREATORS.put("Pufferfish", () -> new Fish("Pufferfish",
        EnumSet.of(Fish.Season.SUMMER),
        new int[] {0, 16},
        EnumSet.of(Fish.Weather.SUNNY),
        EnumSet.of(Fish.Location.OCEAN),
        "REGULAR")
        );

        ITEM_CREATORS.put("Sardine", () -> new Fish("Sardine",
        EnumSet.allOf(Fish.Season.class),
        new int[] {6, 18},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.OCEAN),
        "REGULAR")
        );

        ITEM_CREATORS.put("SuperCucumber", () -> new Fish("SuperCucumber",
        EnumSet.of(Fish.Season.SUMMER, Fish.Season.FALL, Fish.Season.WINTER),
        new int[] {18, 2},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.OCEAN),
        "REGULAR")
        );   
        
        ITEM_CREATORS.put("Catfish", () -> new Fish("Catfish",
        EnumSet.of(Fish.Season.SUMMER, Fish.Season.FALL, Fish.Season.SPRING),
        new int[] {6, 22},
        EnumSet.of(Fish.Weather.RAINY),
        EnumSet.of(Fish.Location.FOREST_RIVER, Fish.Location.POND),
        "REGULAR")
        );      

        ITEM_CREATORS.put("Salmon", () -> new Fish("Salmon",
        EnumSet.of(Fish.Season.FALL),
        new int[] {6, 18},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.FOREST_RIVER),
        "REGULAR")
        );
        
        
        /* -------------------------------------------------------------------------- */
        /*                               Legendary Fish                               */
        /* -------------------------------------------------------------------------- */

        ITEM_CREATORS.put("Angler", () -> new Fish("Angler",
        EnumSet.of(Fish.Season.FALL),
        new int[] {8, 20},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.POND),
        "LEGENDARY")
        );

        ITEM_CREATORS.put("Crimsonfish", () -> new Fish("Crimsonfish",
        EnumSet.of(Fish.Season.SUMMER),
        new int[] {8, 20},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.OCEAN),
        "LEGENDARY")
        );

        ITEM_CREATORS.put("Glacierfish", () -> new Fish("Glacierfish",
        EnumSet.of(Fish.Season.WINTER),
        new int[] {8, 20},
        EnumSet.allOf(Fish.Weather.class),
        EnumSet.of(Fish.Location.FOREST_RIVER),
        "LEGENDARY")
        );

        ITEM_CREATORS.put("Legend", () -> new Fish("Legend",
        EnumSet.of(Fish.Season.SPRING),
        new int[] {8, 20},
        EnumSet.of(Fish.Weather.RAINY),
        EnumSet.of(Fish.Location.MOUNTAIN_LAKE),
        "LEGENDARY")
        );
    }

    private static void itemRegisterCrop() {
        
        ITEM_CREATORS.put("Parsnip", () -> new Crop("Parsnip", 50, 35, 1));
        ITEM_CREATORS.put("Cauliflower", () -> new Crop("Cauliflower", 200, 150, 1));
        ITEM_CREATORS.put("Potato", () -> new Crop("Potato", 0, 80, 1));
        ITEM_CREATORS.put("Wheat", () -> new Crop("Wheat", 50, 30, 3));
        ITEM_CREATORS.put("Blueberry", () -> new Crop("Blueberry", 150, 40, 3));
        ITEM_CREATORS.put("Tomato", () -> new Crop("Tomato", 90, 60, 1));
        ITEM_CREATORS.put("HotPepper", () -> new Crop("HotPepper", 0, 40, 1));
        ITEM_CREATORS.put("Melon", () -> new Crop("Melon", 0, 250, 1));
        ITEM_CREATORS.put("Cranberry", () -> new Crop("Cranberry", 0, 25, 10));
        ITEM_CREATORS.put("Pumpkin", () -> new Crop("Pumpkin", 300, 250, 1));
        ITEM_CREATORS.put("Grape", () -> new Crop("Grape", 100, 10, 20));

    }
    
    private static void itemRegisterMisc() {

        ITEM_CREATORS.put("Coal", () -> new Misc("Coal", 100, 50));
        ITEM_CREATORS.put("Firewood", () -> new Misc("Firewood", 150, 75));
        ITEM_CREATORS.put("Gift", () -> new Misc("Gift", 250, 125));
        ITEM_CREATORS.put("WeddingRing", () -> new Misc("WeddingRing", 1500, 50));
        //TODO: FIND MORE OTHER ITEMS THATS NOT LISTED
    }

    private static void itemRegisterSeed() {

        /* -------------------------------------------------------------------------- */
        /*                                 Spring Seed                                */
        /* -------------------------------------------------------------------------- */

        ITEM_CREATORS.put("ParsnipSeeds", () -> new Seed("ParsnipSeeds", 1, 20, Seed.Season.SPRING));
        ITEM_CREATORS.put("CauliflowerSeeds", () -> new Seed("CauliflowerSeeds", 5, 80, Seed.Season.SPRING));
        ITEM_CREATORS.put("PotatoSeeds", () -> new Seed("PotatoSeeds", 3, 50, Seed.Season.SPRING));
        ITEM_CREATORS.put("WheatSeeds", () -> new Seed("WheatSeeds", 1, 60, Seed.Season.SPRING));


        /* -------------------------------------------------------------------------- */
        /*                                 Summer Seed                                */
        /* -------------------------------------------------------------------------- */

        ITEM_CREATORS.put("BlueberrySeeds", () -> new Seed("BlueberrySeeds", 7, 80, Seed.Season.SUMMER));
        ITEM_CREATORS.put("TomatoSeeds", () -> new Seed("TomatoSeeds", 3, 50, Seed.Season.SUMMER));
        ITEM_CREATORS.put("HotPepperSeeds", () -> new Seed("HotPepperSeeds", 1, 40, Seed.Season.SUMMER));
        ITEM_CREATORS.put("MelonSeeds", () -> new Seed("MelonSeeds", 4, 80, Seed.Season.SUMMER));


        /* -------------------------------------------------------------------------- */
        /*                                  Fall Seed                                 */
        /* -------------------------------------------------------------------------- */

        ITEM_CREATORS.put("CranberrySeeds", () -> new Seed("CranberrySeeds", 2, 100, Seed.Season.FALL));
        ITEM_CREATORS.put("PumpkinSeeds", () -> new Seed("PumpkinSeeds", 7, 150, Seed.Season.FALL));
        ITEM_CREATORS.put("WheatSeeds", () -> new Seed("WheatSeeds", 1, 60, Seed.Season.FALL));
        ITEM_CREATORS.put("GrapeSeeds", () -> new Seed("GrapeSeeds", 3, 60, Seed.Season.FALL));

        
        /* -------------------------------------------------------------------------- */
        /*                                 Winter Seed                                */
        /* -------------------------------------------------------------------------- */

    }

    private static void itemRegisterFurniture() {

    }

    private static void itemRegisterFood() {
        
        ITEM_CREATORS.put("FishnChips", () -> new Food("FishnChips", 50, 150, 135));
        ITEM_CREATORS.put("Baguette", () -> new Food("Baguette", 25, 100, 80));
        ITEM_CREATORS.put("Sashimi", () -> new Food("Sashimi", 70, 300, 275));
        ITEM_CREATORS.put("Fugu", () -> new Food("Fugu", 50, 0, 135));
        ITEM_CREATORS.put("Wine", () -> new Food("Wine", 20, 100, 90));
        ITEM_CREATORS.put("PumpkinPie", () -> new Food("PumpkinPie", 35, 120, 100));
        ITEM_CREATORS.put("VeggieSoup", () -> new Food("VeggieSoup", 40, 140, 120));
        ITEM_CREATORS.put("FishStew", () -> new Food("FishStew", 70, 280, 260));
        ITEM_CREATORS.put("SpakborSalad", () -> new Food("SpakborSalad", 70, 0, 250));
        ITEM_CREATORS.put("FishSandwich", () -> new Food("FishSandwich", 50, 200, 180));
        ITEM_CREATORS.put("TheLegendsOfSpakbor", () -> new Food("TheLegendsOfSpakbor", 100, 0, 2000));
        ITEM_CREATORS.put("CookedPigsHead", () -> new Food("FishCookedPigsHeadnChips", 100, 1000, 0));

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
