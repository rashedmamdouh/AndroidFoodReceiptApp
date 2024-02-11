package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class view_dish extends AppCompatActivity {
ImageView dish_img;
TextView ing,prep;
ImageButton fav;
    String dish_name ;
    String img_path ;
    String dish_ing ;
    String dish_prep;
    String dish_id;
    Intent intent=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dish);
        dish_img = (ImageView) findViewById(R.id.dish_img);
        ing = (TextView) findViewById(R.id.ing);
        prep = (TextView) findViewById(R.id.prep);
        fav = (ImageButton) findViewById(R.id.fav);

        //get data sent through intent
         intent = getIntent();
         dish_name = intent.getStringExtra("dish_name");
         img_path = intent.getStringExtra("dish_img");
         dish_ing = intent.getStringExtra("dish_ing");
         dish_prep = intent.getStringExtra("dish_prep");
         dish_id=intent.getStringExtra("dish_id");

        //substitute
        RequestOptions requestOptions = new RequestOptions()
                .override(300, 200)
                .centerCrop();

        Glide.with(this)
                .load(img_path)
                .apply(requestOptions)
                .into(dish_img);

        dish_img.setContentDescription(dish_name);
        ing.setText(dish_ing);
        prep.setText(dish_prep);



        //fav button
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils2 utils2 = new Utils2(new FavDbHelper(view_dish.this));

                if (!utils2.faveExist(Integer.parseInt(dish_id))) {
                    utils2.insertFav(Integer.parseInt(dish_id));
                    Toast.makeText(view_dish.this, "Added To Favourite", Toast.LENGTH_LONG).show();
                } else {
                    utils2.deleteFav(Integer.parseInt(dish_id));
                    Toast.makeText(view_dish.this, "Deleted From Favourite", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(view_dish.this, fav_dish.class));
            }
        });
    }
}
