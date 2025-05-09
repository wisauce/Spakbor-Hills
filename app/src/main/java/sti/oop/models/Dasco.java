package sti.oop.models;

import java.util.List;

public class Dasco extends NPC {

    public Dasco() {
        super(
            "Dasco", //name
            new Point(200, 0), //location
            List.of("The Legends of Spakbor, Cooked Pig's Head, Wine, Fugu, Spakbor Salad"), //lovedItems
            List.of("Fish Sandwich, Fish Stew, Baguette, Fish n’ Chips"), //likedItems
            List.of("Legend, Grape, Cauliflower, Wheat, Pufferfish, Salmon") //hatedItems
        );
    }
}