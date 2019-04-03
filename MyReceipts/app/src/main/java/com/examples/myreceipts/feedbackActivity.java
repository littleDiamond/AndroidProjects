package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class feedbackActivity extends AppCompatActivity {
    private Button btn_help;
  //  private Button btn_product_feedback;
  //  private Button btn_report_bug;

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
         * Tap help button to get back to helpActivity
         */
        btn_help = (Button) findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity_help();
            }
        });

        /**
         * Tap product_feedback button and show toast message to check
         *  btn_product_feedback = (Button) findViewById(R.id.btn_product_feedback);
         */

        /**
         * Tap report_bug button and show toast message to check
         * btn_report_bug = (Button) findViewById(R.id.btn_report_bug);
         */
    }

    public void openActivity_help(){
        /**
         * Open helpActivity.
         */
        Intent intent = new Intent(this, helpActivity.class);
        startActivity(intent);
    }
}
