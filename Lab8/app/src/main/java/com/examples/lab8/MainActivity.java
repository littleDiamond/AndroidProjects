package com.examples.lab8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText name, loc, design;
    Button btn_save;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.setText1);
        loc = findViewById(R.id.setText2);
        design = findViewById(R.id.setText3);

        btn_save = findViewById(R.id.btn_save);
       btn_save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String
           }
       });
    }

}
