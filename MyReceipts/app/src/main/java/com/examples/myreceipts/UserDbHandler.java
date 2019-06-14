package com.examples.myreceipts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class UserDbHandler extends SQLiteOpenHelper {
    // version 1, columns
    // KEY_ID
    // KEY_NAME
    // KEY_EMAIL
    // KEY_PASSWORD

    private static final int DB_VERSION = 2;                //DATABASE VERSION
    private static final String DB_NAME = "register.db";    //DATABASE NAME
    private static final String TABLE_USERS = "Register";   //TABLE NAME

    private static final String KEY_ID = "id";              //TABLE USERS COLUMNS,ID COLUMN @primaryKey
    private static final String KEY_NAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_STREET_ADDRESS = "street";
    private static final String KEY_AREA_ADDRESS = "area";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_GST = "gst";

    private static final String TAG = "UserDbHandler";
    /**
     * Constructor
     *
     * @param context
     */
    public UserDbHandler(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Started."); //debug
        // Create Table when onCreate gets called
        String USER_CREATE_TABLE = "CREATE TABLE " + TABLE_USERS
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_COMPANY + " TEXT,"
                + KEY_STREET_ADDRESS + " TEXT,"
                + KEY_AREA_ADDRESS + " TEXT,"
                + KEY_PHONE_NUMBER + " TEXT,"
                + KEY_GST + " TEXT"
                + ")";
        db.execSQL(USER_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  {
        //Drop Register Table if exist
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        //onCreate(db);   // Create tables again

        // we need to upgrade the db from v1 to v2
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + KEY_COMPANY + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + KEY_STREET_ADDRESS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + KEY_AREA_ADDRESS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + KEY_PHONE_NUMBER + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + KEY_GST + " TEXT");
        }
    }
    /**
     * This method is to create user record
     * @param user
     */
    public long addUser(User user){
        //Add the required code and call the necessary methods
        SQLiteDatabase db = this.getWritableDatabase();

        //Get the data repository in write mode
        //Create a new map of values, where column names are the key
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME,  user.getName().toLowerCase());
        cValues.put(KEY_EMAIL, user.getEmail().toLowerCase());
        cValues.put(KEY_PASSWORD, user.getPassword());
        cValues.put(KEY_COMPANY, user.getCompanyName().toLowerCase());
        cValues.put(KEY_STREET_ADDRESS, user.getStreetAddress().toLowerCase());
        cValues.put(KEY_AREA_ADDRESS, user.getAreaAddress().toLowerCase());
        cValues.put(KEY_PHONE_NUMBER, user.getPhoneNumber());
        cValues.put(KEY_GST, user.getGST());

        long newRowId = db.insert(TABLE_USERS, null, cValues);  //Insert the new row
        db.close(); // Close the db after insertion

        return newRowId;
    }
    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_COMPANY, user.getCompanyName());
        cValues.put(KEY_STREET_ADDRESS, user.getStreetAddress());
        cValues.put(KEY_AREA_ADDRESS, user.getAreaAddress());
        cValues.put(KEY_PHONE_NUMBER, user.getPhoneNumber());
        cValues.put(KEY_GST, user.getGST());

        String[] whereArgs = {user.getName().toLowerCase()};

        // updating row
        int updatedRow = db.update(TABLE_USERS, cValues, KEY_NAME + " = ?", whereArgs);
        if ( updatedRow > 0 )
        {
            Log.d("DB", "updateUser is successful!");
        }
        return true;
    }

    public User getUserByName(String username) {

        SQLiteDatabase db = this.getReadableDatabase();

        String whereClause = KEY_NAME + " = ?";    //Selection criteria
        String[] selectionArs = {username.toLowerCase()};        //Selection argument

        Cursor mCursor = db.query(TABLE_USERS, null, whereClause, selectionArs,
                null, null,null);
        //boolean result = false;
        User existingUser = null;
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();

            existingUser = new User();

            int columnIndex = mCursor.getColumnIndex(KEY_EMAIL);
            if ( columnIndex >= 0 )
            {
                existingUser.setEmail(mCursor.getString(columnIndex));
            }

            columnIndex = mCursor.getColumnIndex(KEY_COMPANY);
            if ( columnIndex >= 0 )
            {
                existingUser.setCompanyName(mCursor.getString(columnIndex));
            }

            columnIndex = mCursor.getColumnIndex(KEY_STREET_ADDRESS);
            if ( columnIndex >= 0 )
            {
                existingUser.setStreetAddress(mCursor.getString(columnIndex));
            }

            columnIndex = mCursor.getColumnIndex(KEY_AREA_ADDRESS);
            if ( columnIndex >= 0 )
            {
                existingUser.setAreaAddress(mCursor.getString(columnIndex));
            }

            columnIndex = mCursor.getColumnIndex(KEY_PHONE_NUMBER);
            if ( columnIndex >= 0 )
            {
                existingUser.setPhoneNumber(mCursor.getString(columnIndex));
            }

            columnIndex = mCursor.getColumnIndex(KEY_GST);
            if ( columnIndex >= 0 )
            {
                existingUser.setGST(mCursor.getString(columnIndex));
            }

            Log.d("DB", existingUser.toString());
        }
        mCursor.close();
        db.close();

        return existingUser;
    }


    /**
     * This method is to check user exist or not
     * @param username
     * @param password
     * @return
     */
    public Boolean checkUser (String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String whereClause = KEY_NAME + " = ? AND " + KEY_PASSWORD + " = ?";    //Selection criteria
        String[] selectionArs = {username.toLowerCase(), password};        //Selection argument

        Cursor mCursor = db.query(TABLE_USERS, null, whereClause, selectionArs,
                                                null, null,null);
        boolean result = false;
        if (mCursor != null && mCursor.getCount() > 0) {
            result = true;
        }
        mCursor.close();
        db.close();

        return result;
    }

    public Boolean doesEmailExist(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_ID}; //Array of columns to fetch
        String whereClause = KEY_EMAIL + " = ?";    //Selection criteria
        String[] selectionArs = {email.toLowerCase()};        //Selection argument

        Cursor mCursor = db.query(TABLE_USERS, columns, whereClause, selectionArs,
                                            null, null,null);
        boolean result = false;
        if (mCursor != null && mCursor.getCount() > 0) {
            result = true;
        }
        mCursor.close();
        db.close();

        return result;
    }
}
