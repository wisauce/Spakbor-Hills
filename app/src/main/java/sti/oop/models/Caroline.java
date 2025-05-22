package sti.oop.models;

import java.util.List;

public class Caroline extends NPC {

    public Caroline() {
        super(
            "Caroline", //name
            new Point(222, 222), //location
            List.of("Firewood, Coal"), //lovedItems
            List.of("Potato, Wheat"), //likedItems
            List.of("Hot Pepper") //hatedItems
        );
    }
}