package com.examples.myreceipts;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    private Button mBtnAdd, mBtnDelete, mBtnUpdate, mBtnConfirm;
    private static final String TAG = "InventoryListActivity";
    public static final String PREFS_NAME = "PreferenceFile";
    public static final String USER_DATA = "USER_DATA";
    ArrayList<InventoryItem> existingData = new ArrayList<>();
    private ItemArrayAdapter mAdapter;
    private String mUserName;
    private Map<String, InventoryItem[]> mUserDataMap = new HashMap<String, InventoryItem[]>();// a map between user name and user data
    private ActionMode actionMode = null;

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
                InventoryItem[] itemList = mUserDataMap.get(mUserName);
                if ( itemList != null && itemList.length > 0 ) {
                    existingData = new ArrayList<>(Arrays.asList(itemList));
                }
            }
        }
        // create the mAdapter to convert the array to views
        mAdapter = new ItemArrayAdapter(InventoryListActivity.this, existingData);
        mItemList.setAdapter(mAdapter); //attach the mAdapter to a ListView
        mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemOption(mItemList.getItemIdAtPosition(position), position);
//                mItemList.getItemIdAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

//        mItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            // Called when the user long-clicks on someView
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (actionMode != null) {
//                    return false;
//                }
//
//                // Start the CAB using the ActionMode.Callback defined above
//                actionMode = startActionMode(actionModeCallback);
//                view.setSelected(true);
//                return true;
//            }
//        });

        //Add single item
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });

        //Update single item
//        mBtnUpdate = findViewById(R.id.btnUpdate);
//        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateItem();
//            }
//        });
//
        //Delete single item
//        mBtnDelete = findViewById(R.id.btnDelete);
//        mBtnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteItem();
//            }
//        });

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
        // add mTextItem to mAdapter
        InventoryItem inventoryItem = new InventoryItem(mTextItem.getText().toString(),
                                 Double.parseDouble(mTextPrice.getText().toString()));
        if( !inventoryItem.getItemName().isEmpty() && inventoryItem.getItemName().length() >0 ){
            mAdapter.add(inventoryItem);      //Add item
            mAdapter.notifyDataSetChanged(); //refresh

            mTextItem.setText("");          // clear the EditText field
            mTextPrice.setText("");
            Toast.makeText(getApplicationContext(), "Add"
                    + inventoryItem.getItemName(),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Nothing is added",
                                                Toast.LENGTH_SHORT).show();
        }
    }
    public  void itemOption(String oldItem,final int index){
        final Dialog dialog = new Dialog(InventoryListActivity.this);
        InventoryItem inventoryItem = this.oldItem;
        
        dialog.setTitle("Choose option");
        dialog.setContentView(R.layout.item_option_dialog);

        TextView mTextDialog = dialog.findViewById(R.id.tvChooseOption);
        mTextDialog.setText("Choose option");

        EditText mEdItem = dialog.findViewById(R.id.edItemName);
        EditText mEdPrice = dialog.findViewById(R.id.edItemPrice);
        mEdItem.setText(inventoryItem.getItemName());
        mEdPrice.setText(Double.toString(inventoryItem.getItemPrice()));

        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
                dialog.dismiss();;
            }
        });

        Button btnDelete =dialog.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void updateItem(){
        InventoryItem inventoryItem = new InventoryItem(mTextItem.getText().toString(),
                Double.parseDouble(mTextPrice.getText().toString()));
        int position = mItemList.getCheckedItemPosition();

        if( !inventoryItem.getItemName().isEmpty() && inventoryItem.getItemName().length() >0 ){
            mAdapter.remove(mAdapter.getItem(position));          //remove item
            mAdapter.insert(inventoryItem,position); //add again
            mAdapter.notifyDataSetChanged();         //refresh

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
        int position = mItemList.getCheckedItemPosition();
        if(position > -1){
            mAdapter.remove(mAdapter.getItem(position));      //remove item
            mAdapter.notifyDataSetChanged();     //refresh

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
        ItemArrayAdapter listToSave = (ItemArrayAdapter) mItemList.getAdapter();
        ArrayList<InventoryItem> allItems = listToSave.getAllItems();

        Gson gson = new Gson();
        mUserDataMap.put(mUserName, allItems.toArray(new InventoryItem[allItems.size()]));
        String jsonString = gson.toJson(mUserDataMap);
        editor.putString(USER_DATA, jsonString);

        editor.commit();    // Commit the edits!
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
}
