package com.examples.myreceipts;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventoryListActivity extends AppCompatActivity {
    private ListView mItemList;
    private EditText mTextItem, mTextPrice;
    private Button mBtnAdd;

    private static final String TAG = "InventoryListActivity";
    public static final String PREFS_NAME = "PreferenceFile";
    public static final String USER_DATA = "USER_DATA";
    ArrayList<InventoryItem> existingData = new ArrayList<>();
    private ItemArrayAdapter mAdapter;
    private String mUserName;

    // a map between user name and user data
    private Map<String, InventoryItem[]> mUserDataMap = new
            HashMap<String, InventoryItem[]>();
    private ActionMode actionMode = null;

    private boolean mItemNameValid;
    private boolean mItemPriceValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Log.d(TAG, "onCreate: Started.");

        Intent intent = getIntent();
        mUserName = intent.getStringExtra(LoginActivity.USER_NAME_TEXT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mItemList = findViewById(R.id.lvItemList);
        mTextItem = findViewById(R.id.etAddItem);
        mTextPrice = findViewById(R.id.etAddPrice);

        // read save user data from preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String itemsInJson = settings.getString(USER_DATA, "");

        // if we have existing user data
        if ( !itemsInJson.isEmpty() ) {
            Gson gson = new Gson();  // Deserialize user data from json string
            mUserDataMap = gson.fromJson(itemsInJson, new TypeToken<Map<String,
                                            InventoryItem[]>>() {}.getType());
            // find the data specific to the current user
            if ( mUserDataMap != null ) {
                InventoryItem[] itemList = mUserDataMap.get(mUserName.toLowerCase());
                if ( itemList != null && itemList.length > 0 ) {
                    existingData = new ArrayList<>(Arrays.asList(itemList));
                }
            }
        }

        // create the mAdapter to convert the array to views
        mAdapter = new ItemArrayAdapter(InventoryListActivity.this, existingData);
        mItemList.setAdapter(mAdapter); //attach the mAdapter to a ListView
        mItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                activeItemOption((InventoryItem)mItemList.getItemAtPosition(position),
                                                                        position);
                return true;
            }
        });

        //Add single item
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnAdd.setEnabled(false);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });

        // only enable "add item" if a valid item name and price is entered
        mTextItem.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mItemNameValid = (s.length() > 0);
                mBtnAdd.setEnabled(mItemNameValid && mItemPriceValid);
            }
        });

        mTextPrice.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try {
                    double price = Double.parseDouble(s.toString());
                    mItemPriceValid = true;
                } catch(NumberFormatException e) {
                    mItemPriceValid = false;
                }

                mBtnAdd.setEnabled(mItemNameValid && mItemPriceValid);
            }
        });
    }// end of onCreate method

    //Add item method
    private void addNewItem() {
        String itemName = mTextItem.getText().toString();
        String itemPrice = mTextPrice.getText().toString();

        if ( itemName.isEmpty() || itemPrice.isEmpty() )
            return;

        // add mTextItem to mAdapter
        InventoryItem inventoryItem = new InventoryItem(itemName,
                                 Double.parseDouble(itemPrice));

        if (!isInventoryItemValid(inventoryItem))
            return;

        mAdapter.add(inventoryItem);      //Add item
        mAdapter.notifyDataSetChanged(); //refresh

        mTextItem.setText("");          // clear the EditText field
        mTextPrice.setText("");
        Toast.makeText(getApplicationContext(), "Add "
                + inventoryItem.getItemName(),Toast.LENGTH_SHORT).show();
    }

    public void activeItemOption(InventoryItem currentItem, final int position){
        final Dialog mDialog = new Dialog(InventoryListActivity.this);
        InventoryItem inventoryItem = currentItem;
        
        mDialog.setTitle("Choose option");
        mDialog.setContentView(R.layout.item_option_dialog);

        final TextView mTextDialog = mDialog.findViewById(R.id.tvChooseOption);
        mTextDialog.setText("Choose option");

        final EditText mEdItem = mDialog.findViewById(R.id.edItemName);
        final EditText mEdPrice = mDialog.findViewById(R.id.edItemPrice);
        mEdItem.setText(inventoryItem.getItemName());
        mEdPrice.setText(Double.toString(inventoryItem.getItemPrice()));

        Button btnUpdate = mDialog.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryItem updatedItem = new InventoryItem(mEdItem.getText().toString(),
                                        Double.parseDouble(mEdPrice.getText().toString()));
                updateItem(updatedItem, position);
                mDialog.dismiss();;
            }
        });

        Button btnDelete =mDialog.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    //Update item method
    private void updateItem(InventoryItem updatedItem, int position){
        if (!isInventoryItemValid(updatedItem))
            return;

        InventoryItem currentItem = mAdapter.getItem(position);
        currentItem.setItemName(updatedItem.getItemName());     //Edit and save new item name
        currentItem.setItemPrice(updatedItem.getItemPrice());   //Edit and save new item price
        mAdapter.notifyDataSetChanged();         //refresh

        Toast.makeText(getApplicationContext(), "Updated "
                + currentItem.getItemName(),Toast.LENGTH_SHORT).show();

    }

    //Check if the item name EditView field is empty and the item price EditView field is positive integer
    private boolean isInventoryItemValid(InventoryItem item) {
        if( item.getItemName().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Name can't be empty",
                                                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if( item.getItemPrice() <= 0.0 ) {
            Toast.makeText(getApplicationContext(), "Price has to be positive.",
                                                        Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Delete item method
    private void deleteItem(int position){
        if(position >=0 && position < mAdapter.getCount()){
            InventoryItem currentItem = mAdapter.getItem(position);
            mAdapter.remove(currentItem);      //remove item
            mAdapter.notifyDataSetChanged();     //refresh

            Toast.makeText(getApplicationContext(), "Deleted "
                    + currentItem.getItemName(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_list_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
       searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    /**
     *Tab action bar menu cases, toast text to test they all work.
     */
 //   @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
// //           case R.id.search:
// //               Toast.makeText(this, "search selected", Toast.LENGTH_SHORT).show();
// //               return true;
//
//            case R.id.add_to_menu:
//
//                Intent menuIntent = new Intent(InventoryListActivity.this,
//                                                        PointOfSaleActivity.class);
//                startActivity(menuIntent);
//                return true;
//
//
//        }
//        return super.(onOptionsItemSelected(item);
//    }


    @Override
    protected void onStop() {
        super.onStop();
        // save the inventory list to user preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        // get the list of items from list view
        ItemArrayAdapter listToSave = (ItemArrayAdapter) mItemList.getAdapter();
        ArrayList<InventoryItem> allItems = listToSave.getAllItems();

        Gson gson = new Gson();
        mUserDataMap.put(mUserName.toLowerCase(), allItems.toArray(new InventoryItem[allItems.size()]));
        String jsonString = gson.toJson(mUserDataMap);
        editor.putString(USER_DATA, jsonString);

        editor.commit();    // Commit the edits!
    }
}
