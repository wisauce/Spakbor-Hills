package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Abigail extends NPC {
    
    public Abigail() {
        super(
            "Abigail", //name
            new Point(10 * Constants.TILE_SIZE, 10 * Constants.TILE_SIZE), //location
            List.of("Blueberry, Melon, Pumpkin, Grape, Cranberry"), //lovedItems
            List.of("Baguette, Pumpkin Pie, Wine"), //likedItems
            List.of("Hot Pepper, Cauliflower, Parsnip, Wheat") //hatedItems
        );
    }
}