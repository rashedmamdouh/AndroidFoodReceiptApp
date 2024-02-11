package com.example.myapplication;

// Dish.java
public class Dish {
    private int id;
    private String name;
    private String ImagePath;
    private String ingredients;
    private String preparation;

    private String Category;
    public void setCategory(String category) {
        Category = category;
    }

    public String getCategory() {
        return Category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String image) {
        this.ImagePath= image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
