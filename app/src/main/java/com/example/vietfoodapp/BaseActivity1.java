package com.example.vietfoodapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public abstract class BaseActivity1 extends AppCompatActivity {
    protected Button confirmButton, cancelButton, deleteButton;
    protected TextView allMenuTextView, editMenuTextView, addMenuTextView, messageTextView,ingredientTextView;
    protected List<Recipe> recipeList;
    protected EditText nameEditText, descriptionEditText, imageURLEditText, servingEditText;
    protected RecipeDatabaseManager recipeDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        RecipeDatabaseManager databaseHelper = new RecipeDatabaseManager(this);
        recipeList = databaseHelper.readData();

        deleteButton = findViewById(R.id.deleteButton);
        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);
        allMenuTextView = findViewById(R.id.allMenuTextView2);
        editMenuTextView = findViewById(R.id.editMenuTextView2);
        addMenuTextView = findViewById(R.id.addMenuTextView2);
        messageTextView = findViewById(R.id.addTextView);
        ingredientTextView = findViewById(R.id.ingredientTextView2);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        imageURLEditText = findViewById(R.id.imageURLEditText);
        servingEditText = findViewById(R.id.servingEditText);

        ingredientTextView.setText("General Information");
    }

    protected abstract int getLayoutResourceID();
}
