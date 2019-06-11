package com.examples.myreceipts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public final class ShoppingCart implements Parcelable {

    public ShoppingCart()
    {

    }

    protected ShoppingCart(Parcel in) {
        shoppingList = in.createTypedArrayList(SaleItem.CREATOR);
        saleTotal = in.readInt();
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

    public double getSaleTotal() {
        return saleTotal;
    }

    public void addItem(SaleItem newItem)
    {
        shoppingList.add(newItem);

        // add the item price to total
        saleTotal += newItem.getInventoryItem().getItemPrice() * newItem.getQuantity();
    }

    public SaleItem getItem(int positionInCart)
    {
        return shoppingList.get(positionInCart);
    }

    public int getItemCount()
    {
        return shoppingList.size();
    }

    public void removeItem(SaleItem newItem)
    {
        shoppingList.remove(newItem);

        // deduct the item price from total
        saleTotal -= newItem.getInventoryItem().getItemPrice() * newItem.getQuantity();
    }

    public void clearAllItems()
    {
        shoppingList.clear();

        saleTotal = 0;
    }

    private ArrayList<SaleItem> shoppingList = new ArrayList<>();
    private double saleTotal = 0;


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
}
