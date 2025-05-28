package sti.oop.utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;

public class SpriteManager {
    private static final Map<String, Image> spriteCache = new ConcurrentHashMap<>();
    private static final String SPRITE_PATH = "/items/";
    
    /* -------------------------------------------------------------------------- */
    /*                             Item Sprite Logics                             */
    /* -------------------------------------------------------------------------- */

    public static Image getItemSprite(String itemID) {
        return spriteCache.computeIfAbsent(itemID, id -> {
            try {
                String itemPath = SPRITE_PATH + id + ".png";
                Image itemSprite = new Image(SpriteManager.class.getResourceAsStream(itemPath));

                System.out.println("Successfully loaded: " + itemPath);
                return itemSprite;
            } catch (Exception e) {
                System.err.println("Failed to load sprite for: " + id + " at path: " + SPRITE_PATH + id + ".png");
                return getDefaultSprite();
            }
        });
    }
    
    /* TODO: Comment Setelah Semua Item Sudah Ada */
    private static Image getDefaultSprite() {
        return spriteCache.computeIfAbsent("default", id -> {
            try {
                String[] defaultPaths = {
                    "/items/default.png",
                };
                
                for (String path : defaultPaths) {
                    try {
                        Image defaultImage = new Image(SpriteManager.class.getResourceAsStream(path));
                        System.out.println("Default sprite loaded from: " + path);
                        spriteCache.put("default", defaultImage);
                        return defaultImage;
                    } catch (Exception ignored) {}
                }
                
                System.err.println("No default sprite found, creating placeholder");
                spriteCache.put("default", createPlaceholderImage());
                return createPlaceholderImage();

            } catch (Exception e) {
                System.err.println("Failed to load default sprite: " + e.getMessage());
                return createPlaceholderImage();
            }
        });
    }
    
    private static Image createPlaceholderImage() {
        WritableImage placeholder = new WritableImage(32, 32);
        PixelWriter pw = placeholder.getPixelWriter();
        
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                pw.setColor(x, y, javafx.scene.paint.Color.PURPLE);
            }
        }
        
        return placeholder;
    }
    /* TODO: Comment Setelah Semua Item Sudah Ada */


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
}