package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Dasco extends NPC {

    public Dasco() {
        super(
            "Dasco", //name
            new Point(10 * Constants.TILE_SIZE, 14 * Constants.TILE_SIZE), //location
            List.of("The Legends of Spakbor, Cooked Pig's Head, Wine, Fugu, Spakbor Salad"), //lovedItems
            List.of("Fish Sandwich, Fish Stew, Baguette, Fish n’ Chips"), //likedItems
            List.of("Legend, Grape, Cauliflower, Wheat, Pufferfish, Salmon") //hatedItems
        );
    }
}