package com.examples.myreceipts;
/**
 * Create each itemName and itemPrice in the itemArray.
 */

public class InventoryItem {
    private String mItemName;
    private double mItemPrice;

    public InventoryItem(String newItem, double newPrice){
         this.mItemName = newItem;
         this.mItemPrice = newPrice;
    }
    public void setItemName(String itemName){
        this.mItemName = itemName;
    }
    public String getItemName(){
         return mItemName;
    }

    public void setItemPrice(double itemPrice){
        this.mItemPrice = itemPrice;
    }
    public double getItemPrice(){
        return mItemPrice;
    }

}
