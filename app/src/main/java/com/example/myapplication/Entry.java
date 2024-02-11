package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Entry extends AppCompatActivity implements OnClickListener {
    Button dish;
    ImageButton fav,add;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //Assignments
        dish = (Button) findViewById(R.id.disc);
        fav = (ImageButton) findViewById(R.id.favbutton);
        add = (ImageButton) findViewById(R.id.addbutton);
        sp = getSharedPreferences("food_info", Context.MODE_PRIVATE);

        //onClick Listeners
        dish.setOnClickListener(this);
        fav.setOnClickListener(this);
        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == dish) {
            startActivity(new Intent(Entry.this, dish_screen.class));
        }else if(v == fav){
            startActivity(new Intent(Entry.this,fav_dish.class));
        }else if(v == add){
            startActivity(new Intent(Entry.this,Add_dish.class));
        }

    }
}