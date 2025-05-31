package sti.oop.action;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;

import sti.oop.controllers.FarmController;
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
            return availableToday; 
        }
        for (Fish fish : allPossibleFishes) {
            if (fish.getSeason() == null || fish.getWeather() == null) continue;

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

    public void startInteractiveFishing(Player player, Fish fishToCatch, PanelController panelController, FarmController farmController, Action action) {
        if (fishToCatch == null) {
            panelController.showDialog("Hmm, nothing seems to be biting right now.");
            if (farmController != null) farmController.updateHotbar(); 
            return;
        }

        String fishGrade = fishToCatch.getGrade().toUpperCase();
        int minRange = 1;
        int maxRange = 10;  
        int maxAttempts = 7; 

        switch (fishGrade) {
            case "COMMON" -> {
                maxRange = 10;
                maxAttempts = 10;
            }
            case "REGULAR" -> {
                maxRange = 100; 
                maxAttempts = 10;
            }
            case "LEGENDARY" -> {
                maxRange = 500; 
                maxAttempts = 7;
            }
            default -> {
                System.out.println(fishToCatch.getItemName() + " fish grade: " + fishGrade + " is not recognized for difficulty scaling.");
                panelController.showDialog("An unusual fish is on the line! But its strength is unknown...");
                maxRange = 10;
                maxAttempts = 7;
                // return; 
            }
        }

        if (minRange > maxRange) {
            minRange = 1;
            maxRange = Math.max(minRange, maxRange); 
        }
        
        int targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        String initialPrompt = "A " + fishGrade + " " + fishToCatch.getItemName() + " is on the line! Guess its number (" + minRange + "-" + maxRange + ").";

        panelController.showFishingGuessingGame(initialPrompt, targetNumber, minRange, maxRange, maxAttempts, (OptionalInt gameResult) -> {
            if (gameResult.isPresent()) {
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
                    player.getInventory().addItemByName("TheLegendsOfSpakborRecipe", 1); 
                    panelController.showDialog("A legendary catch! You've learned 'The Legends Of Spakbor' Recipe!");
                }
                
                player.getInventory().addItemByName(fishToCatch.getItemName(), 1); 
            } else {
                panelController.showDialog(player.getName() + " couldn't reel in the " + fishToCatch.getItemName() + ". It got away!");
            }
            updateTimeFishing(farmController.getFarm());
            if (farmController != null) {
                action.closeFishingInterface();
                farmController.updateHotbar();
                // farmController.getPlayerController().updateEnergyDisplay();
            }
        });
    }

    private void updateTimeFishing(Farm farm) {
        int currentHour = farm.getInGameHour();
        int currentMinute = farm.getInGameMinute();
        String timeOfDay = farm.getTimeOfDay();

        int newMinute = currentMinute + 15;
        int newHour = currentHour;
        String newTimeOfDay = timeOfDay;

        if (newMinute >= 60) {
            newMinute -= 60;
            newHour++;
            if (newHour == 12 && timeOfDay.equals("AM")) {
            newTimeOfDay = "PM";
            }
            else if (newHour == 12 && timeOfDay.equals("PM")) {
            newTimeOfDay = "AM";
            }

            else if (newHour > 12) {
            newHour = 1;
            }
        }

        farm.setTime(newHour, newMinute);

        if (!newTimeOfDay.equals(timeOfDay)) {
            farm.setTimeOfDay(newTimeOfDay);
            if (timeOfDay.equals("PM") && newTimeOfDay.equals("AM")) {
            farm.nextDay();
            }
        }
    }

}