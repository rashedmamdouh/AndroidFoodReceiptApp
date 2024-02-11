package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(MainActivity.this,Entry.class));
                    finish();
            }
        },3000);


    }

    @Override
    protected void onStart() {
        super.onStart();
       // Utils utils = new Utils(new DishDbHelper(this));  // 'this' refers to your current context
//        utils.insertDishData("sii","sii","sii","sii");
//        utils.insertDishData("nii","nii","nii","nii");
//        utils.insertDishData("pii","pii","pii","pii");
        //List<Dish> dishes =utils.getAllDishes();
//        for ( Dish dish : dishes ){
//            Log.d("ppp:", " " + dish.getName());
//        }


    }
}