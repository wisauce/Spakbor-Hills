package sti.oop.models.NPC;

import java.util.List;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Point;
import sti.oop.utils.Constants;


public class Abigail extends NPC {
    
    public Abigail() {
        super(
            "Abigail", //name
            List.of(
                ItemRegistry.createItem("Blueberry"),
                ItemRegistry.createItem("Melon"),
                ItemRegistry.createItem("Pumpkin"),
                ItemRegistry.createItem("Grape"),
                ItemRegistry.createItem("Cranberry")
            ), //lovedItems

            List.of(
                ItemRegistry.createItem("Baguette"),
                ItemRegistry.createItem("PumpkinPie"),
                ItemRegistry.createItem("Wine")
            ), //likedItems
            
            List.of(
                ItemRegistry.createItem("HotPepper"),
                ItemRegistry.createItem("Cauliflower"),
                ItemRegistry.createItem("Parsnip"),
                ItemRegistry.createItem("Wheat")
            ), //hatedItems
            List.of("hi, i owns a store btw. You should check it out!", "What would you want to buy?")
        );
    }
}