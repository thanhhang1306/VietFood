package com.example.vietfoodapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// This class provides the functionality for database which stores the recipe's information
public class RecipeDatabaseManager extends SQLiteOpenHelper {

    // Initialize variables for database name and column name.
    private static final String TABLE_NAME = "RECIPE_LIST";
    private static final String COLUMN_RECIPE_NAME = "RECIPE_NAME";
    private static final String COLUMN_RECIPE_TYPE = "RECIPE_TYPE";
    private static final String COLUMN_RECIPE_DESCRIPTION = "RECIPE_DESCRIPTION";
    private static final String COLUMN_RECIPE_URL = "RECIPE_URL";
    private static final String COLUMN_RECIPE_SERVING = "RECIPE_SERVING";
    private static final String COLUMN_RECIPE_ID = "ID";
    private static final String COLUMN_RECIPE_INGREDIENT = "RECIPE_INGREDIENT";
    private static final String COLUMN_RECIPE_INSTRUCTION = "RECIPE_INSTRUCTION";

    // Initialize the variables required to export data from preexisting database/
    private AssetManager assetManager;
    private String databaseDirection;
    private final static String DATABASE_NAME = "recipess.db";
    private final static int DATABASE_VERSION = 1;


    /*
     * Constructor from super class SQLiteOpenHelper, which includes parameters (Context, String-Database file name,
     * SQLiteDatabase.CursorFactory, int version). For SQLiteDatabase.CursorFactory, null is for the default cursor.
     */
    public RecipeDatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        assetManager = context.getAssets();
        databaseDirection = context.getApplicationInfo().dataDir + "/databases/";

        File file = new File(databaseDirection);
        if(!file.exists())
            file.mkdir();
    }

    /*
     * Below are the methods to pull in data from an external database into the SQL database, based on
     * https://stackoverflow.com/questions/28589900/how-use-an-external-sqlite-database-in-android-application
     * response by codegames on October 10, 2018.
     */


    /*
     * Method to create SQL database if it has not been created already. Here, nothing is added as into
     * the method we are pulling from an existing database, which is located in the "databases" folder
     * within the "assets" folder.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    // Method to update the database when the version changes.
    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        copyDatabase();
    }

    /*
     * Override the getWritableDatabase() and getReadableDatabase() from the parent class as data
     * is being pulled from external database and not from a newly created one.
     */

    @Override
    public SQLiteDatabase getWritableDatabase(){
        if(!isDatabaseExist())
            copyDatabase();
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase(){
        if(!isDatabaseExist())
            copyDatabase();
        return super.getReadableDatabase();
    }

    // This method checks to ensure the external database is in the assets folder.
    private Boolean isDatabaseExist(){
        return new File(databaseDirection + DATABASE_NAME).exists();
    }

    /*
     * This method is adapted from codegames response in the Stack Overflow question
     * "How use an external sqlite database in android application."
     * At: https://stackoverflow.com/questions/28589900/how-use-an-external-sqlite-database-in-android-application
     */

    private void copyDatabase(){
        try{
            InputStream inputStream = assetManager.open("databases/" + DATABASE_NAME);
            FileOutputStream outputStream = new FileOutputStream(databaseDirection + DATABASE_NAME);
            byte[] buffer = new byte[8 * 1024];
            int read;
            while((read = inputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, read);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    // The methods below relate to data manipulation within the database

    // This method writes the details of the recipes that users' added into the database.
    public void writeData(Recipe recipe){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_NAME, recipe.getName());
        contentValues.put(COLUMN_RECIPE_TYPE, recipe.getCuisineType());
        contentValues.put(COLUMN_RECIPE_SERVING,recipe.getServings());
        contentValues.put(COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());
        contentValues.put(COLUMN_RECIPE_URL, recipe.getImageURL());
        if(recipe.getRecipeIngredient() != null)
            contentValues.put(COLUMN_RECIPE_INGREDIENT, recipe.getIngredient());
        else contentValues.put(COLUMN_RECIPE_INGREDIENT, "");
        if(recipe.getRecipeInstruction() != null)
            contentValues.put(COLUMN_RECIPE_INSTRUCTION, recipe.getInstruction());
        else contentValues.put(COLUMN_RECIPE_INSTRUCTION, "");
        database.insert(TABLE_NAME,null, contentValues);
        database.close();
    }

    // This method obtain a List of all the Recipes in the Database
    public List<Recipe> readData(){
        List<Recipe> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        // Result set from a SQL statement is stored in Cursor.
        Cursor cursor = database.rawQuery(queryString,null);
        // moveToFirst moves to the first item on list. It returns true if an item was selected.
        if(cursor.moveToFirst()){ // loop through the cursor (result set) and create new recipe
            do{
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(0));
                recipe.setName(cursor.getString(1));
                recipe.setCuisineType(cursor.getString(2));
                recipe.setServings(cursor.getString(3));
                recipe.setDescription(cursor.getString(4));
                recipe.setImageURL(cursor.getString(5));
                recipe.setIngredient(cursor.getString(6));
                recipe.setInstruction(cursor.getString(7));
                // Add recipe to the returnList
                returnList.add(recipe);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return returnList;
    }

    // This method update the data of a specific recipe (passed through the argument)
    public void updateData(Recipe recipe){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Add the values from the second argument into each appropriate column.
        contentValues.put(COLUMN_RECIPE_NAME, recipe.getName());
        contentValues.put(COLUMN_RECIPE_TYPE, recipe.getCuisineType());
        contentValues.put(COLUMN_RECIPE_SERVING,recipe.getServings());
        contentValues.put(COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());
        contentValues.put(COLUMN_RECIPE_URL, recipe.getImageURL());
        /*
         * Because the ingredient and instruction column will be empty when users first add the general information,
         * these checks will prevent the application from crashing due to NullPointerException.
         */
        if(recipe.getIngredient() != null)
            contentValues.put(COLUMN_RECIPE_INGREDIENT, recipe.getIngredient());
        else contentValues.put(COLUMN_RECIPE_INGREDIENT, "");
        if(recipe.getInstruction() != null)
            contentValues.put(COLUMN_RECIPE_INSTRUCTION, recipe.getInstruction());
        else contentValues.put(COLUMN_RECIPE_INSTRUCTION, "");
        database.update(TABLE_NAME, contentValues, COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf((recipe.getId()))});
    }

    // This method deletes the data of a specific recipe
    public void deleteData(long id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_RECIPE_ID + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    // Method to obtain the ID of the recipe user chooses to edit (increase ease when moving between the Edit Activities class.
    public long getRecipeID(){
        SQLiteDatabase database = this.getReadableDatabase();
        // Return type must be long for the queryNumEntries() method.
        long rowNumber = DatabaseUtils.queryNumEntries(database, TABLE_NAME);
        database.close();
        return rowNumber;
    }

    // Method to obtain the last ID of the recently added recipe (increase ease when moving between the Add Activities class.
    public int getIDLastRow() {
        int id = -1;
        SQLiteDatabase database = this.getReadableDatabase();
        // SQL statement to obtain all recipes in the database.
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        // This method moves the cursor selection to the last row and set int to be equal to the value of the first column (ID).
        if(cursor.moveToLast())
            id = cursor.getInt(0);
        return id;
    }

    // This method obtains the data of a Recipe from its ID value.
    @SuppressLint("Range")
    public Recipe getRecipeInfoFromID(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        // Cursor records entries where ID is equal to the id passed through the argument.
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf(id)});
        Recipe recipe = new Recipe();
        // If there is a value in the cursor, create a recipe object from the data associated with the ID and return it.
        if(cursor!=null){
            if(cursor.moveToFirst()){
                recipe.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_RECIPE_ID)));
                recipe.setName(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME)));
                recipe.setCuisineType(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_TYPE)));
                recipe.setServings(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_SERVING)));
                recipe.setImageURL(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_URL)));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_DESCRIPTION)));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_DESCRIPTION)));
                recipe.setIngredient(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_INGREDIENT)));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_INSTRUCTION)));
            }
            cursor.close();
        }
        return recipe;
    }
}
