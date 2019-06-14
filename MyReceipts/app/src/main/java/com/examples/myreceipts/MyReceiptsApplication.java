package com.examples.myreceipts;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *  User data sharing within the app
 */
public final class MyReceiptsApplication extends Application {

    public String getCurrentUser() {
        return currentUserName;
    }

    public void setCurrentUser(String newUserName) {
        currentUserName = newUserName;
    }

    public ArrayList<InventoryItem> getUserInventoryItems() {
        return userInventoryItems;
    }

    public ArrayList<Receipt> getUserReceipts()
    {
        return userReceipts;
    }

    public void loadUserData()
    {
        // read save user data from preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String inventoryItemsInJson = settings.getString(USER_INVENTORY_ITEMS, "");
        String receiptsInJson = settings.getString(USER_RECEIPTS, "");
        int globalReceiptID = settings.getInt(RECEIPT_ID, 0);

        // if we have existing user data
        Gson gson = new Gson();

        if ( !inventoryItemsInJson.isEmpty() ) {
            // Deserialize user data from json string
            globalUserInvetoryItems = gson.fromJson(inventoryItemsInJson, new TypeToken<Map<String,
                    InventoryItem[]>>() {}.getType());

            // find the data specific to the current user
            if ( globalUserInvetoryItems != null ) {
                InventoryItem[] itemList = globalUserInvetoryItems.get(currentUserName.toLowerCase());
                if ( itemList != null && itemList.length > 0 ) {
                    userInventoryItems = new ArrayList<>(Arrays.asList(itemList));
                }
            }
        }

        if ( !receiptsInJson.isEmpty() ) {
            // Deserialize user data from json string
            globalUserReceipts = gson.fromJson(receiptsInJson, new TypeToken<Map<String,
                    Receipt[]>>() {}.getType());

            // find the data specific to the current user
            if ( globalUserReceipts != null ) {
                Receipt[] itemList = globalUserReceipts.get(currentUserName.toLowerCase());
                if ( itemList != null && itemList.length > 0 ) {
                    userReceipts = new ArrayList<>(Arrays.asList(itemList));
                }
            }
        }

        Receipt.updateGlobalReceiptID(globalReceiptID);
    }

    public void saveInventoryItems(ArrayList<InventoryItem> updatedInventoryItems)
    {
        userInventoryItems = updatedInventoryItems;

        // save the inventory list to user preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        globalUserInvetoryItems.put(currentUserName.toLowerCase(),
                userInventoryItems.toArray(new InventoryItem[userInventoryItems.size()]));
        String jsonString = gson.toJson(globalUserInvetoryItems);
        editor.putString(USER_INVENTORY_ITEMS, jsonString);

        editor.commit();    // Commit the edits!
    }

    public void saveReceipt(Receipt newReceipt)
    {
        userReceipts.add(newReceipt);

        // save all the receipts to user preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        globalUserReceipts.put(currentUserName.toLowerCase(),
                userReceipts.toArray(new Receipt[userReceipts.size()]));
        String jsonString = gson.toJson(globalUserReceipts);
        editor.putString(USER_RECEIPTS, jsonString);

        editor.commit();    // Commit the edits!
    }

    public void saveGlobalReceiptID() {

        Log.d("App data", "save the global receipt id to user preference file");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(RECEIPT_ID, Receipt.getGlobalReceiptID());

        editor.commit();    // Commit the edits!
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        saveGlobalReceiptID();

        super.onTerminate();
    }

    private String currentUserName;
    private Map<String, InventoryItem[]> globalUserInvetoryItems = new
            HashMap<String, InventoryItem[]>();

    private Map<String, Receipt[]> globalUserReceipts = new
            HashMap<String, Receipt[]>();

    private ArrayList<InventoryItem> userInventoryItems = new ArrayList<>();
    private ArrayList<Receipt> userReceipts = new ArrayList<>();

    private static final String PREFS_NAME = "PreferenceFile";
    private static final String USER_INVENTORY_ITEMS = "USER_DATA";
    private static final String USER_RECEIPTS = "USER_RECEIPTS";
    private static final String RECEIPT_ID = "RECEIPT_ID";
}
