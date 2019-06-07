package com.examples.myreceipts;

public final class SaleItem {
    InventoryItem inventoryItem;
    int quantity;

    public SaleItem(InventoryItem inventoryItem, int quantity) {
        this.inventoryItem = inventoryItem;
        this.quantity = quantity;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("[Sale Item] Name : {%s} Price: {%.2f} Quantity: {%d}",
                inventoryItem.getItemName(), inventoryItem.getItemPrice(), quantity );
    }
}
