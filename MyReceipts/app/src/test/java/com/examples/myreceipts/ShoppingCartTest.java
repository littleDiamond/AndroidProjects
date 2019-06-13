package com.examples.myreceipts;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShoppingCartTest {

    final double tolerance = 1e-9;

    @Test
    public void getSaleTotal() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new SaleItem( new InventoryItem("item1", 1.23), 1));
        cart.addItem(new SaleItem( new InventoryItem("item2", 1.34), 2));

        // test the sale total calculation
        assertEquals(cart.getSaleTotal(), 3.91, tolerance);
    }

    @Test
    public void addItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new SaleItem( new InventoryItem("item1", 1.23), 1));

        // add a new item
        assertEquals(cart.getItemCount(), 1);

        cart.addItem(new SaleItem( new InventoryItem("item2", 1.23), 2));

        // add another new item
        assertEquals(cart.getItemCount(), 2);

        cart.addItem(new SaleItem( new InventoryItem("item2", 1.23), 4));

        // add an existing with new quantity, merge with existing one
        assertEquals(cart.getItemCount(), 2);
    }

    @Test
    public void removeItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new SaleItem( new InventoryItem("item1", 1.23), 1));
        cart.addItem(new SaleItem( new InventoryItem("item2", 1.23), 2));

        assertEquals(cart.getItemCount(), 2);

        // remove the first item
        cart.removeItem(0);

        assertEquals(cart.getItemCount(), 1);

        // remove the item with out-of-bound index
        cart.removeItem(123);

        assertEquals(cart.getItemCount(), 1);

        // remove the item with out-of-bound index
        cart.removeItem(-1);

        assertEquals(cart.getItemCount(), 1);

        // remove the first item
        cart.removeItem(0);

        assertEquals(cart.getItemCount(), 0);

        // remove the item with an empty cart
        cart.removeItem(0);

        assertEquals(cart.getItemCount(), 0);
    }

    @Test
    public void clearAllItems() {

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new SaleItem( new InventoryItem("item1", 1.23), 1));
        cart.addItem(new SaleItem( new InventoryItem("item2", 1.23), 2));

        cart.clearAllItems();

        assertEquals(cart.getItemCount(), 0);
    }

    @Test
    public void getItem() {

        ShoppingCart cart = new ShoppingCart();
        SaleItem item1 = new SaleItem( new InventoryItem("item1", 1.23), 1);
        cart.addItem(item1);
        SaleItem item2 = new SaleItem( new InventoryItem("item2", 1.23), 2);
        cart.addItem(item2);

        assertEquals(cart.getItem(0), item1);

        assertEquals(cart.getItem(1), item2);

        assertEquals(cart.getItem(5), null);
    }

    @Test
    public void getItemCount() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new SaleItem( new InventoryItem("item1", 1.23), 1));

        assertEquals(cart.getItemCount(), 1);

        cart.addItem(new SaleItem( new InventoryItem("item2", 1.23), 2));

        assertEquals(cart.getItemCount(), 2);

        cart.addItem(new SaleItem( new InventoryItem("item2", 1.23), 2));

        assertEquals(cart.getItemCount(), 2);

        cart.addItem(new SaleItem( new InventoryItem("item3", 1.23), 4));

        assertEquals(cart.getItemCount(), 3);

    }

    @Test
    public void getItems() {
        ShoppingCart cart = new ShoppingCart();
        ArrayList<SaleItem> newItems = new ArrayList<>();
        for(int i=1; i <= 10; ++i)
        {
            SaleItem newItem = new SaleItem( new InventoryItem(String.format("item%d", i), 2.5 * i ), i);
            newItems.add(newItem);
            cart.addItem(newItem);
        }

        assertEquals( cart.getItems(), newItems );

        newItems.remove(0);

        assertNotEquals( cart.getItems(), newItems );
    }
}