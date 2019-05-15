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
    private static final int DB_VERSION = 1;                //DATABASE VERSION
    private static final String DB_NAME = "register.db";    //DATABASE NAME
    private static final String TABLE_USERS = "Register";   //TABLE NAME
    private static final String KEY_ID = "id";              //TABLE USERS COLUMNS,ID COLUMN @primaryKey
    private static final String KEY_NAME = "username";      //modify this,COLUMN user name
    private static final String KEY_EMAIL = "email";        //modify this,COLUMN email
    private static final String KEY_PASSWORD = "password";  //modify this,COLUMN password

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
        Log.d(TAG, "onCreate: Started.");
        // Create Table when onCreate gets called
        String USER_CREATE_TABLE = "CREATE TABLE " + TABLE_USERS
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT"
                + ")";
        db.execSQL(USER_CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop Register Table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);   // Create tables again
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

        long newRowId = db.insert(TABLE_USERS, null, cValues);  //Insert the new row
        db.close(); // Close the db after insertion

        return newRowId;
    }
    /**
     * This method is to fetch all user and return the list of user records
     * @return arrayList
     */
    public ArrayList<User> getUsers(){
        String[] column = {KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD};  //Array of columns to fetch
        String sortOrder = KEY_NAME + " ASC";    //Sorting orders
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_USERS, column,
                                null,null,
                                null, null, sortOrder);

        //Traversing through all rows and adding to list
        if(mCursor != null && mCursor.getCount() > 0) {
            int columnID = mCursor.getColumnIndex(KEY_ID);
            int columnName = mCursor.getColumnIndex(KEY_NAME);
            int columnEmail = mCursor.getColumnIndex(KEY_EMAIL);
            int columnPassword = mCursor.getColumnIndex(KEY_PASSWORD);

            if (mCursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(mCursor.getInt(columnID));
                    String temp = mCursor.getString(columnName);
                    user.setName(temp);
                    temp = mCursor.getString(columnEmail);
                    user.setEmail(temp);
                    temp = mCursor.getString(columnPassword);
                    user.setPassword(temp);
                    userList.add(user); // Adding user record to list
                } while (mCursor.moveToNext());
            }
        }
        mCursor.close();
        db.close();

        return userList;     //return user list
    }
    /**
     * This method is to check user exist or not
     * @param username
     * @param password
     * @return
     */
    public Boolean checkUser (String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID}; //Array of columns to fetch
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
        String[] selectionArs = {email};        //Selection argument
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
