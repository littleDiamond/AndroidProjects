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
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PointOfSaleActivity extends AppCompatActivity {
    private static final String TAG = "PointOfSaleActivity";

    ArrayList<SaleItem> saleItems = new ArrayList<>();
    private RecyclerView rvPOS;
    private RecyclerView.LayoutManager manager;
    private POSAdapter mPOSAdapter;
    private Button btnNewOrder;

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

        // populate the grid with inventory items
        ArrayList<InventoryItem> inventoryItems = getIntent().getExtras().getParcelableArrayList("InventoryItem");
        for( InventoryItem item  : inventoryItems ) {
            saleItems.add( new SaleItem(item, 1));
        }
        mPOSAdapter = new POSAdapter(this,saleItems);
        rvPOS.setAdapter(mPOSAdapter);
//        mPOSAdapter.setOnItemClickListener(new POSAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                SaleItem selectedItem =
//                getSelectedItem(position, saleItems);
//            }
//        });

        /**
         * Tap new order and clear all the order data that has create in case user cancels the order
         */
        btnNewOrder = findViewById(R.id.btnNewOrder);
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    } // end of onCreate

//    public final void getSelectedItem (int position,SaleItem selecteditem) {
//        saleItems.get(position).getInventoryItem();
//        saleItems.get(position).getQuantity();
//        mPOSAdapter.notifyItemInserted(position);
//    }
//
//    public void removeItem (int position) {
//        saleItems.remove(position);
//        mPOSAdapter.notifyItemRemoved(position);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pos_cart_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                cartIntent.putExtra("ShoppingCart", mPOSAdapter.getShoppingCart() );
                startActivity(cartIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
