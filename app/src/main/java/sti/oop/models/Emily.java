package sti.oop.models;

import java.util.List;

public class Emily extends NPC {

    public Emily() {
        super(
            "Emily", //name
            new Point(333, 333), //location
            List.of("Parsnip Seeds, Cauliflower Seeds, Potato Seeds, Wheat Seeds, Blueberry Seeds, Tomato Seeds, Hot Pepper Seeds, Melon Seeds, Cranberry Seeds, Pumpkin Seeds, Wheat Seeds, Grape Seeds"), //lovedItems
            List.of("Catfish, Salmon, Sardine"), //likedItems
            List.of("Coal, Wood") //hatedItems
        );
    }
}