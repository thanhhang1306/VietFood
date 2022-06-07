package com.example.vietfoodapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity2  extends AppCompatActivity {
    protected Button addButton, cancelButton, confirmButton;
    protected TextView allMenuTextView, editMenuTextView, addMenuTextView, ingredientTextView;
    protected List<Recipe> recipeList;
    protected EditText ingredientEditText, amountEditText, urlEditText, componentEditText;
    protected ArrayList<List<String>> ingredients = new ArrayList<List<String>>();
    protected RecipeDatabaseManager recipeDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add1);

        RecipeDatabaseManager databaseHelper = new RecipeDatabaseManager(this);
        recipeList = databaseHelper.readData();

        addButton = findViewById(R.id.addMoreButton);
        confirmButton = findViewById(R.id.confirmButton2);
        cancelButton = findViewById(R.id.cancelButton2);
        allMenuTextView = findViewById(R.id.allMenuTextView3);
        editMenuTextView = findViewById(R.id.editMenuTextView3);
        addMenuTextView = findViewById(R.id.addMenuTextView3);

        ingredientEditText = findViewById(R.id.ingredientEditText);
        amountEditText = findViewById(R.id.amountEditText);
        urlEditText = findViewById(R.id.imageURLEditText);
        componentEditText = findViewById(R.id.componentEditText);

        ingredientTextView = findViewById(R.id.ingredientTextView);
        ingredientTextView.setText("Ingredient List");
    }
    protected abstract int getLayoutResourceID();
}
