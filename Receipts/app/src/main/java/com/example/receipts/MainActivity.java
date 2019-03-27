package com.example.receipts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME_MESSAGE = "com.example.receipts.USER_NAME";

    public static final String PASSWORD_MESSAGE = "com.example.receipts.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Called when the user taps the login button */
    public void logIn(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//
//        // send user name and password to login page
//        EditText userNameText = findViewById(R.id.user_name);
//        String userName = userNameText.getText().toString();
//
//        EditText passwordText = findViewById(R.id.password);
//        String password = passwordText.getText().toString();
//
//        intent.putExtra(USER_NAME_MESSAGE, userName);
//        intent.putExtra(PASSWORD_MESSAGE, password);
//        startActivity(intent);
    }

}
