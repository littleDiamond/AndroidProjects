package com.examples.myreceipts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class log_inActivity extends AppCompatActivity {
    private Button btn_create_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        /**
         * show the username on screen
         */
        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        TextView hello_user = findViewById(R.id.hello_user);
        hello_user.setText(text);

        /**
         * Set up actionbar and have previous activity return.
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        /**
         * Tap create list button and open item_list activity
         */
        btn_create_list = (Button) findViewById(R.id.btn_create_list);
        btn_create_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity_item_list();
            }
        });
    }
    public void openActivity_item_list(){
        Intent intent = new Intent(this, item_listActivity.class);
        startActivity(intent);
    }

    /**
     *Set action bar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     *Tab action bar menu cases, toast text to test they all work.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.my_account:
                Toast.makeText(this, "My account selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.operator:
                Toast.makeText(this, "Seller selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.user:
                Toast.makeText(this, "Buyer selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.help_feedback:
                Intent help = new Intent (this, helpActivity.class);
                startActivity(help);
                return true;

            case R.id.help:
                Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.feedback:
                Toast.makeText(this, "Feedback selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.setting:
                Intent settings = new Intent (this, SettingsActivity.class);
                startActivity(settings);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
