package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends BaseActivity1 {
    private Spinner dropDownSpinner;
    private ImageView lineImageView;
    private boolean cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageTextView.setText("Add Recipes!");

        recipeDatabaseManager = new RecipeDatabaseManager(AddActivity.this);
        dropDownSpinner = findViewById(R.id.spinner);
        String[] items = new String[]{"Main Course", "Dessert", "Drink"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropDownSpinner.setPrompt("Please pick a dish type!");
        dropDownSpinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.activity_spinner, this));

        lineImageView = findViewById(R.id.lineImageView4);
        lineImageView.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        cancel = getIntent().getBooleanExtra("id",false);

        if(cancel){
             Recipe lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
             nameEditText.setText(lastIDRecipe.getName());
             servingEditText.setText(lastIDRecipe.getServings());
             imageURLEditText.setText(lastIDRecipe.getImageURL());
             descriptionEditText.setText(lastIDRecipe.getDescription());
             for (int i=1;i<dropDownSpinner.getCount();i++){
                if (dropDownSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(lastIDRecipe.getCuisineType())){
                    dropDownSpinner.setSelection(i);
                }
            }
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe newRecipe, updateRecipe;
                try{
                    if (nameEditText.getText().toString().trim().length() > 0 && servingEditText.getText().toString().trim().length() > 0 && descriptionEditText.getText().toString().trim().length() > 0
                            && imageURLEditText.getText().toString().trim().length() > 0 && dropDownSpinner.getSelectedItem() != null) {
                        ingredientTextView.setText("General Information");
                        if(!cancel) {
                            newRecipe = new Recipe(nameEditText.getText().toString(), dropDownSpinner.getSelectedItem().toString(), servingEditText.getText().toString(), descriptionEditText.getText().toString(), imageURLEditText.getText().toString());
                            recipeDatabaseManager.writeData(newRecipe);
                            Intent intent = new Intent(AddActivity.this, AddActivity1.class);
                            intent.putExtra("id",newRecipe.getId());
                            startActivity(intent);
                        }
                        if(cancel) {
                            Recipe lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
                            updateRecipe = new Recipe(lastIDRecipe.getId(),nameEditText.getText().toString(), dropDownSpinner.getSelectedItem().toString(), servingEditText.getText().toString(), descriptionEditText.getText().toString(), imageURLEditText.getText().toString(),lastIDRecipe.getIngredient(),lastIDRecipe.getInstruction());
                            updateRecipe.setRecipeIngredient(null);
                            updateRecipe.setRecipeInstruction(null);
                            recipeDatabaseManager.updateData(updateRecipe);
                            Intent intent1 = new Intent(AddActivity.this, AddActivity1.class);
                            intent1.putExtra("id",lastIDRecipe.getId());
                            startActivity(intent1);
                           }
                        }
                        else {
                            ingredientTextView.setText("General Info. - Please enter all fields!");
                            nameEditText.getText().clear();
                            servingEditText.getText().clear();
                            imageURLEditText.getText().clear();
                            descriptionEditText.getText().clear();
                            dropDownSpinner.setPrompt("Please pick a dish type!");
                            dropDownSpinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.activity_spinner, AddActivity.this));
                        }
                    }
                catch(Exception e){

                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancel) {
                    Recipe lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
                    recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                }
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancel) {
                    Recipe lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
                    recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                }
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancel) {
                    Recipe lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeDatabaseManager.getIDLastRow());
                    RecipeDatabaseManager recipeDatabaseManager = new RecipeDatabaseManager(AddActivity.this);
                    recipeDatabaseManager.deleteData(lastIDRecipe.getId());
                }
                Intent intent = new Intent(AddActivity.this, EditActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_add;
    }
}