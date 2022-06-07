package com.example.vietfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView pageNameTextView;
    private int id;
    private String text;
    private Boolean back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pageNameTextView = findViewById(R.id.pageNameTextView);
        startAnimation();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        text = intent.getStringExtra("screen");
        back = intent.getBooleanExtra("back",false);
        switch(text){
            case "ingredient":
                pageNameTextView.setText("Recipe Ingredients");
                break;
            case "instruction":
                pageNameTextView.setText("Recipe Instructions");

        }
        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (text.equals("ingredient")) {
                        if(!back) {
                            Intent intent1 = new Intent(SplashActivity.this, IngredientActivity.class);
                            intent1.putExtra("id", id);
                            startActivity(intent1);
                            finish();
                        }
                        return true;
                    } else if (text.equals("instruction")) {
                        Intent intent1 = new Intent(SplashActivity.this, InstructionActivity.class);
                        intent1.putExtra("id", id);
                        startActivity(intent1);
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });
    }


    public void startAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        pageNameTextView.startAnimation(animation);
    }

}