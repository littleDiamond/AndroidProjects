package com.examples.myreceipts;

import android.content.Intent;

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


public class HomeActivity extends AppCompatActivity {
    private Button btnCreateList;
    private Button btnPanel;
    private Button btnKeeper;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * show the username on screen
         */
        Intent intent = getIntent();
        userName = intent.getStringExtra(LoginActivity.USER_NAME_TEXT);
        TextView hello_user = findViewById(R.id.hello_user);
        hello_user.setText(userName);

        /**
         * Set up actionbar and have previous activity return.
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Tap create list button and open item_list activity
         */
        btnCreateList = findViewById(R.id.btn_create_list);
        btnCreateList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openInventoryListActivity();
            }
        });

        /**
         * Tap checkout panel button and show toast message for testing.
         */
        btnPanel = findViewById(R.id.btn_panel);
        btnPanel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Checkout panel selected", Toast.LENGTH_SHORT ).show();
            }
        });

        /**
         * Tap receipt keeper and show toast message for testing.
         */
        btnKeeper = findViewById(R.id.btn_keeper);
        btnKeeper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Receipt keeper selected", Toast.LENGTH_SHORT ).show();
            }
        });
    }
    public void openInventoryListActivity(){
        Intent intent = new Intent(this, InventoryListActivity.class);
        intent.putExtra(LoginActivity.USER_NAME_TEXT, userName);
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

            case R.id.log_out:
                Intent logOut = new Intent (this, LoginActivity.class);
                startActivity(logOut);
                return true;

            case R.id.user:
                Toast.makeText(this, "User selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.help:
                Intent help = new Intent (this, HelpActivity.class);
                startActivity(help);
                return true;

            case R.id.feedback:
                Intent feedback = new Intent (this, FeedBackActivity.class);
                startActivity(feedback);
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
