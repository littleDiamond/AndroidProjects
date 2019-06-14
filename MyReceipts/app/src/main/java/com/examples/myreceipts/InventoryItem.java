package com.examples.myreceipts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Create each mItemName and mItemPrice in the itemArray.
 */

public class InventoryItem implements Parcelable {
    private String mItemName;
    private double mItemPrice;

    public InventoryItem(String newItem, double newPrice) {
        this.mItemName = newItem;
        this.mItemPrice = newPrice;
    }

    public InventoryItem(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        mItemName = in.readString();
        mItemPrice = in.readDouble();
    }

    public void setItemName(String itemName) {
        this.mItemName = itemName;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemPrice(double itemPrice) {
        this.mItemPrice = itemPrice;
    }

    public double getItemPrice() {
        return mItemPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return Double.compare(that.mItemPrice, mItemPrice) == 0 &&
                mItemName.equals(that.mItemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mItemName, mItemPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mItemName);
        dest.writeDouble(mItemPrice);
    }

    public static final Creator<InventoryItem> CREATOR = new Creator<InventoryItem>() {
        @Override
        public InventoryItem createFromParcel(Parcel in) {
            return new InventoryItem(in);
        }

        @Override
        public InventoryItem[] newArray(int size) {
            return new InventoryItem[size];
        }
    };
}
