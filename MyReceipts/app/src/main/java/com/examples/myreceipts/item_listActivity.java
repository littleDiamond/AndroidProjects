package com.examples.myreceipts;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.util.Log;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class item_listActivity extends AppCompatActivity {
    private static final String TAG ="item_list";

    ArrayList<String> selected = new ArrayList<>();
    ArrayList<Double> totalArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        totalArray = new ArrayList<>();
        Button btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {

                displayList();
            }
        });

        loadData();

        Button btn_save =findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v) {
               saveData();
           }
        });
    }

    public void displayList(){
        ListView item_list = findViewById(R.id.item_list);
        final ArrayList<String> arrayList =new ArrayList<>();
        arrayList.add("text1");
        arrayList.add("text2");
        arrayList.add("text3");

        for(int i = 0; i< arrayList.size(); i++){
            Log.d(TAG, "displayList: name: " + arrayList.get(i));
        } //end of for-loop

        //to add a Toast to display some message once an item is clicked
        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adding the selected item to the selected ArrayList
                selected.add(arrayList.get(position).toString());

                Toast.makeText(item_listActivity.this, "clicked item: " +
                        arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(totalArray);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences =getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<NewItem>>() {}.getType();
        totalArray = gson.fromJson(json, type);

        if(totalArray == null){
            totalArray = new ArrayList<>();
        }
    }


}
