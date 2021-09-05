package com.example.ben_j.golfassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "stats.db";
    public static final String TABLE_NAME = "stats_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PAR";
    public static final String COL_3 = "SCORE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_1 +" integer primary key autoincrement, "+ COL_2 +", "+ COL_3 + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int par, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, par);  //column name and value
        contentValues.put(COL_3, score);
        long result = db.insert(TABLE_NAME, null, contentValues);  //table name, something, values
        if (result == -1) {
            return false;
        }
        return true;
    }


    public Cursor getAllData() {
        //Cursor provides random read-write access to y9our result
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

}
