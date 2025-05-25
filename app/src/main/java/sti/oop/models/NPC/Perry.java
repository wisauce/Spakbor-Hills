package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Perry extends NPC {

    public Perry() {
        super(
            "Perry", //name
            new Point(10 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE), //location
            List.of("Cranberry, Blueberry"), //lovedItems
            List.of("Wine"), //likedItems
            List.of("Bullhead, Carp, Chub, Largemouth Bass, Rainbow Trout, Sturgeon, Midnight Carp, Flounder, Halibut, Octopus, Pufferfish, Sardine, Super Cucumber, Catfish, Salmon, Angler, Crimsonfish, Glacierfish, Legend") //hatedItems
        );
    }
}