package com.examples.myreceipts;

import android.app.Application;
import android.content.SharedPreferences;

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
    }

    public void saveInventoryItems(ArrayList<InventoryItem> updatedInventoryItems)
    {
        // save the inventory list to user preference file
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        globalUserInvetoryItems.put(currentUserName.toLowerCase(),
                updatedInventoryItems.toArray(new InventoryItem[updatedInventoryItems.size()]));
        String jsonString = gson.toJson(globalUserInvetoryItems);
        editor.putString(USER_INVENTORY_ITEMS, jsonString);

        editor.commit();    // Commit the edits!
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
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
}
