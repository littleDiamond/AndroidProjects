package com.examples.myreceipts;
/**
 * Create each itemName and itemPrice in the itemArray.
 */

public class InventoryItem {
    private String itemName;
    private double itemPrice;

    public InventoryItem(String newItem, double newPrice){
         this.itemName = newItem;
         this.itemPrice = newPrice;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public String getItemName(){
         return itemName;
    }

    public void setItemPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }
    public double getItemPrice(){
        return itemPrice;
    }

}
