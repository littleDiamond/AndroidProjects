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

    public String getDateTimeStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,   HH:mm:ss");
        return dateTime.format(formatter);
    }

    public String getDateStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    public LocalDateTime getDateTime() { return dateTime; }

    /*
    get a brief description of the order
     */
    public String getDescription() {
        final int maxDescriptionLength = 70;
        StringBuilder strBuilder = new StringBuilder();

        int currentLength = 0;
        for(SaleItem saleItem : order.getItems())
        {
            String itemDescription = saleItem.getDescription();
            if ( currentLength + itemDescription.length() < maxDescriptionLength )
            {
                if ( currentLength > 0 )
                {
                    strBuilder.append(", ");
                    currentLength += 2;
                }
                strBuilder.append(itemDescription);
                currentLength += itemDescription.length();
            }
            else
            {
                strBuilder.append("...");
                break;
            }
        }
        return strBuilder.toString();
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
