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

    ListView listView;
    ArrayList<String> selected = new ArrayList<>();
    Button btnSummary;
    Button btnTotal;
    double itemPrice = 0;
    ArrayList<Double> totalArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalArray = new ArrayList<>();

        //allow the button to perform something
        Button btnVList = findViewById(R.id.btnViewList);
        btnVList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayList();
            }
        });

        //initialised the btnSummary
        btnSummary = findViewById(R.id.btnSummary);
        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItems();
            }
        });

        btnTotal = findViewById(R.id.btnTotal);
        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "The total is: $" +getTotal(), Toast.LENGTH_SHORT ).show();

            }
        });

    } //end to onCreate() method

    public void displayList(){
        listView = findViewById(R.id.studentList);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Pencil");
        arrayList.add("Crayons");
        arrayList.add("Eraser");
        arrayList.add("Pen");
        arrayList.add("Fiction Book");
        arrayList.add("Novel");
        arrayList.add("Art book");
        arrayList.add("Drawing book");
        arrayList.add("Coloring book");
        arrayList.add("Bag");

        for(int i = 0; i< arrayList.size(); i++){
            Log.d(TAG, "displayList: name: " + arrayList.get(i));
        } //end of for-loop

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        //to add a Toast to display some message once an item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adding the selected item to the selected ArrayList
                selected.add(arrayList.get(position).toString());

                Toast.makeText(MainActivity.this, "clicked item: " + position + " " +
                        arrayList.get(position).toString(), Toast.LENGTH_SHORT).show();
                totalArray.add(getItemCost(arrayList.get(position).toString()));
            }
        });



    }//end of the displayList()


    public void selectedItems(){
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, selected);
        listView.setAdapter(arrayAdapter2);
    }

    public double getItemCost(String item){
        //write some codes and compare it with the content of your arraylist
        if(item.equals("Pencil")){
            itemPrice = 0.50;
        } else if(item.equals("Crayons")){
            itemPrice = 3.25;
        }
        else if(item.equals("Eraser")){
            itemPrice = 0.25;
        }
        else if(item.equals("Pen")){
            itemPrice =4.25;
        }
        else if(item.equals("Fiction Book")){
            itemPrice = 18.75;
        }
        else if(item.equals("Novel")){
            itemPrice = 15.00;
        }
        else if(item.equals("Art book")){
            itemPrice = 7.50;
        }
        else if(item.equals("Drawing book")){
            itemPrice = 6.25;
        }
        else if(item.equals("Bag")){
            itemPrice = 50;
        }
        return itemPrice;
    }

    public double getTotal(){
        double total = 0;
        for(int i = 0; i < totalArray.size(); i++){
            total = total + Double.parseDouble(totalArray.get(i).toString());
        }
        return total;
    }

}// end of the activity
