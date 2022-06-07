package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class InstructionActivity extends AppCompatActivity {

    private TextView recipeNameTextView, instructionComponentTextView, instructionSumTextView, instructionDetailedTextVIew;
    private Button backButton, skipButton, nextButton;
    private RecipeDatabaseManager recipeDatabaseManager;
    private int recipeId, instructionId = 0;
    private TextView addMenuTextView,editMenuTextView, allMenuTextView;
    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        recipeNameTextView = findViewById(R.id.recipeNameTextView);
        instructionComponentTextView = findViewById(R.id.instructionComponentTextView);
        instructionDetailedTextVIew = findViewById(R.id.instructionDetailedTextView);
        instructionSumTextView = findViewById(R.id.instructionSummaryTextView);
        backButton = findViewById(R.id.backButton);
        skipButton = findViewById(R.id.skipButton);
        nextButton = findViewById(R.id.nextButton);

        addMenuTextView = findViewById(R.id.addMenuTextView4);
        editMenuTextView = findViewById(R.id.editMenuTextView4);
        allMenuTextView = findViewById(R.id.allMenuTextView4);

        recipeDatabaseManager = new RecipeDatabaseManager(this);

        Intent intent  = getIntent();
        recipeId = intent.getIntExtra("id",-1);
        selectedRecipe = recipeDatabaseManager.getRecipeInfoFromID(recipeId);
        repaintInstruction();

        /*
         * This method is ran when the "Back" Button is clicked. It's main job is to redirect the user
         * to the previous instruction or to the All Menu screen.
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If it is the first instruction, go back to All Menu (MainActivity class).
                if(instructionId==0){
                    Intent intent = new Intent(InstructionActivity.this, SplashActivity.class);
                    intent.putExtra("id",recipeId);
                    intent.putExtra("screen", "ingredient");
                    intent.putExtra("back", false);
                    startActivity(intent);
                    finish();
                }
                // Else move to the previous instruction.
                else if (instructionId<getInstructionAmount()+1&& instructionId > 0) {
                    instructionId--;
                    repaintInstruction();
                }
            }
        });

        /*
         * This method is ran when the "Next" Button is clicked. It's main job is to redirect the user
         * to the next instruction or to the All Menu screen.
         */
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If it is the last instruction, go back to All Menu (MainActivity class).
                if(instructionId>=getInstructionAmount()){
                    Intent intent = new Intent(InstructionActivity.this, MainActivity.class);
                    intent.putExtra("id",recipeId);
                    startActivity(intent);
                    finish();
                }
                // Else move to the next instruction.
                else if (instructionId >= 0) {
                    instructionId++;
                    repaintInstruction();
                }
            }
        });

        // This method is ran when the "Skip" Button is clicked. It directs user back to the All Menu screen.
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionActivity.this, MainActivity.class);
                intent.putExtra("id",recipeId);
                startActivity(intent);
                finish();
            }
        });

        // These three methods direct the user depending on the TextView (selection) they clicked on the top.
        addMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionActivity.this, EditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        allMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void repaintInstruction(){
        if(selectedRecipe.getId() == recipeId && !selectedRecipe.setInstructionStringtoArray().isEmpty()){
            recipeNameTextView.setText(selectedRecipe.getName());
            instructionSumTextView.setText(selectedRecipe.setInstructionStringtoArray().get(instructionId).get(1));
            if(selectedRecipe.setInstructionStringtoArray().get(instructionId).get(0).equals("N/A"))
                instructionComponentTextView.setVisibility(View.GONE);
            else {
                instructionComponentTextView.setVisibility(View.VISIBLE);
                instructionComponentTextView.setText(selectedRecipe.setInstructionStringtoArray().get(instructionId).get(0));
            }
            instructionDetailedTextVIew.setText(selectedRecipe.setInstructionStringtoArray().get(instructionId).get(2));
        }
    }


    public int getInstructionAmount(){
        return selectedRecipe.getRecipeInstruction().size() - 1;
    }

}
