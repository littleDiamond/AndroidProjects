package com.examples.myreceipts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryListActivity extends AppCompatActivity {
    private ListView itemList;
    private EditText item;
    private EditText price;
    private Button insert;
    private static final String TAG = "InventoryListActivity";
    public static final String PREFS_NAME = "PreferenceFile";
    public static final String ITEM_LIST = "Items";
    private ItemArrayAdapter adapter;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Log.d(TAG, "onCreate: Started.");

        Intent intent = getIntent();
        userName = intent.getStringExtra(MainActivity.USER_NAME_TEXT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        item = findViewById(R.id.add_item);
        price = findViewById(R.id.add_price);

        itemList = findViewById(R.id.item_list);

        ArrayList<InventoryItem> existingData = null;

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String itemsInJson = settings.getString(ITEM_LIST, "");

        if ( itemsInJson.isEmpty() )
        {
            existingData = new ArrayList<>();
        }
        else
        {
            Gson gson = new Gson();
            InventoryItem[] items = gson.fromJson(itemsInJson, InventoryItem[].class);
            existingData = new ArrayList<>(Arrays.asList(items));
        }

        // create the adapter to convert the array to views

        adapter = new ItemArrayAdapter(InventoryListActivity.this, existingData);
        itemList.setAdapter(adapter); //attach the adapter to a ListView

        insert = findViewById(R.id.btn_insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewItem();
            }
        });
    }// end of onCreate method

    private void insertNewItem() {
        // add item to adapter
        InventoryItem inventoryItem = new InventoryItem(item.getText().toString(),
                Double.parseDouble(price.getText().toString()));
        adapter.add(inventoryItem);

        // clear the EditText field for user to input new data.
        item.setText("");
        price.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();


        // save the inventory list to user preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        // get the list of items from list view
        ItemArrayAdapter listToSave = (ItemArrayAdapter) itemList.getAdapter();
        ArrayList<InventoryItem> allItems = listToSave.getAllItems();

        Gson gson = new Gson();
        String jsonString = gson.toJson(allItems.toArray(new InventoryItem[allItems.size()]));

        editor.putString(ITEM_LIST, jsonString);

        // Commit the edits!
        editor.commit();
    }



}
