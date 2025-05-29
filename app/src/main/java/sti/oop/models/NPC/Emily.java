package sti.oop.models.NPC;

import java.util.List;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Emily extends NPC {

    public Emily() {
        super(
            "Emily", //name
            new Point(10 * Constants.TILE_SIZE, 16 * Constants.TILE_SIZE), //location
            List.of(
                ItemRegistry.createItem("ParsnipSeeds"),
                ItemRegistry.createItem("CauliflowerSeeds"),
                ItemRegistry.createItem("PotatoSeeds"),
                ItemRegistry.createItem("WheatSeeds"),
                ItemRegistry.createItem("BlueberrySeeds"),
                ItemRegistry.createItem("TomatoSeeds"),
                ItemRegistry.createItem("HotPepperSeeds"),
                ItemRegistry.createItem("MelonSeeds"),
                ItemRegistry.createItem("CranberrySeeds"),
                ItemRegistry.createItem("PumpkinSeeds"),
                ItemRegistry.createItem("WheatSeeds"),
                ItemRegistry.createItem("GrapeSeeds")
            ), //loveditems
            
            List.of(
                ItemRegistry.createItem("Catfish"),
                ItemRegistry.createItem("Salmon"),
                ItemRegistry.createItem("Sardine")
                ), //likedItems

            List.of(
                ItemRegistry.createItem("Coal"),
                ItemRegistry.createItem("Firewood")
            ), //hatedItems
            "Sigh... I am thinking of getting a haircut at a Garut Barbershop but i don't know where"
        );
    }
}