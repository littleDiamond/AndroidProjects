package com.examples.myreceipts;

import android.os.Parcel;
import android.os.Parcelable;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("[Sale Item] Name : {%s} Price: {%.2f} Quantity: {%d}",
                inventoryItem.getItemName(), inventoryItem.getItemPrice(), quantity );
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
