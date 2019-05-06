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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventoryListActivity extends AppCompatActivity {
    private ListView itemList;
    private EditText mTextItem, mTextPrice;
    private Button mBtnAdd, mBtnDelete, mBtnUpdate, mBtnConfirm;
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
        itemList.setOnClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                
        } );

        //Add single item
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        //Update single item
        mBtnUpdate = findViewById(R.id.btnUpdate);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });
        //Delete single item
        mBtnDelete = findViewById(R.id.btnDelete);
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
        //Open next activity and pass data
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

    private void addNewItem() {
        // add mTextItem to adapter
        InventoryItem inventoryItem = new InventoryItem(mTextItem.getText().toString(),
                                 Double.parseDouble(mTextPrice.getText().toString()));
        if( !inventoryItem.getItemName().isEmpty() && inventoryItem.getItemName().length() >0 ){
            adapter.add(inventoryItem);      //Add item
            adapter.notifyDataSetChanged(); //refresh

            mTextItem.setText("");          // clear the EditText field
            mTextPrice.setText("");
            Toast.makeText(getApplicationContext(), "Add"
                    + inventoryItem.getItemName(),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Nothing is added",
                                                Toast.LENGTH_SHORT).show();
        }
    }

    private void updateItem(){
        InventoryItem inventoryItem = new InventoryItem(mTextItem.getText().toString(),
                Double.parseDouble(mTextPrice.getText().toString()));
        int position = itemList.getCheckedItemPosition();

        if( !inventoryItem.getItemName().isEmpty() && inventoryItem.getItemName().length() >0 ){
            adapter.remove(inventoryItem);          //remove item
            adapter.insert(inventoryItem,position); //add again
            adapter.notifyDataSetChanged();         //refresh

            Toast.makeText(getApplicationContext(), "Update"
                    + inventoryItem.getItemName(),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Nothing is updated",
                                                Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteItem(){
        InventoryItem inventoryItem = new InventoryItem(mTextItem.getText().toString(),
                Double.parseDouble(mTextPrice.getText().toString()));
        int position = itemList.getCheckedItemPosition();
        if(position > -1){
            adapter.remove(inventoryItem);      //remove item
            adapter.notifyDataSetChanged();     //refresh

            mTextItem.setText("");              // clear the EditText field
            mTextPrice.setText("");
            Toast.makeText(getApplicationContext(), "Delete"
                    + inventoryItem.getItemName(),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Nothing is deleted",
                                                Toast.LENGTH_SHORT).show();
        }
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
        userDataMap.put(userName, allItems.toArray(new InventoryItem[allItems.size()]));
        String jsonString = gson.toJson(userDataMap);
        editor.putString(USER_DATA, jsonString);

        editor.commit();    // Commit the edits!
    }
}
