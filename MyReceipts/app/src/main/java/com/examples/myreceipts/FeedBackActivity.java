package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FeedBackActivity extends AppCompatActivity {
    private Button mBtnHelp, mBtnBug;
    private static final String TAG = "FeedBackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Log.d(TAG, "onCreate: Started.");

        /**
         * Set up actionbar and have previous activity return.
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        /**
         * Tap help button to get back to HelpActivity
         */
        mBtnHelp = findViewById(R.id.btnHelp);
        mBtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hIntent = new Intent(FeedBackActivity.this,
                        HelpActivity.class);
                startActivity(hIntent);
            }
        });

        /**
         * Tap report bug button and go to Email activity to send user's bug report
         */
        mBtnBug = findViewById(R.id.btnBug);
        mBtnBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fbIntent = new Intent(FeedBackActivity.this,
                        EmailActivity.class);
                startActivity(fbIntent);
            }
        });
    } // end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
