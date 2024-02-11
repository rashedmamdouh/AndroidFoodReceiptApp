package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private DishDbHelper dbHelper;

    // Constructor to initialize dbHelper
    public Utils(DishDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertDishData(String name, String img, String ingredients, String preparation,String category) {
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("image", img);
        values.put("ingredients", ingredients);
        values.put("preparation", preparation);
        values.put("category", category);

        // Insert the new row
        long newRowId = database.insert("dishes", null, values);
        dbHelper.close();

    }

    public List<Dish> getAllDishes() {
        SQLiteDatabase database=dbHelper.getWritableDatabase();

        List<Dish> dishes = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM dishes", null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Dish dish = new Dish();
                    dish.setId(cursor.getInt(0));
                    dish.setName(cursor.getString(1));
                    dish.setImagePath(cursor.getString(2));
                    dish.setIngredients(cursor.getString(3));
                    dish.setPreparation(cursor.getString(4));
                    dish.setCategory(cursor.getString(5));
                    dishes.add(dish);

                }
            } finally {
               dbHelper.close();
            }

        }
        return dishes;
    }
    public Dish getDish(int id) {
        Dish dish = new Dish();
        try (SQLiteDatabase database = dbHelper.getWritableDatabase();
             Cursor cursor = database.rawQuery("SELECT * FROM dishes WHERE _id=?", new String[]{String.valueOf(id)})) {
            if (cursor != null && cursor.moveToFirst()) {
                dish.setId(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dish.setImagePath(cursor.getString(2));
                dish.setIngredients(cursor.getString(3));
                dish.setPreparation(cursor.getString(4));
                dish.setCategory(cursor.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbHelper.close();
        }


            return dish;
        }


    public List<Dish> recommended(Dish fav_dish) {
        List<Dish> recommendedDishes = new ArrayList<>();

        try (SQLiteDatabase database = dbHelper.getWritableDatabase();
             Cursor cursor = database.rawQuery(
                     "SELECT * FROM dishes WHERE category = ? AND _id <> ?",
                     new String[]{fav_dish.getCategory(), String.valueOf(fav_dish.getId())})) {

            while (cursor != null && cursor.moveToNext()) {
                Dish dish = new Dish();
                dish.setId(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dish.setImagePath(cursor.getString(2));
                dish.setIngredients(cursor.getString(3));
                dish.setPreparation(cursor.getString(4));
                recommendedDishes.add(dish);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }

        return recommendedDishes;
    }

}


