package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class EditActivity1 extends BaseActivity1 {
    private int id;
    private Spinner dropDownSpinner;
    private ImageView lineImageView;
    private Recipe lastIDRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeDatabaseManager = new RecipeDatabaseManager(EditActivity1.this);
        dropDownSpinner=findViewById(R.id.spinner);
        String[] items = new String[]{"Main Course", "Dessert", "Drink"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownSpinner.setAdapter(adapter);

        Intent intent  = getIntent();
        id = intent.getIntExtra("id",-1);
        lastIDRecipe = recipeDatabaseManager.getRecipeInfoFromID(id);
        repaintIngredient();

        lineImageView = findViewById(R.id.lineImageView2);
        lineImageView.setVisibility(View.GONE);
        messageTextView.setText("Edit Recipes!");


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id >= 0 && nameEditText.getText().toString().trim().length() > 0 && servingEditText.getText().toString().trim().length() > 0
                        && descriptionEditText.getText().toString().trim().length() > 0
                        && imageURLEditText.getText().toString().trim().length() > 0) {

                    Recipe updatedRecipe = new Recipe(id,nameEditText.getText().toString(),dropDownSpinner.getSelectedItem().toString(),servingEditText.getText().toString(),descriptionEditText.getText().toString(),imageURLEditText.getText().toString(),lastIDRecipe.getIngredient(),lastIDRecipe.getInstruction());
                    recipeDatabaseManager.updateData(updatedRecipe);

                    Intent intent = new Intent(EditActivity1.this, EditActivity2.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else ingredientTextView.setText("General Info. - Please enter all fields!");
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id >= 0) {
                    Recipe deletedRecipe = new Recipe(id,nameEditText.getText().toString(),dropDownSpinner.getSelectedItem().toString(),servingEditText.getText().toString(),descriptionEditText.getText().toString(),imageURLEditText.getText().toString());
                    recipeDatabaseManager.deleteData(deletedRecipe.getId());

                    Intent intent = new Intent(EditActivity1.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addMenuTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity1.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_add;
    }

    public void repaintIngredient(){
        nameEditText.setText(lastIDRecipe.getName());
        servingEditText.setText(lastIDRecipe.getServings());
        descriptionEditText.setText(lastIDRecipe.getDescription());
        for (int i=1;i<dropDownSpinner.getCount();i++){
            if (dropDownSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(lastIDRecipe.getCuisineType()))
                dropDownSpinner.setSelection(i);
        }
        imageURLEditText.setText(lastIDRecipe.getImageURL());
    }
}
