package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;
import sti.oop.utils.Constants;
import sti.oop.models.ItemRegistry;

public class Perry extends NPC {

    public Perry() {
        super(
            "Perry", //name
            new Point(10 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE), //location
            List.of(
                ItemRegistry.createItem("Cranberry"), 
                ItemRegistry.createItem("Blueberry")
            ), //lovedItems
            List.of(
                ItemRegistry.createItem("Wine")
            ), //likedItems
            List.of(
                ItemRegistry.createItem("Bullhead"),
                ItemRegistry.createItem("Carp"),
                ItemRegistry.createItem("Chub"),
                ItemRegistry.createItem("LargemouthBass"),
                ItemRegistry.createItem("RainbowTrout"),
                ItemRegistry.createItem("Sturgeon"),
                ItemRegistry.createItem("MidnightCarp"),
                ItemRegistry.createItem("Flounder"),
                ItemRegistry.createItem("Halibut"),
                ItemRegistry.createItem("Octopus"),
                ItemRegistry.createItem("Pufferfish"),
                ItemRegistry.createItem("Sardine"),
                ItemRegistry.createItem("SuperCucumber"),
                ItemRegistry.createItem("Catfish"),
                ItemRegistry.createItem("Salmon"),
                ItemRegistry.createItem("Angler"),
                ItemRegistry.createItem("Crimsonfish"),
                ItemRegistry.createItem("Glacierfish"),
                ItemRegistry.createItem("Legend")
            ), //hatedItems
            "Aksually.... Platypus is a mammal and not a vertebrate 🤓☝️"
        );
    }
}