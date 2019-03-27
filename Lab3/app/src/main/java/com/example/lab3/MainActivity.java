package com.example.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Button button; // create the button instance
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialise the button using the id from the xml file
        button = findViewById(R.id.btnAccessActivity2);
        ((View) button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessActivity2();
            }
        });
    } //end of the onCreate() method

    private void accessActivity2(){
        Intent intent =new intent(this, Activity2.class);
        startActivity(intent);
}
