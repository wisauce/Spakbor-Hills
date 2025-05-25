package sti.oop.models.NPC;

import java.util.List;
import sti.oop.models.Point;
import sti.oop.utils.Constants;

public class Emily extends NPC {

    public Emily() {
        super(
            "Emily", //name
            new Point(10 * Constants.TILE_SIZE, 16 * Constants.TILE_SIZE), //location
            List.of("Parsnip Seeds, Cauliflower Seeds, Potato Seeds, Wheat Seeds, Blueberry Seeds, Tomato Seeds, Hot Pepper Seeds, Melon Seeds, Cranberry Seeds, Pumpkin Seeds, Wheat Seeds, Grape Seeds"), //lovedItems
            List.of("Catfish, Salmon, Sardine"), //likedItems
            List.of("Coal, Wood") //hatedItems
        );
    }
}