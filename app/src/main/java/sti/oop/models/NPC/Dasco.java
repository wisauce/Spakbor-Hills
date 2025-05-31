package sti.oop.models.NPC;

import java.util.List;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Dasco extends NPC {

    public Dasco() {
        super(
            "Dasco", //name
            List.of(
                ItemRegistry.createItem("TheLegendsOfSpakbor"),
                ItemRegistry.createItem("CookedPigsHead"),
                ItemRegistry.createItem("Wine"),
                ItemRegistry.createItem("Fugu"),
                ItemRegistry.createItem("SpakborSalad")
            ), //lovedItems
            
            List.of(
                ItemRegistry.createItem("FishSandwich"),
                ItemRegistry.createItem("FishStew"),
                ItemRegistry.createItem("Baguette"),
                ItemRegistry.createItem("FishnChips")
            ), //likedItems
            
            List.of(
                ItemRegistry.createItem("Legend"),
                ItemRegistry.createItem("Grape"),
                ItemRegistry.createItem("Cauliflower"),
                ItemRegistry.createItem("Wheat"),
                ItemRegistry.createItem("Pufferfish"),
                ItemRegistry.createItem("Salmon")
            ), //hatedItems
            List.of("LETSSS GOOOOO GAMBLINGGGG 🤑🤑🤑", "I am the GOD of Gambiling", "PENTA INFO", "Masa Iya Ga Masang?!")
        );
    }
}