package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class EditActivity2 extends BaseActivity2{
    private ImageView line;
    private int id, ingredientId = 0;
    private Recipe lastIDRecipe;
    private ArrayList<List<String>> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        line = findViewById(R.id.lineImageView3);
        line.setVisibility(View.GONE);

        addButton.setText("Next");
        cancelButton.setText("Back");
        recipeDatabaseManager = new RecipeDatabaseManager(EditActivity2.this);
        recipeList= recipeDatabaseManager.readData();
        Intent intent  = getIntent();
        id = intent.getIntExtra("id",-1);
        lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(id);

        lastIDRecipe.getIngredient();
        tempList = lastIDRecipe.setIngredientStringtoArray();
        paintIngredient();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientEditText.getText().toString().trim().length() > 0 && amountEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0 && urlEditText.getText().toString().trim().length() > 0) {
                    ingredientTextView.setText("Ingredient List");
                    updateIngredient();

                    Recipe updatedRecipe = new Recipe(lastIDRecipe.getId(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(),
                            lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), tempList, lastIDRecipe.getInstruction());
                    updatedRecipe.setIngredientArraytoString();

                    recipeDatabaseManager.updateData(updatedRecipe);
                }
                else ingredientTextView.setText("Ingredient List - Please fill out all fields!");

                if(ingredientId>=getIngredientAmount()){
                    Intent intent = new Intent(EditActivity2.this, EditActivity3.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                }
                else if (ingredientId >= 0) {
                    ingredientId++;
                    paintIngredient();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientId==0){
                    Intent intent = new Intent(EditActivity2.this, EditActivity1.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                }
                else if (ingredientId<getIngredientAmount()+1&& ingredientId > 0) {
                    ingredientId--;
                    paintIngredient();
                }

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(ingredientEditText.getText().toString().trim().length() > 0 && amountEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0 && urlEditText.getText().toString().trim().length() > 0){
                    updateIngredient();
                    Recipe updatedRecipe = new Recipe(lastIDRecipe.getId(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(), lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), tempList, lastIDRecipe.getInstruction());

                    updatedRecipe.setIngredientArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);

                    Intent intent = new Intent(EditActivity2.this, EditActivity3.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                }
                else ingredientTextView.setText("Ingredient List - Please add at least one ingredient!");

            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity2.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_add1;
    }

    public int getIngredientAmount(){
        return lastIDRecipe.getRecipeIngredient().size() - 1;
    }


    public void updateIngredient(){
        tempList.get(ingredientId).set(0,componentEditText.getText().toString());
        tempList.get(ingredientId).set(1,ingredientEditText.getText().toString());
        tempList.get(ingredientId).set(2,amountEditText.getText().toString());
        tempList.get(ingredientId).set(3,urlEditText.getText().toString());
    }


    public void paintIngredient() {
        if (!tempList.isEmpty()){
            componentEditText.setText(tempList.get(ingredientId).get(0));
            ingredientEditText.setText(tempList.get(ingredientId).get(1));
            amountEditText.setText(tempList.get(ingredientId).get(2));
            urlEditText.setText(tempList.get(ingredientId).get(3));
        }
    }
}


