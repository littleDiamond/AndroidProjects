package com.examples.myreceipts;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Objects;

public final class SaleItem implements Parcelable {
    InventoryItem inventoryItem;
    int quantity;

    public SaleItem(InventoryItem inventoryItem, int quantity) {
        this.inventoryItem = inventoryItem;
        this.quantity = quantity;
    }

    protected SaleItem(Parcel in) {
        inventoryItem = in.readParcelable(InventoryItem.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<SaleItem> CREATOR = new Creator<SaleItem>() {
        @Override
        public SaleItem createFromParcel(Parcel in) {
            return new SaleItem(in);
        }

        @Override
        public SaleItem[] newArray(int size) {
            return new SaleItem[size];
        }
    };

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return inventoryItem.getItemName().equals(saleItem.inventoryItem.getItemName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryItem.getItemName());
    }

    @Override
    public String toString() {
        return String.format("[Sale Item] Name : {%s} Price: {%.2f} Quantity: {%d}",
                inventoryItem.getItemName(), inventoryItem.getItemPrice(), quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(inventoryItem, flags);
        dest.writeInt(quantity);
    }
}
