package com.example.vietfoodapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity  {
    protected TextView addOneTextView, editOneTextView, mainTextView, helloTextView;
    protected Context context;
    protected RecipeDatabaseManager recipeDatabaseManager;
    protected Toolbar toolBar;
    protected List<Recipe> recipeList;

    private RecyclerView recyclerView;
    private RecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeDatabaseManager = new RecipeDatabaseManager(this);
        recipeList = recipeDatabaseManager.readData();

        mainTextView = findViewById(R.id.allMenuTextView);
        addOneTextView = findViewById(R.id.addMenuTextView);
        editOneTextView = findViewById(R.id.editMenuTextView);
        helloTextView = findViewById(R.id.helloTextView);

        recyclerView = findViewById(R.id.recipeListRecyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new RecycleViewAdapter(recipeList,this);
        recyclerView.setAdapter(mAdapter);

        // toolbar;
        toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
    }

    protected abstract int getLayoutResourceID();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu,menu);
        MenuItem item = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipeList = recipeDatabaseManager.readData();
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.foodNameAZMenu:
                Collections.sort(recipeList, Recipe.recipeNameComparatorAZ);
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.foodNameZAMenu:
                Collections.sort(recipeList,Recipe.recipeNameComparatorZA);
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.foodTypeMenu:
                Collections.sort(recipeList,Recipe.recipeTypeComparator);
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
