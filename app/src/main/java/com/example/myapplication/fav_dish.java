package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class fav_dish extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_dish);
        List<Dish>fav_dishes=getFav();
        TextView titleTextView=createText("My Favourite");
        LinearLayout container = findViewById(R.id.container);
        container.addView(titleTextView);
        showLayout(fav_dishes,container);

        List<Dish> rec_dishes = getRec(fav_dishes);
        if (!rec_dishes.isEmpty()) {
            TextView recTextView = createText("Recommended");
            LinearLayout rec_container = findViewById(R.id.container);
            rec_container.addView(recTextView);
            showLayout(rec_dishes, rec_container);
        }
    }
    private List<Dish> getFav() {
        Utils2 utils2 = new Utils2(new FavDbHelper(this));
        List<Integer> ids = utils2.getAllFav();
        List<Dish>fav_dishes = new ArrayList<Dish>();
        Utils utils = new Utils(new DishDbHelper(this));
        for (int i:ids){
            fav_dishes.add(utils.getDish(i));
        }
        return fav_dishes;
    }
    private List<Dish> getRec(List<Dish> FavDishes) {
        List<Dish> Recommended= new ArrayList<Dish>();
        Utils utils = new Utils(new DishDbHelper(this));
        for (Dish dish:FavDishes){
            for(Dish dish2:utils.recommended(dish))
                Recommended.add(dish2);
        }
        return Recommended;
    }


    @Override
    public void onClick(View v) {
        if (v instanceof ImageButton) {
            int imageButtonId = v.getId();
            Utils utils = new Utils(new DishDbHelper(this));
            Dish dish=utils.getDish(imageButtonId);
            Intent intent = new Intent(fav_dish.this, view_dish.class);
            intent.putExtra("dish_id",""+dish.getId());
            intent.putExtra("dish_name",dish.getName());
            intent.putExtra("dish_img",dish.getImagePath());
            intent.putExtra("dish_ing",dish.getIngredients());
            intent.putExtra("dish_prep",dish.getPreparation());
            startActivity(intent);
        }
    }
    public TextView createText(String str){
        TextView titleTextView = new TextView(this);
        titleTextView.setText(str);
        titleTextView.setTextColor(Color.parseColor("#FF0000"));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        titleTextView.setPadding(180, 16, 0, 16);
        titleTextView.setTypeface(null, Typeface.BOLD);
        return titleTextView;
    }

    public void showLayout(List<Dish>fav_dishes, LinearLayout container){
        int i = 1;
        for (Dish dish : fav_dishes) {
            if (i % 2 == 1) {
                LinearLayout rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                rowLayout.setGravity(Gravity.CENTER);

                container.addView(rowLayout);
            }

            LinearLayout productLayout = new LinearLayout(this);
            productLayout.setOrientation(LinearLayout.VERTICAL);
            productLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f));

            ImageButton productIcon = new ImageButton(this);
            productIcon.setBackgroundResource(R.color.back_ground);
            productIcon.setId(dish.getId());
            productIcon.setOnClickListener(this);
            RequestOptions requestOptions = new RequestOptions()
                    .override(300, 200)
                    .centerCrop();

            Glide.with(this)
                    .load(dish.getImagePath())
                    .apply(requestOptions)
                    .into(productIcon);
            productIcon.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView productName = new TextView(this);
            productName.setText(dish.getName());
            productName.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            productName.setPadding(30, 0, 0, 0);
            productName.setTextColor(Color.WHITE);
            productName.setTypeface(null, Typeface.BOLD);
            productName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            productLayout.addView(productIcon);
            productLayout.addView(productName);

            LinearLayout lastRow = (LinearLayout) container.getChildAt(container.getChildCount() - 1);
            lastRow.addView(productLayout);

            if (i % 2 == 0) {
                View space = new View(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        70));
                container.addView(space);
            }
            i++;
        }
    }
}