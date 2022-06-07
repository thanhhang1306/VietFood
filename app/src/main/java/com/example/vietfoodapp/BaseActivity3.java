package com.example.vietfoodapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity3 extends AppCompatActivity {
    protected Button addButton, cancelButton, confirmButton;
    protected TextView allMenuTextView, editMenuTextView, addMenuTextView, instructionTextView;
    protected List<Recipe> recipeList;
    protected EditText summaryEditText, detailedEditText, componentEditText;
    protected ArrayList<List<String>> instructions = new ArrayList<List<String>>();
    protected RecipeDatabaseManager recipeDatabaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);

        recipeDatabaseManager = new RecipeDatabaseManager(this);
        recipeList = recipeDatabaseManager.readData();

        addButton = findViewById(R.id.addMoreButton);
        confirmButton = findViewById(R.id.confirmButton2);
        cancelButton = findViewById(R.id.cancelButton2);
        allMenuTextView = findViewById(R.id.allMenuTextView3);
        editMenuTextView = findViewById(R.id.editMenuTextView3);
        addMenuTextView = findViewById(R.id.addMenuTextView3);

        summaryEditText = findViewById(R.id.summaryEditText);
        detailedEditText = findViewById(R.id.detailedEditText);
        componentEditText = findViewById(R.id.componentEditText);

        instructionTextView = findViewById(R.id.instructionTextView);
        instructionTextView.setText("Instructions");
    }

    protected abstract int getLayoutResourceID();
}
