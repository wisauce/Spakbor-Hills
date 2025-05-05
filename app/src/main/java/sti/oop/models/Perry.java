package sti.oop.models;

import java.util.List;

public class Perry extends NPC {

    public Perry() {
        super(
            "Perry", //name
            new Point(0, 0), //location
            List.of("Cranberry, Blueberry"), //lovedItems
            List.of("Wine"), //likedItems
            List.of("FISH") //hatedItems
        );
    }
}