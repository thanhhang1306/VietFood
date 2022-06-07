package com.example.vietfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class StartActivity extends AppCompatActivity {
    private SliderView sliderView;
    private Button loginButton, registerButton;

    // An array of image for the Image Slider
    private int[] image = {
            R.drawable.banh_cat,
            R.drawable.banh_chon,
            R.drawable.banh_my,
            R.drawable.banh_xeo,
            R.drawable.banh_cuon,
            R.drawable.bun_mam,
            R.drawable.cafe_sua,
            R.drawable.com_dau,
            R.drawable.nam,
            R.drawable.pho
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Initialize the two buttons
        loginButton=findViewById(R.id.logInButton2);
        registerButton=findViewById(R.id.registerButton2);

        // This method is ran when the "Login" Button is clicked. It's main job is to redirect the user to the Login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // This method is ran when the "Register" Button is clicked. It's main job is to redirect the user to the Register page
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        // To initialize and set the Photo Slider
        sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(image);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }
}