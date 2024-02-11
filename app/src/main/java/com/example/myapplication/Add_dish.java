package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add_dish extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    private ImageView dishImageView;
    private EditText dishNameEditText;
    private EditText ingredientsEditText;
    private EditText instructionsEditText;
    private Spinner spinner;

    String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        dishImageView = findViewById(R.id.dishImageView);
        dishNameEditText = findViewById(R.id.editTextText);
        ingredientsEditText = findViewById(R.id.editTextText3);
        instructionsEditText = findViewById(R.id.editTextText4);

        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });


        spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"Meal", "Desserts", "Salads","Drinks","Snacks","Soups"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedCategory = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });


        Button addDishButton = findViewById(R.id.button);
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = dishNameEditText.getText().toString();
                String ingredients = ingredientsEditText.getText().toString();
                String instructions = instructionsEditText.getText().toString();
                String imagePath = getImagePathFromImageView(dishImageView);
                String category = selectedCategory.toString();


                Utils utils = new Utils(new DishDbHelper(Add_dish.this));
                utils.insertDishData(name, imagePath, ingredients, instructions,category);
                Toast.makeText(Add_dish.this, "Dish Added", Toast.LENGTH_LONG).show();
                Log.d("Imagg",imagePath);
                startActivity(new Intent(Add_dish.this,dish_screen.class));
            }
        });


    }
    private static final int PICK_IMAGE_REQUEST = 1;
    private void openImagePicker() {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }

            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                    dishImageView.setImageBitmap(selectedImageBitmap);
                    dishImageView.setTag(selectedImageUri.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getImagePathFromImageView(ImageView imageView) {
        Object tag = imageView.getTag();
        if (tag instanceof String) {
            return (String) tag;
        } else {
            return "";
        }
    }

}
