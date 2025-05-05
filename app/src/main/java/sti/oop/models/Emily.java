package sti.oop.models;

import java.util.List;

public class Emily extends NPC {

    public Emily() {
        super(
            "Emily", //name
            new Point(0, 0), //location
            List.of("Seeds"), //lovedItems
            List.of("Catfish, Salmon, Sardine"), //likedItems
            List.of("Coal, Wood") //hatedItems
        );
    }
}