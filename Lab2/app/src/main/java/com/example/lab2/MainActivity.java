package com.example.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ListView  listView;
    ArrayList<String> selected = new ArrayList<>();
    Button btnSummary;
    Button btnTotal;
    double itemPrice =0;
    ArrayList<Double> totalArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total ArrayList = new ArrayList<>();
        //allow the button to do stuff
        //get the button

        Button btn = findViewById(R.id.viewListBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Lab 2", "this is a log message");
                displayList();

            }
        });
        //initial the summary
        btnSummary = findViewById(R.id.btnSummary);
        btnSummary.setOnClickListener(new View.onClickListener){
            public void onClick(View v){
                selectedItems();
            }
        });

        btnTotal = findViewById(R.id.btnTotal);
        btnTotal.setOnClickListener(new View.onClickListener){
            public void onClick(View v){
                selectedItems(MainActivity.this, "The total is: $ " +getTotal(),Total);
            }
        });
    }

    public void displayList(){
        listView = findViewById(R.id.studentsList);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("art book");
        arrayList.add("novel");
        arrayList.add("drawing book");
        arrayList.add("colour book");
        arrayList.add("cook book");
        arrayList.add("bag");

        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(TAG, "displayList: name: "+arrayList.get(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


        //To add a Toast to display some message once an item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "clicked item: "+position+ " "+
                        arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                totalArray.add(getItemCost(arrayList.get(position).toString()));
            }
        });
    } // eof displayList()

    public void selectedItems(){
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, andriod.R.layout.simple_list_item_1,selected);
        listView.setAdapter(arrayAdapter2);
    }

    public  double getItemCost(String,item){
        //compare the contest of your arraylist
        if(item.equals("cook book")){
            itemPrice = 0.50;
        } else if(item.equals("novel")){
            itemPrice = 7.50;
        }else if(item.equals("drawing book")){
            itemPrice = 9.50;
        }else if(item.equals("colour book")){
            itemPrice = 8.50;
        }else if(item.equals("art book")){
            itemPrice = 6.50;
        }else if(item.equals("bag")){
            itemPrice = 3.50;
        }


        return itemPrice;
    }

    public double getTotal(){
        double total = 0;
        for(int i =0; i < totalArray.size(); i++){
            total = total + Double.parseDouble(totalArray.get(i))
        }
    }
}// end of the activity
