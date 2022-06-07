package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class EditActivity3 extends BaseActivity3{
    private ImageView line;
    private int id, instructionId = 0;
    private Recipe lastIDRecipe;
    private ArrayList<List<String>> tempList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        line = findViewById(R.id.lineImageView3);
        line.setVisibility(View.GONE);

        addButton.setText("Next");
        cancelButton.setText("Back");
        recipeDatabaseManager = new RecipeDatabaseManager(EditActivity3.this);
        recipeList = recipeDatabaseManager.readData();
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(id);
        tempList = lastIDRecipe.setInstructionStringtoArray();

        paintInstruction();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(componentEditText.getText().toString().trim().length() > 0 && summaryEditText.getText().toString().trim().length() > 0
                        && detailedEditText.getText().toString().trim().length() > 0) {
                    instructionTextView.setText("Instruction List");
                    updateInstruction();
                    Recipe updatedRecipe = new Recipe(lastIDRecipe.getId(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(), lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), lastIDRecipe.getIngredient(),tempList);
                    updatedRecipe.setInstructionArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);
                }
                else instructionTextView.setText("Instruction List - Please fill out all fields!");

                if(instructionId>=getInstructionAmount()){
                    Intent intent = new Intent(EditActivity3.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (instructionId >= 0) {
                    instructionId++;
                    paintInstruction();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(instructionId==0){
                    Intent intent = new Intent(EditActivity3.this, EditActivity2.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                }
                else if (instructionId<getInstructionAmount()+1&& instructionId > 0) {
                    instructionId--;
                    paintInstruction();
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailedEditText.getText().toString().trim().length() > 0 && summaryEditText.getText().toString().trim().length() > 0
                        && componentEditText.getText().toString().trim().length() > 0){
                    updateInstruction();
                    Recipe updatedRecipe = new Recipe(lastIDRecipe.getId(), lastIDRecipe.getName(), lastIDRecipe.getCuisineType(), lastIDRecipe.getServings(),
                            lastIDRecipe.getDescription(), lastIDRecipe.getImageURL(), lastIDRecipe.getIngredient(),tempList);
                    updatedRecipe.setInstructionArraytoString();
                    recipeDatabaseManager.updateData(updatedRecipe);

                    Intent intent = new Intent(EditActivity3.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else instructionTextView.setText("Instruction List - Please add at least one instruction!");
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity3.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_add1;
    }

    public int getInstructionAmount(){
        int amount = 0;
        amount = lastIDRecipe.getRecipeInstruction().size() - 1;
        return amount;
    }

    public void updateInstruction(){
        tempList.get(instructionId).set(0,componentEditText.getText().toString());
        tempList.get(instructionId).set(1,summaryEditText.getText().toString());
        tempList.get(instructionId).set(2,detailedEditText.getText().toString());
    }

    public void paintInstruction() {
        if (!tempList.isEmpty()){
            componentEditText.setText(tempList.get(instructionId).get(0));
            summaryEditText.setText(tempList.get(instructionId).get(1));
            detailedEditText.setText(tempList.get(instructionId).get(2));
        }
    }
}


