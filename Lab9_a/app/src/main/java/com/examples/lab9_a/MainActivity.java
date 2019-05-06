package com.examples.lab9_a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button openDialog;
    private TextView inputDisplay;
    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDialog =findViewById(R.id.btnOpendialog);
        inputDisplay =findViewById(R.id.inputdisplay);

        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick: opening dialog");
            }
        });

    }//end of onCreate
}
