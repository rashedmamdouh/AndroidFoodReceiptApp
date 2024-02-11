package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DishDbHelper.java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavDbHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "fav_dishes.db";
        private static final int DATABASE_VERSION = 2;

        // Define your table structure here

    public FavDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
        public void onCreate(SQLiteDatabase db) {
            // Create your dishes table here
            String createTable = "CREATE TABLE fav_dishes (_id INTEGER PRIMARY KEY AUTOINCREMENT, Fav_id INTEGER UNIQUE);";
            db.execSQL(createTable);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS fav_dishes");
            onCreate(db);
        }


    }


