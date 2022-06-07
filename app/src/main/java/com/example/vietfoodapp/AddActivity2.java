package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;

public class AddActivity2 extends BaseActivity3 {
    private ImageView line;
    private int id;
    private Recipe lastIDRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        line = findViewById(R.id.lineImageView5);
        line.setVisibility(View.GONE);

        Intent intent  = getIntent();
        id = intent.getIntExtra("id",-1);

        lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(summaryEditText.getText().toString().trim().length() > 0 && detailedEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0) {
                    addInstructions();
                    Recipe updatedRecipe = new Recipe(recipeDatabaseManager.getIDLastRow(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(), lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), lastIDRecipe.getIngredient(), instructions);
                    updatedRecipe.setInstructionArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);
                    instructionTextView.setText("Instructions");
                }
                else instructionTextView.setText("Instructions - Please fill out all fields!");
                summaryEditText.getText().clear();
                detailedEditText.getText().clear();
                componentEditText.getText().clear();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity2.this, AddActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(summaryEditText.getText().toString().trim().length() > 0 && detailedEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0){
                    addInstructions();
                    Recipe updatedRecipe = new Recipe(recipeDatabaseManager.getIDLastRow(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(), lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), lastIDRecipe.getIngredient(), instructions);
                    updatedRecipe.setInstructionArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);
                    instructionTextView.setText("Instructions");
                    Intent intent = new Intent(AddActivity2.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    lastIDRecipe=recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
                    if(lastIDRecipe.getInstruction() == null)
                        instructionTextView.setText("Instructions - Please add at least one instruction!");
                    else {

                        Intent intent = new Intent(AddActivity2.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                summaryEditText.getText().clear();
                detailedEditText.getText().clear();
                componentEditText.getText().clear();
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                Intent intent = new Intent(AddActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                Intent intent = new Intent(AddActivity2.this, EditActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void addInstructions(){
        if(componentEditText.getText().toString().trim().length() > 0 && summaryEditText.getText().toString().trim().length() > 0
                && detailedEditText.getText().toString().trim().length() > 0)
            instructions.add(Arrays.asList(componentEditText.getText().toString(),summaryEditText.getText().toString(),detailedEditText.getText().toString()));
    }

    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_add2;
    }




}
