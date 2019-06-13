package com.examples.myreceipts;

import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryItemTest {

    final double tolerance = 1e-9;

    @Test
    public void setItemName() {
        InventoryItem newItem = new InventoryItem("item1", 12.0);

        assertEquals(newItem.getItemName(), "item1");

        newItem.setItemName("item2");

        assertEquals(newItem.getItemName(), "item2");
    }

    @Test
    public void getItemName() {
        InventoryItem newItem1 = new InventoryItem("item1", 12.0);
        assertEquals(newItem1.getItemName(), "item1");

        InventoryItem newItem2 = new InventoryItem("", 12.0);
        assertEquals(newItem2.getItemName(), "");
    }

    @Test
    public void setItemPrice() {
        InventoryItem newItem1 = new InventoryItem("item1", 12.0);
        assertEquals(newItem1.getItemPrice(), 12.0, tolerance);

        newItem1.setItemPrice(1000.0);
        assertEquals(newItem1.getItemPrice(), 1000.0, tolerance);

    }

    @Test
    public void getItemPrice() {
        InventoryItem newItem1 = new InventoryItem("item1", 12.0);
        assertEquals(newItem1.getItemPrice(), 12.0, tolerance);

        InventoryItem newItem2 = new InventoryItem("item2", 5.0);
        assertEquals(newItem2.getItemPrice(), 5.0, tolerance);
    }
}