package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EditActivity extends BaseActivity {
    private ImageView lineImageView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        lineImageView = findViewById(R.id.lineImageView);
        lineImageView.setVisibility(View.GONE);

        helloTextView.setText("Edit Recipes! \nClick on cuisine's display to edit");

        addOneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceID(){
            return R.layout.activity_main;
        }
}
