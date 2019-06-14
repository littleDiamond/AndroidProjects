package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class PointOfSaleActivity extends AppCompatActivity {
    private static final String TAG = "PointOfSaleActivity";

    ArrayList<SaleItem> saleItems = new ArrayList<>();
    private RecyclerView rvPOS;
    private RecyclerView.LayoutManager manager;
    private POSAdapter mPOSAdapter;
    private ShoppingCart updatedShoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        Log.d(TAG, "onCreate: Started.");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // setup the grid layout
        rvPOS = findViewById(R.id.rvPOS);
        manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rvPOS.setLayoutManager(manager);

        MyReceiptsApplication app = (MyReceiptsApplication)getApplication();

        // populate the grid with inventory items
        ArrayList<InventoryItem> inventoryItems = app.getUserInventoryItems();

        for (InventoryItem item : inventoryItems) {
            saleItems.add(new SaleItem(item, 1));
        }
        mPOSAdapter = new POSAdapter(this, saleItems);
        rvPOS.setAdapter(mPOSAdapter);

    } // end of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pos_cart_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.cart_list:

                // pass the updated shopping cart if we have any
                // otherwise, pass the current shopping cart in the POS activity
                Intent cartIntent = new Intent(this, CartActivity.class);
                cartIntent.putExtra("ShoppingCart", mPOSAdapter.getShoppingCart());
                //startActivityForResult(cartIntent, 1);
                startActivity(cartIntent);
                return true;

            case R.id.new_order:

                //Tap new order to clear all the order data that has created
                // for user wants to cancel the order
                mPOSAdapter.clearShoppingCart();
                Toast.makeText(getApplicationContext(),
                        "clear all items", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                // update the adapter with updated shopping cart data from CartActivity
                updatedShoppingCart = data.getExtras().getParcelable("ShoppingCart");
                mPOSAdapter.updateShoppingCart(updatedShoppingCart);
            }
            else
            {
                mPOSAdapter.clearShoppingCart();
            }
        }
    }
}// end
