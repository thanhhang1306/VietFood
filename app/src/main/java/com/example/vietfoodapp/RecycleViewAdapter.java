package com.example.vietfoodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vietfoodapp.EditActivity;
import com.example.vietfoodapp.EditActivity1;
import com.example.vietfoodapp.R;
import com.example.vietfoodapp.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> implements Filterable {

    private List<Recipe> recipeList;
    private List<Recipe> recipeListAll;
    private Context context;

    // RecycleView.Adapter is the base class for the adapter associated with RecycleView
    public RecycleViewAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
        this.recipeListAll = new ArrayList<>(recipeList);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView foodDescription;
        TextView foodType;
        TextView foodName;
        TextView foodServing;
        ConstraintLayout parentLayout;

        // Initialize all the graphical elements for each recipe display on the All Menu page/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
            foodDescription = itemView.findViewById(R.id.foodDescriptionTextView);
            foodType = itemView.findViewById(R.id.foodTypeTextView);
            foodName = itemView.findViewById(R.id.foodNameTextView);
            foodServing = itemView.findViewById(R.id.foodServingTextView);
            parentLayout = itemView.findViewById(R.id.oneLineRecipeLayout);
        }
    }

    // This method creates a new RecycleView.ViewHolder and initializes private variables used by the RecyleView
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_recipe,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    // Called by the RecylerView to display data at a specified position
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Set the value of the graphical elements (initialized above) based on each Recipe information.
        holder.foodDescription.setText(recipeList.get(position).getDescription());
        holder.foodName.setText(recipeList.get(position).getName());
        holder.foodType.setText(recipeList.get(position).getCuisineType());
        holder.foodServing.setText(recipeList.get(position).getServings());
        // For the ImageView (cuisine photo), the Image Loader Library by bumptech "Glide" is utilized.
        Glide.with(this.context).load(recipeList.get(position).getImageURL()).into(holder.foodImageView);

        /*
         * The two following if() statements determine the Activity the user is on by checking their context.
         * Within the if() statements, the actions to be performed (Intent) after a specific RecyclerView box.
         */
        if(context instanceof EditActivity) {
            /*
             * This method is ran if the user clicked on a recipe in the EditActivity page. It sends users'
             * to the Edit Activity - General Information page (from the EditActivity1 class).
             */
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditActivity1.class);
                    intent.putExtra("id", recipeList.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }

        if(context instanceof MainActivity) {
            /*
             * This method is ran if the user clicked on a recipe in the MainActivity page. It sends users'
             * to the Ingredients page (from the IngredientActivity class).
             */
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SplashActivity.class);
                    intent.putExtra("id", recipeList.get(position).getId());
                    intent.putExtra("screen","ingredient");
                    context.startActivity(intent);
                }
            });
        }
    }

    // This method returns the total number of items in the data set held by the RecyclerView adapter.
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    /*
     * The base class Filterable was implemented on the top. A filterable class is able to have its data
     * constrained by a filter, serving the purpose for my application. Note that the Android developer
     * guide state that the Filterable classes are usually implemented in Adapter classes.
     */

    // This method returns a filter that can be utilized to constrain the data by filtering through a specific pattern.
    @Override
    public Filter getFilter() {
        return filter;
    }
    // An asynchronous filter was created
    Filter filter = new Filter() {
        /* This method will perform the filtering when getFilter() is called. The argument (of CharSequence type) of the  performFiltering() method is the users' search (or the constraint
         * used to filter). Here, the filteredList is the list of all recipes available for filtering, while the Filter results is an Object which holds the results of the filtering activity.
         */
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Recipe> filteredList = new ArrayList<>();
            FilterResults filterResults = new FilterResults();
            // If the User does not search anything, the results shown should be all recipes.
            if(charSequence.toString().isEmpty())
                filteredList.addAll(recipeListAll);
            // Else, transverse through all the recipes in the recipeListAll to check if the users' search is contained in any menu.
            else {
                for(Recipe r:recipeListAll){
                    if(r.toString().toLowerCase().contains((charSequence.toString().toLowerCase())))
                        filteredList.add(r);
                }
            }
            // Convert List<Recipe> to FilterResults to return.
            filterResults.values = filteredList;
            return filterResults;
        }

        /* This method alters the recipeList (and thus, what is displayed on the screen). It does this by: 1. Clear the recipeList, and 2. Add the filterResults from performFiltering() to recipeList
         * by typecasting it as a Collection which holds Recipe instances. 3. Notify the observers that the recipeList values has been altered and that all View (graphics element) should be refreshed.
         */
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            recipeList.clear();
            recipeList.addAll((Collection<? extends Recipe>) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
