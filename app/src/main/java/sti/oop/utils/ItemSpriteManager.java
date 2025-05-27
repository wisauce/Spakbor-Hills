package sti.oop.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;

public class ItemSpriteManager {
    private static final Map<String, Image> spriteCache = new ConcurrentHashMap<>();
   
    private static final String SPRITE_SHEET_PATH = "/items/itemSpriteSheet.png";
    private static Image itemSpriteSheet;
    private static final int ITEM_SPRITE_WIDTH = 64;
    private static final int ITEM_SPRITE_HEIGHT = 64;

    private static final Map<String, SpritePosition> SPRITE_POSITIONS = new HashMap<>();

    static {
        SPRITE_POSITIONS.put("EQUIPMENT_Pickaxe", new SpritePosition( 20,31));
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
    
    public static void preloadSprites() {
        Map<String, String[]> itemByCategory = new HashMap<>();
        
        // itemByCategory.put("SEED", new String[]{"Parsnip", "Cauliflower", "Potato", "Wheat", "Blueberry", "Tomato", "HotPepper", "Melon", "Cranberry", "Pumpkin", "Grape"});
        // itemByCategory.put("FISH", new String[]{"Bullhead", "Carp", "Chub", "LargemouthBass", "RainbowTrout", "Sturgeon", "MidnightCarp", "Flounder", "Halibut", "Octopus", "Pufferfish", "Sardine", "SuperCucumber", "Catfish", "Salmon", "Angler", "Crimsonfish", "Glacierfish", "Legend"});
        // itemByCategory.put("CROP", new String[]{"Parsnip", "Cauliflower", "Potato", "Wheat", "Blueberry", "Tomato", "HotPepper", "Melon", "Cranberry", "Pumpkin", "Grape"});
        // itemByCategory.put("FOOD", new String[]{"FishNChips", "Baguette", "Sashimi", "Fugu", "Wine", "PumpkinPie", "VeggieSoup", "FishStew", "SpakborSalad", "FishSandwich", "TheLegendsOfSpakbor", "CookedPigsHead"});
        // itemByCategory.put("EQUIPMENT", new String[]{"Hoe", "WateringCan", "Pickaxe", "FishingRod"});
        // itemByCategory.put("MISC", new String[]{"Coal", "Firewood"});

        itemByCategory.put("EQUIPMENT", new String[]{"Pickaxe"});

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