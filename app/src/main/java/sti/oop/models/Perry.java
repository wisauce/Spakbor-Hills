package sti.oop.models;

import java.util.List;

public class Perry extends NPC {

    public Perry() {
        super(
            "Perry", //name
            new Point(300, 0), //location
            List.of("Cranberry, Blueberry"), //lovedItems
            List.of("Wine"), //likedItems
            List.of("Bullhead, Carp, Chub, Largemouth Bass, Rainbow Trout, Sturgeon, Midnight Carp, Flounder, Halibut, Octopus, Pufferfish, Sardine, Super Cucumber, Catfish, Salmon, Angler, Crimsonfish, Glacierfish, Legend") //hatedItems
        );
    }
}