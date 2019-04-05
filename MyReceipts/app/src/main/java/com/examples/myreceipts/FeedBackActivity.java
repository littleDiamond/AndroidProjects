package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FeedBackActivity extends AppCompatActivity {
    private Button btn_help;
    private Button productFeedback;
    private Button reportBug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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
        btn_help = (Button) findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivityHelp();
            }
        });

        /**
         * Tap product_feedback button and show toast message to check
         */
        productFeedback = (Button) findViewById(R.id.btn_product_feedback);
        productFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedBackActivity.this, "Product feedback selected", Toast.LENGTH_SHORT ).show();
            }
        });

        /**
         * Tap report_bug button and show toast message to check
         */
        reportBug = (Button) findViewById(R.id.btn_report_bug);
        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedBackActivity.this, "Report bug selected", Toast.LENGTH_SHORT ).show();
            }
        });
    } // end of onCreate

    public void openActivityHelp(){
        /**
         * Open HelpActivity.
         */
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
