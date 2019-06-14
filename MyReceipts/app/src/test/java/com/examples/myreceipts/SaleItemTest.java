package com.examples.myreceipts;

import org.junit.Test;

import static org.junit.Assert.*;

public class SaleItemTest {

    @Test
    public void getInventoryItem() {
        SaleItem saleItem = new SaleItem(new InventoryItem("item1", 1.23), 1);
        InventoryItem inventoryItem = new InventoryItem("item1", 1.23);
        InventoryItem anotherItem = new InventoryItem("item2", 4.56);
        InventoryItem anotherItem2 = new InventoryItem("item2", 1000.0);


        assertEquals(saleItem.getInventoryItem(), inventoryItem);
        assertNotEquals(saleItem.getInventoryItem(), anotherItem);
        assertNotEquals(saleItem.getInventoryItem(), anotherItem2);
    }

    @Test
    public void setInventoryItem() {
        SaleItem saleItem = new SaleItem(new InventoryItem("item1", 1.23), 1);
        InventoryItem anotherItem = new InventoryItem("item2", 4.56);

        //test the original item
        assertEquals(saleItem.getInventoryItem(), new InventoryItem("item1", 1.23));

        // update the inventory item
        saleItem.setInventoryItem(anotherItem);

        // check the new item
        assertEquals(saleItem.getInventoryItem(), new InventoryItem("item2", 4.56));

        // another check for the new item
        assertNotEquals(saleItem.getInventoryItem(), new InventoryItem("item2", 999));
    }

    @Test
    public void getQuantity() {
        SaleItem saleItem = new SaleItem(new InventoryItem("item1", 1.23), 50);
        assertEquals(saleItem.getQuantity(), 50);

        SaleItem saleItem2 = new SaleItem(new InventoryItem("item2", 1.23), 100);

        assertEquals(saleItem2.getQuantity(), 100);
    }

    @Test
    public void setQuantity() {
        SaleItem saleItem = new SaleItem(new InventoryItem("item1", 1.23), 50);
        assertEquals(saleItem.getQuantity(), 50);

        saleItem.setQuantity(200);
        assertEquals(saleItem.getQuantity(), 200);
    }

    @Test
    public void equals() {
        SaleItem saleItem1 = new SaleItem(new InventoryItem("item1", 1.23), 1);
        SaleItem saleItem2 = new SaleItem(new InventoryItem("item2", 4.56), 2);
        SaleItem saleItem3 = new SaleItem(new InventoryItem("item2", 4.56), 4);
        SaleItem saleItem4 = new SaleItem(new InventoryItem("item4", 4.56), 10);
        SaleItem saleItem5 = new SaleItem(new InventoryItem("item5", 20), 10);

        // different names
        assertNotEquals(saleItem1, saleItem2);

        // same name
        assertEquals(saleItem2, saleItem3);

        // same price
        assertNotEquals(saleItem3, saleItem4);

        // same quantity
        assertNotEquals(saleItem4, saleItem5);

    }
}