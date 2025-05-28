package sti.oop.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import sti.oop.models.ItemRegistry;
import sti.oop.models.Item.Item;
import javafx.scene.image.PixelWriter;

import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.spec.PBEKeySpec;

import java.util.HashMap;
import java.util.Map;

public class ItemSpriteManager {
    private static final Map<String, Image> spriteCache = new ConcurrentHashMap<>();
   
    private static final String SPRITE_SHEET_PATH = "/items/itemSpriteSheet.png";
    private static Image itemSpriteSheet;
    private static final int ITEM_SPRITE_WIDTH = 64;
    private static final int ITEM_SPRITE_HEIGHT = 64;

    private static final Map<String, SpritePosition> SPRITE_POSITIONS = new HashMap<>();


    /* -------------------------------------------------------------------------- */
    /*                     Item Sprite Location Initializator                     */
    /* -------------------------------------------------------------------------- */

    /*  https://stackoverflow.com/questions/2420389/static-initialization-blocks  */
    static {

        // itemByCategory.put("SEED", new String[]{"Parsnip", "Cauliflower", "Potato", "Wheat", "Blueberry", "Tomato", "HotPepper", "Melon", "Cranberry", "Pumpkin", "Grape"});
        // itemByCategory.put("FISH", new String[]{""Angler", "Crimsonfish", "Glacierfish", "Legend"});
        // itemByCategory.put("CROP", new String[]{"Parsnip", "Cauliflower", "Potato", "Wheat", "Blueberry", "Tomato", "HotPepper", "Melon", "Cranberry", "Pumpkin", "Grape"});
        // itemByCategory.put("FOOD", new String[]{"FishNChips", "Baguette", "Sashimi", "Fugu", "Wine", "PumpkinPie", "VeggieSoup", "FishStew", "SpakborSalad", "FishSandwich", "TheLegendsOfSpakbor", "CookedPigsHead"});
        // itemByCategory.put("EQUIPMENT", new String[]{"Hoe", "WateringCan", "Pickaxe", "FishingRod"});
        // itemByCategory.put("MISC", new String[]{"Coal", "Firewood"});

        /* Seed */


        /* Fish */

        SPRITE_POSITIONS.put("FISH_Bullhead", new SpritePosition(8, 9));
        SPRITE_POSITIONS.put("FISH_Carp", new SpritePosition(8, 10));
        SPRITE_POSITIONS.put("FISH_Chub", new SpritePosition(8, 11));
        SPRITE_POSITIONS.put("FISH_LargemouthBass", new SpritePosition(8, 12));
        SPRITE_POSITIONS.put("FISH_RainbowTrout", new SpritePosition(8, 13));
        SPRITE_POSITIONS.put("FISH_Sturgeon", new SpritePosition(8, 14));
        SPRITE_POSITIONS.put("FISH_MidnightCarp", new SpritePosition(9, 6));
        SPRITE_POSITIONS.put("FISH_Flounder", new SpritePosition(9, 7));
        SPRITE_POSITIONS.put("FISH_Halibut", new SpritePosition(9, 8));
        SPRITE_POSITIONS.put("FISH_Octopus", new SpritePosition(12, 16));
        SPRITE_POSITIONS.put("FISH_Pufferfish", new SpritePosition(12, 17));
        SPRITE_POSITIONS.put("FISH_Sardine", new SpritePosition(11, 8));
        SPRITE_POSITIONS.put("FISH_SuperCucumber", new SpritePosition(8, 15));
        SPRITE_POSITIONS.put("FISH_Catfish", new SpritePosition(8, 16));
        SPRITE_POSITIONS.put("FISH_Salmon", new SpritePosition(15, 18));


        SPRITE_POSITIONS.put("FISH_Angler", new SpritePosition(14, 21));
        SPRITE_POSITIONS.put("FISH_Crimsonfish", new SpritePosition(15, 19));
        SPRITE_POSITIONS.put("FISH_Glacierfish", new SpritePosition(12, 18));
        SPRITE_POSITIONS.put("FISH_Legend", new SpritePosition(9, 5));

        /* Crop */

        /* Food */

        /* Equipment */
        SPRITE_POSITIONS.put("EQUIPMENT_Hoe", new SpritePosition(22,27));
        SPRITE_POSITIONS.put("EQUIPMENT_Pickaxe", new SpritePosition(20,31));
        SPRITE_POSITIONS.put("EQUIPMENT_WateringCan", new SpritePosition(23,8));
        SPRITE_POSITIONS.put("EQUIPMENT_FishingRod", new SpritePosition(11, 10));

        /* Misc */
        SPRITE_POSITIONS.put("MISC_Coal", new SpritePosition(13, 9));
        SPRITE_POSITIONS.put("MISC_Firewood", new SpritePosition(4, 3));
        SPRITE_POSITIONS.put("MISC_Gift", new SpritePosition(12, 7));
        SPRITE_POSITIONS.put("MISC_WeddingRing", new SpritePosition(29, 22));

       
    }

    private static void loadItemSpriteSheet() {
        if (itemSpriteSheet == null) {
            try {
                itemSpriteSheet = new Image(ItemSpriteManager.class.getResourceAsStream(SPRITE_SHEET_PATH));
                System.out.println("Sprite sheet loaded succesfully from " + SPRITE_SHEET_PATH);
            } catch (Exception e) {
                System.err.println("Failed to load sprite sheet: " + e.getMessage());
                itemSpriteSheet = createPlaceholderSpriteSheet();
            }
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                             Item Sprite Logics                             */
    /* -------------------------------------------------------------------------- */

    public static Image getItemSprite(String itemID) {
        return spriteCache.computeIfAbsent(itemID, id -> {
            loadItemSpriteSheet();

            SpritePosition itemPosition = SPRITE_POSITIONS.get(id);
            
            if (itemPosition == null) {
                System.err.println("No Sprite Found for: " + id);
                return getDefaultSprite();
            }

            try {
                int x = itemPosition.col * ITEM_SPRITE_WIDTH;
                int y = itemPosition.row * ITEM_SPRITE_HEIGHT;

                PixelReader reader = itemSpriteSheet.getPixelReader();
                WritableImage itemSprite = new WritableImage(reader, x, y, ITEM_SPRITE_WIDTH, ITEM_SPRITE_HEIGHT);

                return itemSprite;
            } catch (Exception e) {
                System.err.println("Failed to load sprite for: " + id + " with Position:  " + itemPosition.row + ", " + itemPosition.col);
                return getDefaultSprite();
            }
        });
    }

    /* -------------------------------------------------------------------------- */
    /*                      Default Sprite If Null In Sprite                      */
    /* -------------------------------------------------------------------------- */

    private static Image getDefaultSprite() {
        return spriteCache.computeIfAbsent("default", id -> {
            WritableImage placeholder = new WritableImage(ITEM_SPRITE_WIDTH, ITEM_SPRITE_HEIGHT);
            PixelWriter pw = placeholder.getPixelWriter();
            
            for (int x = 0; x < ITEM_SPRITE_WIDTH; x++) {
                for (int y = 0; y < ITEM_SPRITE_HEIGHT; y++) {
                    pw.setColor(x, y, javafx.scene.paint.Color.PURPLE);
                }
            }
            
            return placeholder;
        });
    }

    /* -------------------------------------------------------------------------- */
    /*                           Make Blank Sprite Sheet                          */
    /* -------------------------------------------------------------------------- */

    private static Image createPlaceholderSpriteSheet() {
        WritableImage placeholder = new WritableImage(ITEM_SPRITE_WIDTH, ITEM_SPRITE_HEIGHT);
        PixelWriter pw = placeholder.getPixelWriter();
        
        for (int x = 0; x < ITEM_SPRITE_WIDTH; x++) {
            for (int y = 0; y < ITEM_SPRITE_HEIGHT; y++) {
                pw.setColor(x, y, javafx.scene.paint.Color.PURPLE);
            }
        }
        
        return placeholder;
    }


    /* -------------------------------------------------------------------------- */
    /*                                Sprite Loader                               */
    /* -------------------------------------------------------------------------- */
    //TODO: Change Sprite Loader
    public static void preloadSprites() {
        Map<String, String[]> itemByCategory = new HashMap<>();

        //Set<String> allItem = ItemRegistry.getAllItemNames();
        
        // itemByCategory.put("SEED", new String[]{"Parsnip", "Cauliflower", "Potato", "Wheat", "Blueberry", "Tomato", "HotPepper", "Melon", "Cranberry", "Pumpkin", "Grape"});
        itemByCategory.put("FISH", new String[]{"Bullhead", "Carp", "Chub", "LargemouthBass", "RainbowTrout", "Sturgeon", "MidnightCarp", "Flounder", "Halibut", "Octopus", "Pufferfish", "Sardine", "SuperCucumber", "Catfish", "Salmon", "Angler", "Crimsonfish", "Glacierfish", "Legend"});
        // itemByCategory.put("CROP", new String[]{"Parsnip", "Cauliflower", "Potato", "Wheat", "Blueberry", "Tomato", "HotPepper", "Melon", "Cranberry", "Pumpkin", "Grape"});
        // itemByCategory.put("FOOD", new String[]{"FishNChips", "Baguette", "Sashimi", "Fugu", "Wine", "PumpkinPie", "VeggieSoup", "FishStew", "SpakborSalad", "FishSandwich", "TheLegendsOfSpakbor", "CookedPigsHead"});
        itemByCategory.put("EQUIPMENT", new String[]{"Hoe", "WateringCan", "Pickaxe", "FishingRod"});
        itemByCategory.put("MISC", new String[]{"Coal", "Firewood", "Gift", "WeddingRing"});


        // for (String itemName: allItem) {
        //     Item item = ItemRegistry.createItem(itemName);
        //     String itemID = item.getItemID();
        //     getItemSprite(itemID);
        // }

        for (Map.Entry<String, String[]> itemEntry : itemByCategory.entrySet()) {
            String itemType = itemEntry.getKey();
            String[] itemName = itemEntry.getValue();

            for (String name : itemName) {
                String itemID = itemType + "_" + name;
                getItemSprite(itemID);
            }
        }
        
        System.out.println("Preloaded " + spriteCache.size() + " item sprites");
    }

    private static class SpritePosition {
        final int row, col;
        SpritePosition(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    
}