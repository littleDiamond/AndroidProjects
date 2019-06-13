package com.examples.myreceipts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {

    public Receipt(String userName, ShoppingCart order) {
        this.userName = userName;
        this.dateTime = LocalDateTime.now();
        this.order = order;

        setReceiptID(++lastReceiptID);
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,   HH:mm:ss");
        return  dateTime.format(formatter);
    }

    public ShoppingCart getOrder() {
        return order;
    }

    public void setOrder(ShoppingCart order) {
        this.order = order;
    }

    private int receiptID;
    private String userName;
    private LocalDateTime dateTime;
    private ShoppingCart order;

    public static int lastReceiptID = 0;

}
