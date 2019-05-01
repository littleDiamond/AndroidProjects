package com.examples.myreceipts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class UserDbHandler extends SQLiteOpenHelper {
   private static final int DB_VERSION = 1;
   private static final String DB_NAME = "register.db";
   private static final String TABLE_Users = "Register user";
   private static final String KEY_ID = "id";
   private static final String KEY_NAME = "name";           //Modify
   private static final String KEY_EMAIL = "email";         //Modify
   private static final String KEY_PASSWORD = "password";   //Modify

    // Add the constructor from the error shown as we made this class a subclass of the SQLiteHelper
    public UserDbHandler(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

   // Modify the argument name to db of the onCreate() method.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a SQL query that will record the user detail table
        String USER_CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                                    + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                                    + KEY_NAME + " TEXT,"
                                    + KEY_EMAIL + " TEXT,"
                                    + KEY_PASSWORD + " TEXT"+ ")";

        db.execSQL(USER_CREATE_TABLE);
    }

    //Add some codes for onUpgrade method to drop the old table
    // if it exist and re-create it if there's a new table to be created
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //the second arg refers to old version and the third arg refers to the new version
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);

        // Create tables again
        onCreate(db);
    }

    //Add a method insertUserDetails to create new users
    public long addUser(String name, String email, String password){
        //Add the required code and call the necessary methods
        SQLiteDatabase db = this.getWritableDatabase();

        //Get the data repository in write mode
        //Create a new map of values, where column names are the key
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_EMAIL, email);
        cValues.put(KEY_PASSWORD, password);

        //Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close(); // Close the db after insertion

        return newRowId;
    }

    public boolean checkuser (String username, String password){
        String[] rows = { KEY_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = KEY_NAME + "=?" + " and " + KEY_PASSWORD + "=?";
        String[] selectionArs = { username, password };
        Cursor cursor = db.query(TABLE_Users,rows,selection,selectionArs,
                                null, null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if( count>0 )
            return true;
        else
            return false;
    }
}
