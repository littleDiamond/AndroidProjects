package com.examples.myreceipts;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class item_listActivity extends AppCompatActivity {
    private ListView itemList;
    private EditText addItem;
    private EditText addPrice;
    private Button save;
    ArrayList<NewItem> itemArray = new ArrayList<>(); //Add the item object to on ArrayList
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

        itemList = findViewById(R.id.item_list);
        addItem = findViewById(R.id.add_item);
        addPrice = findViewById(R.id.add_price);
        save = findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Take input item detail and add to array to show.
                 */
                String getInput1 = addItem.getText().toString();
                double getInput2 = Double.parseDouble(addPrice.getText().toString());

                if (itemArray.contains(getInput1)) {
                    Toast.makeText(getBaseContext(), "Item added to the array", Toast.LENGTH_SHORT).show();
                } else if (getInput1 == null || getInput1.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Input item field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    final ArrayList<String> arrayList = new ArrayList<>();
                    itemArray.add( new NewItem(getInput1, getInput2));

                    ItemArrayAdapter adapter = new ItemArrayAdapter(item_listActivity.this, R.layout.adapter_item_list, itemArray);
                    itemList.setAdapter(adapter);
                    ((EditText) ((EditText) findViewById(R.id.add_item))).setText("");
                }
            }
        });
    }
}
