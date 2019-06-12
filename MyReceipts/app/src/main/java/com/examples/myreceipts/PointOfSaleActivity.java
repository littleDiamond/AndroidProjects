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
import android.widget.Toast;

import java.util.ArrayList;

public class PointOfSaleActivity extends AppCompatActivity {
    private static final String TAG = "PointOfSaleActivity";

    ArrayList<SaleItem> saleItems = new ArrayList<>();
    private RecyclerView rvPOS;
    private RecyclerView.LayoutManager manager;
    private POSAdapter mPOSAdapter;
    private Button btnNewOrder;
    ShoppingCart shoppingCart;

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

        /**
         * Tap new order and clear all the order data that has create in case user cancels the order
         */
        btnNewOrder = findViewById(R.id.btnNewOrder);
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCart.clearAllItems();
                Toast.makeText(getApplicationContext(),
                        "clear all items",Toast.LENGTH_SHORT ).show();
            }
        });
    } // end of onCreate

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
                if(mPOSAdapter.getShoppingCart().equals("")){
                    Toast.makeText(PointOfSaleActivity.this,"Nothing in the cart",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Intent cartIntent = new Intent(this, CartActivity.class);
                    cartIntent.putExtra("ShoppingCart", mPOSAdapter.getShoppingCart() );
                    startActivityForResult(cartIntent,1);
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getExtras() != null) {

                ShoppingCart selectedItem = data.getExtras().getParcelable("ShoppingCart");
            }
            if (resultCode == RESULT_CANCELED) {
               Toast.makeText(PointOfSaleActivity.this,"Data lost",
                       Toast.LENGTH_SHORT).show();
          }

    }
}// end
