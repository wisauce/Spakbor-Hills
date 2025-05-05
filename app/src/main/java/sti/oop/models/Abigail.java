package sti.oop.models;

import java.util.List;

public class Abigail extends NPC {

    public Abigail() {
        super(
            "Abigail", //name
            new Point(0, 0), //location
            List.of("Blueberry, Melon, Pumpkin, Grape, Cranberry"), //lovedItems
            List.of("Baguette, Pumpkin Pie, Wine"), //likedItems
            List.of("Hot Pepper, Cauliflower, Parsnip, Wheat") //hatedItems
        );
    }
}