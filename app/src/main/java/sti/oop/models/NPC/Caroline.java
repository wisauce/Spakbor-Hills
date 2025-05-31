package sti.oop.models.NPC;


import java.util.List;
import sti.oop.models.ItemRegistry;

import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Caroline extends NPC {

    public Caroline() {
        super(
            "Caroline", //name
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
            ), //hatedItems
            List.of("do you know that tung tung tung sahur is my cousin?", "I am the God of Carpenter", "Kang Mebel Nih Bos, OTW Presiden")
        );
    }
}