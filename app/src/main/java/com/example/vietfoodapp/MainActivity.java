package com.example.vietfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vietfoodapp.AddActivity;
import com.example.vietfoodapp.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ImageView lineImageView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Set the line below the Edit Menu to be invisible
        lineImageView = findViewById(R.id.lineImageView3);
        lineImageView.setVisibility(View.GONE);

        helloTextView.setText("Good day, hope you are \ndoing well!");

        /*
         * This method is ran when the "Add Menu" TextView is clicked. It's main job is to
         * redirect the user from the All Menu page to the Add Menu page.
         */
        addOneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*
         * This method is ran when the "Edit Menu" TextView is clicked. It's main job is to
         * redirect the user from the All Menu page to the Edit Menu page.
         */
        editOneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
               finish();
            }
        });
    }

    // From the parent BaseActivity class
    @Override
    protected int getLayoutResourceID(){
        return R.layout.activity_main;
    }



}