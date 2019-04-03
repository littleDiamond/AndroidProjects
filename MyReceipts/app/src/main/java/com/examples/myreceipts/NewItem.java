package com.examples.myreceipts;


/**
 * Create each item and price in the itemArray.
 */

public class NewItem {
    private String addItem;
    private double addPrice;

    public NewItem(String addItem, double addPrice){
         this.addItem = addItem;
         this.addPrice = addPrice;
    }
    public void setAddItem(String addItem){
        this.addItem = addItem;
    }
     public String getAddItem(){
         return addItem;
     }
    public void setAddPrice(double addPrice){
        this.addPrice = addPrice;
    }
    public double getAddPrice(){
        return addPrice;
    }

}
