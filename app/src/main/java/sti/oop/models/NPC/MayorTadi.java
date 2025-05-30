package sti.oop.models.NPC;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Point;
import sti.oop.models.Item.Item;
import sti.oop.utils.Constants;


public class MayorTadi extends NPC {

    public MayorTadi() {
        super(
            "MayorTadi", //name
            List.of(
                ItemRegistry.createItem("Legend")
            ), //lovedItems

            List.of(
                ItemRegistry.createItem("Angler"),
                ItemRegistry.createItem("Crimsonfish"),
                ItemRegistry.createItem("Glacierfish")
            ), //likedItems

            getHatedItemsForMayorTadi(), //hatedItems
            "i love gemoy people. btw don't forget to get your free lunch! it's good for your health"
        );
    }

    private static List<Item> getHatedItemsForMayorTadi() {
        Set<String> allItemNames = ItemRegistry.getAllItemNames();

        Set<String> lovedItemMayorTadi = Set.of("Legend");
        Set<String> likedItemMayorTadi = Set.of("Angler", "Crimsonfish", "Glacierfish");

        return 
            allItemNames.stream()
            .filter(itemName -> !lovedItemMayorTadi.contains(itemName) && !likedItemMayorTadi.contains(itemName))
            .map(ItemRegistry::createItem)
            .collect(Collectors.toList());
    }
}