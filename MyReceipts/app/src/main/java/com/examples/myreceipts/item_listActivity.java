package com.examples.myreceipts;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class item_listActivity extends AppCompatActivity {
    private ListView itemList;
    private EditText item;
    private EditText price;
    private Button insert;
    ArrayList<NewItem> itemArray = new ArrayList<>();
    private static final String TAG = "item_listActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Log.d(TAG, "onCreate: Started.");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        item = findViewById(R.id.add_item);
        price = findViewById(R.id.add_price);

        insert = findViewById(R.id.btn_insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertList();
            }
        });
    }// end of onCreate method

    private void insertList() {
        itemList = findViewById(R.id.item_list);
        final ArrayList<NewItem> arrayList = new ArrayList<>(); //construct the data source

        NewItem newItem = new NewItem("apple", "0.00");// add item to adapter
        arrayList.add(newItem);

        // create the adapter to convert the array to views
        ItemArrayAdapter adapter = new ItemArrayAdapter(item_listActivity.this, arrayList);
        itemList.setAdapter(adapter); //attach the adapter to a ListView

        JSONArray jsonArray= new JSONArray();
        ArrayList<NewItem>  itemArray = NewItem.fromJson(jsonArray);
        adapter.addAll(newItem);

        // clear the EditText field for user to input new data.
        item.setText("");
        price.setText("");

    }

}
