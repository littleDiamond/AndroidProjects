package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class PointOfSaleActivity extends AppCompatActivity {
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_POS);

        Intent intent = getIntent();
        userName = intent.getStringExtra(LoginActivity.USER_NAME_TEXT);
        TextView mTextHelloUser = findViewById(R.id.tvHelloUser);
        mTextHelloUser.setText(userName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
