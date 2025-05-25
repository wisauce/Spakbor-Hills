package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

import java.util.Collections;

public class MayorTadi extends NPC {

    public MayorTadi() {
        super(
            "MayorTadi", //name
            new Point(10 * Constants.TILE_SIZE, 18 * Constants.TILE_SIZE), //location
            List.of("Legend"), //lovedItems
            List.of("Angler, Crimsonfish, Glacierfish"), //likedItems
            Collections.emptyList() //hatedItems
        );
    }
}