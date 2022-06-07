package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;

public class AddActivity1 extends BaseActivity2 {
    private ImageView line;
    private Recipe lastIDRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecipeDatabaseManager recipeDatabaseManager = new RecipeDatabaseManager(AddActivity1.this);
        line = findViewById(R.id.lineImageView5);
        line.setVisibility(View.GONE);

        lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientEditText.getText().toString().trim().length() > 0 && amountEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0 && urlEditText.getText().toString().trim().length() > 0) {
                    addIngredients();
                    ingredientTextView.setText("Ingredient List");
                    Recipe updatedRecipe = new Recipe(recipeDatabaseManager.getIDLastRow(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(), lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), ingredients, lastIDRecipe.getInstruction());
                    updatedRecipe.setIngredientArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);
                }
                else ingredientTextView.setText("Ingredient List - Please fill out all fields!");
                ingredientEditText.getText().clear();
                amountEditText.getText().clear();
                componentEditText.getText().clear();
                urlEditText.getText().clear();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity1.this, AddActivity.class);
                intent.putExtra("id", true);
                startActivity(intent);
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(ingredientEditText.getText().toString().trim().length() > 0 && amountEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0 && urlEditText.getText().toString().trim().length() > 0){
                    addIngredients();
                    Recipe updatedRecipe = new Recipe(lastIDRecipe.getId(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(), lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(),ingredients, lastIDRecipe.getInstruction());
                    updatedRecipe.setIngredientArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);
                    ingredientTextView.setText("Ingredient List");
                    Intent intent = new Intent(AddActivity1.this, AddActivity2.class);
                    startActivity(intent);
                    finish();
               }
               else{
                    lastIDRecipe=recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
                    if(lastIDRecipe.getIngredient() == null)
                        ingredientTextView.setText("Ingredient List - Please add at least one ingredient!");
                    else {
                        Intent intent = new Intent(AddActivity1.this, AddActivity2.class);
                        startActivity(intent);
                        finish();
                    }
               }
               ingredientEditText.getText().clear();
               amountEditText.getText().clear();
               componentEditText.getText().clear();
               urlEditText.getText().clear();
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                Intent intent = new Intent(AddActivity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                Intent intent = new Intent(AddActivity1.this, EditActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void addIngredients(){
        if(ingredientEditText.getText().toString().trim().length() > 0 && amountEditText.getText().toString().trim().length() > 0
                && urlEditText.getText().toString().trim().length() > 0 && componentEditText.getText().toString().trim().length() > 0)
            ingredients.add(Arrays.asList(componentEditText.getText().toString(),ingredientEditText.getText().toString(),amountEditText.getText().toString(),urlEditText.getText().toString()));
    }

    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_add1;
    }
}
