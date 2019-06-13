package com.examples.myreceipts;

import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.examples.myreceipts.InventoryListActivity.PREFS_NAME;
import static com.examples.myreceipts.InventoryListActivity.USER_DATA;


public class HomeActivity extends AppCompatActivity implements LogoutDialog.LogoutDialogListener{
    private static final String TAG = "HomeActivity";

    private String mUserName;
    private Button mBtnCreateList, mBtnPOS,mBtnKeeper;

    private Map<String, InventoryItem[]> mUserDataMap = new
            HashMap<String, InventoryItem[]>();
    private ArrayList<InventoryItem> posInventoryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: Started.");   //debug

        /**
         * show the username on screen
         */
        Intent intent = getIntent();
        mUserName = intent.getStringExtra(LoginActivity.USER_NAME_TEXT);
        TextView mTextHelloUser = findViewById(R.id.tvHelloUser);
        if(mUserName != null) {
            mTextHelloUser.setText(mUserName);
        }

        /**
         * Set up actionbar and have previous activity return.
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // FIXME: this is duplicate from InventoryListActivity
        // read save user data from preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String itemsInJson = settings.getString(USER_DATA, "");

        // if we have existing user data

        if ( !itemsInJson.isEmpty() ) {
            Gson gson = new Gson();  // Deserialize user data from json string
            mUserDataMap = gson.fromJson(itemsInJson, new TypeToken<Map<String,
                    InventoryItem[]>>() {}.getType());
            // find the data specific to the current user
            if ( mUserDataMap != null ) {
                InventoryItem[] itemList = mUserDataMap.get(mUserName.toLowerCase());
                if ( itemList != null && itemList.length > 0 ) {
                    posInventoryItems = new ArrayList<>(Arrays.asList(itemList));
                }
            }
        }

        /**
         * Tap create list button and open InventoryListActivity
         */
        mBtnCreateList = findViewById(R.id.btnCreateList);
        mBtnCreateList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent inventoryIntent = new Intent(HomeActivity.this, InventoryListActivity.class);
                inventoryIntent.putExtra(LoginActivity.USER_NAME_TEXT, mUserName);
                startActivity(inventoryIntent);
            }
        });

        /**
         * Tap checkout panel button and open PointOfSaleActivity.
         */
        mBtnPOS = findViewById(R.id.btnPOSMenu);
        mBtnPOS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Menu selected",
                                                Toast.LENGTH_SHORT ).show();
                Intent menuIntent = new Intent(HomeActivity.this, PointOfSaleActivity.class);
                menuIntent.putParcelableArrayListExtra("InventoryItem", posInventoryItems);
                startActivity(menuIntent);
            }
        });

        /**
         * Tap receipt keeper and show toast message for testing.
         */
        mBtnKeeper = findViewById(R.id.btnKeeper);
        mBtnKeeper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Keeper selected",
                                                            Toast.LENGTH_SHORT ).show();
            }
        });
    }
    /**
     *Set action bar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }
    /**
     *Tab action bar menu cases, toast text to test they all work.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.log_out:
                LogoutDialog dialog = new LogoutDialog();
                dialog.show(getSupportFragmentManager(),"Logout");
                return true;

            case R.id.help:
                Intent helpIntent = new Intent (this, HelpActivity.class);
                startActivity(helpIntent);
                return true;

            case R.id.feedback:
                Intent feedbackIntent = new Intent (this, FeedBackActivity.class);
                startActivity(feedbackIntent);
                return true;

            case R.id.setting:
                Intent asIntent = new Intent (this, AccountSettingActivity.class);
                startActivity(asIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is for closing this current activity when dialog option is positive.
     */
    @Override
    public void onConfirmClicked() {
        Intent logoutIntent = new Intent(this,LoginActivity.class);
        startActivity(logoutIntent);
    }
}
