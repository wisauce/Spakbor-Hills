package sti.oop.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Season;
import sti.oop.models.Weather;
import sti.oop.models.Item.Fish;

public class Fishing {
    private final Random random = new Random();
    public boolean hasRequiredItems(Player player){
        return true;//player.getInventory().hasItem(new Item("Fishing Rod", "EQUIPMENT"));
    }

    public List<Fish> availableFishList(List<Fish> fishes, Farm farm){
        List<Fish> container = new ArrayList<>();
        for (Fish fish : fishes){
            for(Season seasons : fish.getSeason()){
                if (farm.getSeason().equals(seasons)){
                    for(Weather weathers : fish.getWeather()){
                        if (farm.getWeather().equals(weathers)){
                            container.add(fish);
                        }
                    }
                }
            }
        }
        return container;
    }

    public Fish randomizeFish(List<Fish> fishes){
        int rands = random.nextInt(1,100);
        List<Integer> container = new ArrayList<>();
        if (rands < 60){
            for (Fish fish : fishes){
                if (fish.getGrade().equals("COMMON")){
                    System.out.println(fish.getItemName());
                    container.add(fishes.indexOf(fish));
                }
            }
            int randFish = random.nextInt(0,container.size());
            return fishes.get(container.get(randFish));
        }
        else if (rands < 90){
            for (Fish fish : fishes){
                if (fish.getGrade().equals("REGULAR")){
                    System.out.println(fish.getItemName());
                    container.add(fishes.indexOf(fish));
                }
            }
            int randFish = random.nextInt(0,container.size());
            return fishes.get(container.get(randFish));
        }
        else{
            for (Fish fish : fishes){
                if (fish.getGrade().equals("LEGENDARY")){
                    System.out.println(fish.getItemName());
                    container.add(fishes.indexOf(fish));
                }
            }
            int randFish = random.nextInt(0,container.size());
            return fishes.get(container.get(randFish));
        }
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
                player.addAmountOfFishReeled();
                if (player.getAmountOfFishReeled() == 10) {
                    player.getInventory().addItemByName("SashimiRecipe", 1);
                }
                if (!player.getEverPufferfish() && fish.getItemName().equals("Pufferfish")) {
                    player.wasEverPufferfish();
                    player.getInventory().addItemByName("FuguRecipe", 1);
                }
                if (!player.getEverLegendaryFish() && fishGrade.equals("LEGENDARY")) {
                    player.wasEverLegendaryFish();
                    player.getInventory().addItemByName("TheLegendsOfSpakborRecipe", 1);
                }
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