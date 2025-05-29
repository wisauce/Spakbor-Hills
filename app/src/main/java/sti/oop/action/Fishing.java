package sti.oop.action;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import sti.oop.models.Player;
import sti.oop.models.Item.Fish;
import sti.oop.models.Item.Fish.Location;
import sti.oop.models.Item.Fish.Weather;

public class Fishing {
    private final Random random = new Random();
    public boolean hasRequiredItems(Player player){
        return true;//player.getInventory().hasItem(new Item("Fishing Rod", "EQUIPMENT"));
    }

    public void doFishing(Player player, Fish fish){
        String fishGrade = fish.getGrade().toUpperCase();
        int min = 1;
        int max = 10; 
        int maxAttempts = 10;

        switch (fishGrade) {
            case "COMMON" -> {
                max = 10;
                maxAttempts = 10;
            }
            case "REGULAR" -> {
                max = 100;
                maxAttempts = 10;
            }
            case "LEGENDARY" -> {
                max = 500;
                maxAttempts = 7;
            }
            default -> {
                System.out.println(fish.getItemName() + " fish grade: " + fishGrade);
                return;
            }
        }
        int target = random.nextInt(max - min + 1) + min;
        boolean caught = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            int guess = random.nextInt(max - min + 1) + min;
            System.out.println("Attempt " + attempt + ": Guess = " + guess);

            if (guess == target) {
                caught = true;
                System.out.println(player.getName() + " caught a " + fishGrade + " fish!");
                player.getInventory().addItem(fish, 1); // tambahkan ke inventory
                break;
            } else if (guess < target) {
                System.out.println("Too low.");
            } else {
                System.out.println("Too high.");
            }
        }

        if (!caught) {
            System.out.println(player.getName() + " failed to catch the fish. Target was: " + target);
        }
    }
}