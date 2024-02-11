package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DishDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dishes.db";
    private static final int DATABASE_VERSION = 2;

    // Define your table structure here

    public DishDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your dishes table here
        String createTable = "CREATE TABLE dishes (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image TEXT, ingredients TEXT, preparation TEXT,category TEXT);";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dishes");
        onCreate(db);
    }


}
