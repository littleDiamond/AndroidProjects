package com.examples.myreceipts;

import android.app.Dialog;
import android.app.SearchManager;
import android.view.MenuItem;
import android.widget.SearchView;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InventoryListActivity extends AppCompatActivity {
    private ListView mItemList;
    private EditText mTextItem, mTextPrice;
    private Button mBtnAdd;

    private static final String TAG = "InventoryListActivity";

    ArrayList<InventoryItem> existingData = new ArrayList<>();
    private InventoryAdapter mAdapter;

    private boolean mItemNameValid;
    private boolean mItemPriceValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Log.d(TAG, "onCreate: Started.");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mItemList = findViewById(R.id.lvItemList);
        mTextItem = findViewById(R.id.etAddItem);
        mTextPrice = findViewById(R.id.etAddPrice);

        // get the inventory items for current user
        MyReceiptsApplication app = (MyReceiptsApplication) getApplication();
        existingData = app.getUserInventoryItems();

        // create the mAdapter to convert the array to views
        mAdapter = new InventoryAdapter(InventoryListActivity.this, existingData);
        mItemList.setAdapter(mAdapter); //attach the mAdapter to a ListView

        mItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                activeItemOption((InventoryItem) mItemList.getItemAtPosition(position),
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
            public void afterTextChanged(Editable s) {
            }

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
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try {
                    // price needs to be a positive double to be valid
                    mItemPriceValid = Double.parseDouble(s.toString()) > 0;
                } catch (NumberFormatException e) {
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

        if (itemName.isEmpty() || itemPrice.isEmpty())
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
                + inventoryItem.getItemName(), Toast.LENGTH_SHORT).show();

    }


    public void activeItemOption(InventoryItem currentItem, final int position) {
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
                mDialog.dismiss();
                ;
            }
        });

        Button btnDelete = mDialog.findViewById(R.id.btnDelete);
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
    private void updateItem(InventoryItem updatedItem, int position) {
        if (!isInventoryItemValid(updatedItem))
            return;

        InventoryItem currentItem = mAdapter.getItem(position);
        currentItem.setItemName(updatedItem.getItemName());     //Edit and save new item name
        currentItem.setItemPrice(updatedItem.getItemPrice());   //Edit and save new item price
        mAdapter.notifyDataSetChanged();         //refresh

        Toast.makeText(getApplicationContext(), "Updated "
                + currentItem.getItemName(), Toast.LENGTH_SHORT).show();

    }

    //Check if the item name EditView field is empty and the item price EditView field is positive integer
    private boolean isInventoryItemValid(InventoryItem item) {
        if (item.getItemName().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Name can't be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (item.getItemPrice() <= 0.0) {
            Toast.makeText(getApplicationContext(), "Price has to be positive.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Delete item method
    private void deleteItem(int position) {
        if (position >= 0 && position < mAdapter.getCount()) {
            InventoryItem currentItem = mAdapter.getItem(position);
            mAdapter.remove(currentItem);      //remove item
            mAdapter.notifyDataSetChanged();     //refresh

            Toast.makeText(getApplicationContext(), "Deleted "
                    + currentItem.getItemName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_list_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // return false as we do all the filtering via text change instead of submit button
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                // return true as we implement this function
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name:
                sortArrayListByName();
                return true;

            case R.id.sort_price:
                sortArrayListByPrice();
                return true;

            case R.id.go_to_pos:
                Intent posIntent = new Intent(InventoryListActivity.this,
                        PointOfSaleActivity.class);

                // get the list of items from list view
                InventoryAdapter listToSave = (InventoryAdapter) mItemList.getAdapter();
                ArrayList<InventoryItem> allItems = listToSave.getAllItems();

                // save it
                MyReceiptsApplication app = (MyReceiptsApplication) getApplication();
                app.saveInventoryItems(allItems);

                startActivity(posIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Sort out the array list order by item name
    private void sortArrayListByName() {
        Collections.sort(existingData, new Comparator<InventoryItem>() {
            @Override
            public int compare(InventoryItem o1, InventoryItem o2) {
                // ascending order
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    //Sort out the array list order by item name
    private void sortArrayListByPrice() {
        Collections.sort(existingData, new Comparator<InventoryItem>() {
            @Override
            public int compare(InventoryItem p1, InventoryItem p2) {
                return Double.compare(p1.getItemPrice(), p2.getItemPrice());
                // return p1.getItemPrice().Double.compareTo(p2.getItemName());
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // get the list of items from list view
        InventoryAdapter listToSave = (InventoryAdapter) mItemList.getAdapter();
        ArrayList<InventoryItem> allItems = listToSave.getAllItems();

        MyReceiptsApplication app = (MyReceiptsApplication) getApplication();
        app.saveInventoryItems(allItems);
    }
}
