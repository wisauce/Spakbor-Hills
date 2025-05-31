// In Fishing.java
package sti.oop.action;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt; // Important for the callback
import java.util.Random;

import sti.oop.controllers.FarmController; // To update hotbar, etc.
import sti.oop.controllers.PanelController;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Season;
import sti.oop.models.Weather;
import sti.oop.models.Item.Fish;

public class Fishing {
    private final Random random = new Random();

    public boolean hasRequiredItems(Player player) {
        return player.hasItemInHand("FishingRod");
    }

    public List<Fish> availableFishList(List<Fish> allPossibleFishes, Farm farm) {
        List<Fish> availableToday = new ArrayList<>();
        if (allPossibleFishes == null || farm == null || farm.getSeason() == null || farm.getWeather() == null) {
            return availableToday; // Return empty if essential info is missing
        }
        for (Fish fish : allPossibleFishes) {
            if (fish.getSeason() == null || fish.getWeather() == null) continue; // Skip fish with incomplete data

            boolean seasonMatch = false;
            for (Season season : fish.getSeason()) {
                if (farm.getSeason().equals(season)) {
                    seasonMatch = true;
                    break;
                }
            }
            if (!seasonMatch) continue;

            boolean weatherMatch = false;
            for (Weather weather : fish.getWeather()) {
                if (farm.getWeather().equals(weather)) {
                    weatherMatch = true;
                    break;
                }
            }
            if (weatherMatch) {
                availableToday.add(fish);
            }
        }
        return availableToday;
    }

    public Fish randomizeFish(List<Fish> availableFishes) {
        if (availableFishes == null || availableFishes.isEmpty()) {
            return null;
        }
    // int rands = random.nextInt(1, availableFishes.size());
    // List<Integer> container = new ArrayList<>();
    // if (rands < 60){
    // for (Fish fish : availableFishes){
    // if (fish.getGrade().equals("COMMON")){
    // System.out.println(fish.getItemName());
    // container.add(availableFishes.indexOf(fish));
    // }
    // }
    // int randFish = random.nextInt(0,container.size());
    // return availableFishes.get(container.get(randFish));
    // }
    // else if (rands < 90){
    // for (Fish fish : availableFishes){
    // if (fish.getGrade().equals("REGULAR")){
    // System.out.println(fish.getItemName());
    // container.add(availableFishes.indexOf(fish));
    // }
    // }
    // int randFish = random.nextInt(0,container.size());
    // return availableFishes.get(container.get(randFish));
    // }
    // else{
    // for (Fish fish : availableFishes){
    // if (fish.getGrade().equals("LEGENDARY")){
    // System.out.println(fish.getItemName());
    // container.add(availableFishes.indexOf(fish));
    // }
    // }
    // int randFish = random.nextInt(0, container.size());
        int randFishIndex = random.nextInt(availableFishes.size());
        return availableFishes.get(randFishIndex);
    }

    /**
     * Initiates the interactive fishing mini-game using the PanelController.
     *
     * @param player          The player who is fishing.
     * @param fishToCatch     The fish determined to be on the line.
     * @param panelController The controller for UI interactions.
     * @param farmController  The controller for game state updates (like hotbar).
     */
    public void startInteractiveFishing(Player player, Fish fishToCatch, PanelController panelController, FarmController farmController) {
        if (fishToCatch == null) {
            panelController.showDialog("Hmm, nothing seems to be biting right now.");
            if (farmController != null) farmController.updateHotbar(); // For energy or other UI updates
            return;
        }

        String fishGrade = fishToCatch.getGrade().toUpperCase();
        int minRange = 1;
        int maxRange = 10;  // Default for common
        int maxAttempts = 7; // Default for common

        switch (fishGrade) {
            case "COMMON" -> {
                maxRange = 10;
                maxAttempts = 10;
            }
            case "REGULAR" -> {
                maxRange = 100; // Example: make REGULAR a bit harder
                maxAttempts = 10;
            }
            case "LEGENDARY" -> {
                maxRange = 500; // Example: LEGENDARY is tough
                maxAttempts = 7;
            }
            default -> {
                System.out.println(fishToCatch.getItemName() + " fish grade: " + fishGrade + " is not recognized for difficulty scaling.");
                panelController.showDialog("An unusual fish is on the line! But its strength is unknown...");
                // Defaulting to common difficulty or simply returning:
                // For safety, let's default or you can choose to not let it be caught.
                maxRange = 10;
                maxAttempts = 7;
                // return; // Or let it proceed with default difficulty
            }
        }

        // Ensure minRange is not greater than maxRange
        if (minRange > maxRange) {
            minRange = 1; // Or some other sensible default
            maxRange = Math.max(minRange, maxRange); // Ensure maxRange is at least minRange
        }
        
        int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        String initialPrompt = "A " + fishGrade + " " + fishToCatch.getItemName() + " is on the line! Guess its number (" + minRange + "-" + maxRange + ").";

        panelController.showFishingGuessingGame(initialPrompt, targetNumber, minRange, maxRange, maxAttempts, (OptionalInt gameResult) -> {
            // This is the LAMBDA CALLBACK executed when the guessing game UI finishes
            if (gameResult.isPresent()) {
                // Player guessed correctly!
                // int actualGuessedNumber = gameResult.getAsInt(); // We know it's the targetNumber
                panelController.showDialog(player.getName() + " successfully caught a " + fishGrade + " " + fishToCatch.getItemName() + "!");

                player.addAmountOfFishReeled();
                if (player.getAmountOfFishReeled() == 10) {
                    player.getInventory().addItemByName("SashimiRecipe", 1);
                    panelController.showDialog("You reel in your 10th fish and get an idea... Sashimi Recipe learned!");
                }
                if (!player.getEverPufferfish() && fishToCatch.getItemName().equals("Pufferfish")) {
                    player.wasEverPufferfish();
                    player.getInventory().addItemByName("FuguRecipe", 1);
                    panelController.showDialog("A dangerous catch! You carefully learn the Fugu Recipe!");
                }
                if (!player.getEverLegendaryFish() && fishGrade.equals("LEGENDARY")) {
                    player.wasEverLegendaryFish();
                    player.getInventory().addItemByName("TheLegendsOfSpakborRecipe", 1); // Assuming this name is correct
                    panelController.showDialog("A legendary catch! You've learned 'The Legends Of Spakbor' Recipe!");
                }
                player.getInventory().addItemByName(fishToCatch.getItemName(), 1); // Add fish to inventory

            } else {
                // Player failed to guess or cancelled
                panelController.showDialog(player.getName() + " couldn't reel in the " + fishToCatch.getItemName() + ". It got away!");
            }

            // Update any relevant UI after the fishing attempt
            if (farmController != null) {
                farmController.updateHotbar();
                // farmController.getPlayerController().updateEnergyDisplay(); // If energy was used
            }
        });
    }
}