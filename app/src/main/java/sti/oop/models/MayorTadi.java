package sti.oop.models;

import java.util.List;

public class MayorTadi extends NPC {

    public MayorTadi() {
        super(
            "MayorTadi", //name
            new Point(0, 0), //location
            List.of("Legend"), //lovedItems
            List.of("Angler, Crimsonfish, Glacierfish"), //likedItems
            List.of("Else") //hatedItems
        );
    }
}