package sti.oop.models.item;

public class Item {
    private String itemName;
    private String itemType;
    private String itemID;

    public Item(String itemName, String itemType) {
        this.itemName = itemName;
        this.itemType = itemType;

        itemID = itemType + "_" + itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }
    
    public String getItemID() {
        return itemID;
    }
}