package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

public class PointOfSaleActivity extends AppCompatActivity {
    private static final String TAG = "PointOfSaleActivity";
    private String mUserName, mItemName;
    private double mItemPrice;
    ArrayList<InventoryItem> menuItem = new ArrayList<>();
    private RecyclerView rvPOS;
    private POSAdapter mPOSAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        Log.d(TAG, "onCreate: Started.");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mUserName = intent.getStringExtra(LoginActivity.USER_NAME_TEXT);
        menuItem = this.getIntent().getExtras().getParcelableArrayList("InventoryItem");
//        InventoryItem inventoryItem = intent.getParcelableExtra("Inventory Item");
//        String mItemName = inventoryItem.getItemName();
//        double mItemPrice = inventoryItem.getItemPrice();

        rvPOS = findViewById(R.id.rvPOS);
        mPOSAdapter = new POSAdapter(this,menuItem);
  //      rvPOS.setLayoutManager(new GridLayoutManager(this,4));
        rvPOS.setAdapter(mPOSAdapter);

    }
}
