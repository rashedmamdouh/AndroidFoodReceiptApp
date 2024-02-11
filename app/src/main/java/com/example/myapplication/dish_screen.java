package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;


import java.util.List;

public class dish_screen extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_screen);

        // Check if the app has permission to read external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, proceed to load images
            loadDishImages();
        } else {
            // If permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }
    }

    private void loadDishImages() {
        Utils utils = new Utils(new DishDbHelper(this));
        List<Dish> dishes = utils.getAllDishes();

        LinearLayout container = findViewById(R.id.container);
        int i = 1;

        for (Dish dish : dishes) {
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
            productIcon.setOnClickListener(this);
            productIcon.setId(dish.getId());

//            productIcon.setScaleType(ImageView.ScaleType.FIT_XY);

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


    @Override
    public void onClick(View v) {
        if (v instanceof ImageButton) {

            int imageButtonId = v.getId();
            Utils utils = new Utils(new DishDbHelper(this));
            Dish dish=utils.getDish(imageButtonId);

            Intent intent = new Intent(dish_screen.this, view_dish.class);
            intent.putExtra("dish_id",""+dish.getId());
            intent.putExtra("dish_name",dish.getName());
            intent.putExtra("dish_img",dish.getImagePath());
            intent.putExtra("dish_ing",dish.getIngredients());
            intent.putExtra("dish_prep",dish.getPreparation());
            startActivity(intent);
        }
    }


}
