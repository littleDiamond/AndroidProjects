package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btn_logIn;
    public static final String EXTRA_TEXT ="com.examples.myreceipts.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * Tap login button and open next activity
         */
        btn_logIn = (Button) findViewById(R.id.btn_logIn);
        btn_logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity_log_in();
            }
        });
    }
    public void openActivity_log_in(){
        /**
         * Send username and password to next activity.
         */
        EditText use_name = findViewById(R.id.user_name);
        String hello_user = use_name.getText().toString();
        Intent intent = new Intent(this, log_inActivity.class);
        intent.putExtra(EXTRA_TEXT, hello_user);
        /**
         * Open activity method.
         */
        startActivity(intent);
    }

}