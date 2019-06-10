package com.examples.myreceipts;

public final class CartItem {
    SaleItem saleItem;
    int totalAmount;

    public CartItem(SaleItem saleItem, int totalAmount) {
        this.saleItem = saleItem;
        this.totalAmount = totalAmount;
    }

    public SaleItem getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(SaleItem saleItem) {
        this.saleItem = saleItem;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotoalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
