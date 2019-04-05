package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button btn_logIn;
    private  Button btn_signUp;
    public static final String USER_NAME_TEXT ="com.examples.myreceipts.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Tap login button to start next activity or signUp button to register.
         */
        btn_logIn = findViewById(R.id.btn_logIn);
        btn_logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity_log_in();
            }
        });
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity_sign_up();
            }
        });
    }
    public void openActivity_log_in(){
        /**
         * Send username and password to next activity.
         */
        EditText use_name = findViewById(R.id.userName);
        String hello_user = use_name.getText().toString();
        Intent intent = new Intent(this, LogInActivity.class);
        intent.putExtra(USER_NAME_TEXT, hello_user);
        /**
         * Open activity method.
         */
        startActivity(intent);
    }
    public void openActivity_sign_up(){
        /**
         * Open sign up activity.
         */
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}