package com.examples.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DPHelper extends SQLiteOpenHelper {

    private  static  final int DB_VERSION = 1;
    private  static  final String DB_NAME = "userdb";
    private  static  final String TABLE_Users = "userdata";
    private  static  final String KEY_NAME = "name";
    private  static  final String KEY_ID = "id";
    private  static  final String KEY_LOC = "location";
    private  static  final String KEY_DESC = "designation";

    public DPHelper (Context context){
        super ( context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE" + TABLE_Users + "("
                + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + KEY_NAME + "TEXT,"
                + KEY_LOC + "TEXT,"
                + KEY_DESC +"TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(db);
    } //end of onUpgrade

    //create a methodto insert users
    void inserUserDetails( String name, String location, String designation ){
        SQLiteDatabase db this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, name);
        cValues.put(KEY_DESC, name);

        long newRowId = db.insert


    }
}
