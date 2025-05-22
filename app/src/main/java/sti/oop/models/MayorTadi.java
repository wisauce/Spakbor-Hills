package sti.oop.models;

import java.util.List;
import java.util.Collections;

public class MayorTadi extends NPC {

    public MayorTadi() {
        super(
            "MayorTadi", //name
            new Point(0, 0), //location
            List.of("Legend"), //lovedItems
            List.of("Angler, Crimsonfish, Glacierfish"), //likedItems
            Collections.emptyList() //hatedItems
        );
    }
}