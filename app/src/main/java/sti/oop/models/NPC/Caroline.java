package sti.oop.models.NPC;


import java.util.List;
import sti.oop.models.ItemRegistry;

import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Caroline extends NPC {

    public Caroline() {
        super(
            "Caroline", //name
            new Point(10 * Constants.TILE_SIZE, 12 * Constants.TILE_SIZE), //location
            List.of(
                ItemRegistry.createItem("Firewood"),
                ItemRegistry.createItem("Coal")
            ), //lovedItems

            List.of(
                ItemRegistry.createItem("Potato"),
                ItemRegistry.createItem("Wheat")
            ), //likedItems
            
            List.of(
                ItemRegistry.createItem("HotPepper")
            ) //hatedItems
        );
    }
}