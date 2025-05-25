package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;

public class Abigail extends NPC {
    
    public Abigail() {
        super(
            "Abigail", //name
            new Point(110, 110), //location
            List.of("Blueberry, Melon, Pumpkin, Grape, Cranberry"), //lovedItems
            List.of("Baguette, Pumpkin Pie, Wine"), //likedItems
            List.of("Hot Pepper, Cauliflower, Parsnip, Wheat") //hatedItems
        );
    }
}