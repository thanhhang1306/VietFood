package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private TextView recipeNameTextView, ingredientNameTextView, ingredientComponentTextView, ingredientAmountTextView;
    private ImageView ingredientImage;
    private Button backButton, skipButton, nextButton;
    private RecipeDatabaseManager recipeDatabaseManager;
    private int recipeId, ingredientId = 0;
    private TextView addMenuTextView,editMenuTextView, allMenuTextView;
    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        // Initialize graphics aspect from the layout
        recipeNameTextView = findViewById(R.id.recipeNameTextView);
        ingredientImage = findViewById(R.id.ingredientImageImageView);
        ingredientComponentTextView = findViewById(R.id.ingredientComponentTextView);
        ingredientNameTextView = findViewById(R.id.ingredientNameTextView);
        ingredientAmountTextView = findViewById(R.id.ingredientAmountTextView);
        backButton = findViewById(R.id.backButton);
        skipButton = findViewById(R.id.skipButton);
        nextButton = findViewById(R.id.nextButton);
        addMenuTextView = findViewById(R.id.addMenuTextView4);
        editMenuTextView = findViewById(R.id.editMenuTextView4);
        allMenuTextView = findViewById(R.id.allMenuTextView4);

        // Initialize LoginRegisterDatabaseManager to access and edit database
        recipeDatabaseManager = new RecipeDatabaseManager(this);

        Intent intent  = getIntent();
        recipeId = intent.getIntExtra("id",-1);
        selectedRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeId);
        repaintIngredient();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientId==0){
                    Intent intent = new Intent(IngredientActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (ingredientId<getIngredientAmount()+1&& ingredientId > 0) {
                    ingredientId--;
                    repaintIngredient();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientId>=getIngredientAmount()){
                    Intent intent = new Intent(IngredientActivity.this, SplashActivity.class);
                    intent.putExtra("id",recipeId);
                    intent.putExtra("screen","instruction");
                    intent.putExtra("back",false);
                    startActivity(intent);
                }
                else if (ingredientId >= 0) {
                    ingredientId++;
                    repaintIngredient();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IngredientActivity.this, SplashActivity.class);
                intent.putExtra("id",recipeId);
                intent.putExtra("screen","instruction");
                intent.putExtra("back",false);
                startActivity(intent);
            }
        });

        addMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        editMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /*
     * This method repaints the ingredients when the ingredients is changed by: First, validating that the Ingredient
     * ArrayList<List<String>> is not empty. Then, the value of the TextView is set based on the index of each characteristics
     * in the inner List. The component is index 0, ingredient name is index 1, amount is index 2, and picture URL is index 3 for
     * every ingredient.
     */
    public void repaintIngredient(){
        if(!selectedRecipe.setIngredientStringtoArray().isEmpty()){
            recipeNameTextView.setText(selectedRecipe.getName());
            ingredientNameTextView.setText(selectedRecipe.setIngredientStringtoArray().get(ingredientId).get(1));
            // For ingredient component, if users do not know they enter "N/A." This will make the Component TextView invisible.
            if(selectedRecipe.setIngredientStringtoArray().get(ingredientId).get(0).equals("N/A"))
                ingredientComponentTextView.setVisibility(View.GONE);
            else {
                ingredientComponentTextView.setVisibility(View.VISIBLE);
                ingredientComponentTextView.setText(selectedRecipe.getRecipeIngredient().get(ingredientId).get(0));
            }
            ingredientAmountTextView.setText(selectedRecipe.getRecipeIngredient().get(ingredientId).get(2));
            // For the ImageView (cuisine photo), the Image Loader Library by bumptech "Glide" is utilized.
            Glide.with(this).load(selectedRecipe.setIngredientStringtoArray().get(ingredientId).get(3)).into(ingredientImage);
        }
    }

    // This method gets the amount of ingredients for the recipe
    public int getIngredientAmount(){
        return selectedRecipe.getRecipeIngredient().size() - 1;
    }
}
