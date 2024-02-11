package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Utils2 {

        private FavDbHelper fvHelper;

        // Constructor to initialize dbHelper
        public Utils2(FavDbHelper fvHelper) {
            this.fvHelper = fvHelper;
        }

        public void insertFav(int id) {
            SQLiteDatabase database=fvHelper.getWritableDatabase();
            // Create a ContentValues object to store the data
            ContentValues values = new ContentValues();

            values.put("Fav_id", id);

            // Insert the new row
            long newRowId = database.insert("fav_dishes", null, values);
            fvHelper.close();

        }
    public boolean faveExist(int id) {
        SQLiteDatabase database = fvHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM fav_dishes WHERE Fav_id=?", new String[]{String.valueOf(id)});

        try {
            // Check if the cursor has any rows
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



        public List<Integer> getAllFav() {
            SQLiteDatabase database=fvHelper.getWritableDatabase();

            List<Integer> ids = new ArrayList<>();
            Cursor cursor = database.rawQuery("SELECT * FROM fav_dishes", null);
            if (cursor != null) {
                try {
                    while (cursor.moveToNext()) {
                        ids.add(cursor.getInt(1));
                    }
                } finally {
                    fvHelper.close();
                }

            }
            return ids;
        }


    public void deleteFav(int id) {
        SQLiteDatabase database = fvHelper.getWritableDatabase();
        // Specify the WHERE clause with the column name and its value
        String selection = "Fav_id=?";
        String[] selectionArgs = {String.valueOf(id)};
        // Delete the row
        int deletedRows = database.delete("fav_dishes", selection, selectionArgs);

        fvHelper.close();
    }

    }



