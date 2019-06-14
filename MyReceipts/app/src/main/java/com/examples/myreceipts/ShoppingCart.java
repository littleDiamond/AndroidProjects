package com.examples.myreceipts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public final class ShoppingCart implements Parcelable {

    public ShoppingCart() {
    }

    protected ShoppingCart(Parcel in) {
        shoppingList = in.createTypedArrayList(SaleItem.CREATOR);
        saleTotal = in.readDouble();
    }

    public double getSaleTotal() {
        return saleTotal;
    }

    public void addItem(SaleItem newItem) {

        int newQuantity = newItem.getQuantity();

        // Returns the index of the first occurrence of the specified element in this list,
        // or -1 if this list does not contain the element.Use equals() on InventoryItem class
        int existingIndex = shoppingList.indexOf(newItem);
        boolean hasExistingItem = existingIndex >= 0;

        if (hasExistingItem) {
            SaleItem existingItem = shoppingList.get(existingIndex);
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
        } else {
            shoppingList.add(newItem);
        }

        // add the item price to total
        saleTotal += newItem.getInventoryItem().getItemPrice() * newQuantity;
    }

    public void removeItem(int positionInCart) {
        if (positionInCart < 0 || positionInCart >= getItemCount()) {
            return;
        }
        SaleItem oldItem = shoppingList.remove(positionInCart);

        // deduct the item price from total
        saleTotal -= oldItem.getInventoryItem().getItemPrice() * oldItem.getQuantity();
    }

    public void clearAllItems() {
        shoppingList.clear();
        saleTotal = 0;
    }

    public void updateSaleTotal(SaleItem changedItem, int oldQuantity, int newQuantity) {
        // add the item sale difference to total to update the sale total
        double price = changedItem.getInventoryItem().getItemPrice();
        saleTotal += price * (newQuantity - oldQuantity);
    }

    public SaleItem getItem(int positionInCart) {
        if (positionInCart < 0 || positionInCart >= getItemCount()) {
            return null;
        }
        return shoppingList.get(positionInCart);
    }

    public int getItemCount() {
        return shoppingList.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(shoppingList);
        dest.writeDouble(saleTotal);
    }

    @Override
    public String toString() {
        String shoppingCartStr = String.format("ShoppingCart{\n shoppingList=%s , saleTotal=%.2f };",
                shoppingList.toString(), saleTotal);
        return shoppingCartStr;
    }

    public static final Creator<ShoppingCart> CREATOR = new Creator<ShoppingCart>() {
        @Override
        public ShoppingCart createFromParcel(Parcel in) {
            return new ShoppingCart(in);
        }

        @Override
        public ShoppingCart[] newArray(int size) {
            return new ShoppingCart[size];
        }
    };

    public ArrayList<SaleItem> getItems() {
        return shoppingList;
    }

    private ArrayList<SaleItem> shoppingList = new ArrayList<>();
    private double saleTotal = 0;
}
