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
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventoryListActivity extends AppCompatActivity {
    private ListView itemList;
    private EditText mTextItem, mTextPrice;
    private Button mBtnInsert, mBtnConfirm;
    private static final String TAG = "InventoryListActivity";
    public static final String PREFS_NAME = "PreferenceFile";
    public static final String USER_DATA = "USER_DATA";
    private ItemArrayAdapter adapter;
    private String userName;
    private Map<String, InventoryItem[]> userDataMap = new HashMap<String, InventoryItem[]>();// a map between user name and user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Log.d(TAG, "onCreate: Started.");

        Intent intent = getIntent();
        userName = intent.getStringExtra(LoginActivity.USER_NAME_TEXT);
        TextView mTextHelloUser = findViewById(R.id.tvHelloUser);
        mTextHelloUser.setText(userName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mTextItem = findViewById(R.id.etAddItem);
        mTextPrice = findViewById(R.id.etAddPrice);
        itemList = findViewById(R.id.lvItemList);
        ArrayList<InventoryItem> existingData = new ArrayList<>();

        // read save user data from preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String itemsInJson = settings.getString(USER_DATA, "");

        // if we have existing user data
        if ( !itemsInJson.isEmpty() ) {
            Gson gson = new Gson();  // Deserialize user data from json string
            userDataMap = gson.fromJson(itemsInJson, new TypeToken<Map<String,
                    InventoryItem[]>>() {}.getType());
            // find the data specific to the current user
            if ( userDataMap != null ) {
                InventoryItem[] itemList = userDataMap.get(userName);
                if ( itemList != null && itemList.length > 0 ) {
                    existingData = new ArrayList<>(Arrays.asList(itemList));
                }
            }
        }
        // create the adapter to convert the array to views
        adapter = new ItemArrayAdapter(InventoryListActivity.this, existingData);
        itemList.setAdapter(adapter); //attach the adapter to a ListView

        mBtnInsert = findViewById(R.id.btnInsert);
        mBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewItem();
            }
        });
        mBtnConfirm = findViewById(R.id.btnNext);
        mBtnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(InventoryListActivity.this,
                                                        PointOfSaleActivity.class);
                startActivity(menuIntent);
            }
        });
    }// end of onCreate method

    private void insertNewItem() {
        // add mTextItem to adapter
        InventoryItem inventoryItem = new InventoryItem(mTextItem.getText().toString(),
                                 Double.parseDouble(mTextPrice.getText().toString()));
        adapter.add(inventoryItem);

        // clear the EditText field for user to input new data.
        mTextItem.setText("");
        mTextPrice.setText("");
    }

    // TODO: 5/04/2019  Add delete mTextItem feature

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
        userDataMap.put(userName, allItems.toArray(new InventoryItem[allItems.size()]));
        String jsonString = gson.toJson(userDataMap);
        editor.putString(USER_DATA, jsonString);

        editor.commit();    // Commit the edits!
    }
}
