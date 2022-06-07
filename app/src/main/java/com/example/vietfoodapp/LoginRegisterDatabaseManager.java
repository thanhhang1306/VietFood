package com.example.vietfoodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginRegisterDatabaseManager extends SQLiteOpenHelper {

    // Initialize variables for database name and column name.
    private static final String TABLE_NAME = "USER_TABLE";
    private static final String COLUMN_USER_ID = "USER_ID";
    private static final String COLUMN_USER_FIRST_NAME = "USER_FIRST_NAME";
    private static final String COLUMN_USER_LAST_NAME = "USER_LAST_NAME";
    private static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    private static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    private static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";

    /*
     * Constructor from super class SQLiteOpenHelper, which includes parameters (Context, String-Database file name,
     * SQLiteDatabase.CursorFactory, int version). For SQLiteDatabase.CursorFactory, null is for the default cursor.
     */

    public LoginRegisterDatabaseManager(Context context){
        super(context, "users.db",null,1);
    }

    /*
     * Method to encrypt String with MD5 hashing algorithm, adapted from tutorialspoint "How to
     * encrypt password and store in Android sqlite."
     */
    public static final String encryptMD5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create the MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create the Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Method to create SQL database if it has not been created already with SQL statement createTableStatement 
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_FIRST_NAME + " TEXT, " + COLUMN_USER_LAST_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_USERNAME + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";
        database.execSQL(createTableStatement);
    }

    /* Method for when the database's version changes. Because the database is set to remain the same, the version
     * should experience no changes. Therefore, if a change is to occur, the table will be dropped if it does exist.
     */ 
    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }


    // This method writes new user's credential into the database
    public void writeUser(User user){
	    // getWriteableDatabase open a readable and writable database.
        SQLiteDatabase database = this.getWritableDatabase();
	    // Content Values can store sets of values.
        ContentValues contentValues = new ContentValues();
	    // The following code encrypt the users' password with MD5 hash
        String password = user.getPassword();
	    password = encryptMD5(password);
        /*
         * Add the values into each appropriate column, through contentValues.put, where the first parameter is
	     * is the column to insert the data and the second parameter is the data.
         */
        contentValues.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        contentValues.put(COLUMN_USER_LAST_NAME, user.getLastName());
        contentValues.put(COLUMN_USER_EMAIL,user.getEmail());
        contentValues.put(COLUMN_USER_USERNAME, user.getUsername());
        contentValues.put(COLUMN_USER_PASSWORD, password);
        database.insert(TABLE_NAME,null,contentValues);
        database.close();
    }

    // This method obtain a List of all the Users in the Database. Every row of the database contains attributes for one User object.
    public List<User> readUser(){
        List<User> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        // Result set from a SQL statement is stored in Cursor.
        Cursor cursor = database.rawQuery(queryString,null);
        // moveToFirst moves to the first item on list. It returns true if an item was selected.
        if(cursor.moveToFirst()){
            // Loop through the cursor (result set) and create Users for every row.
            do{
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setUsername(cursor.getString(4));
                user.setPassword(cursor.getString(5));
                // Add user to the returnList
                returnList.add(user);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return returnList;
    }

    // This method update the data of a specific user (passed through the argument)
    public void updateData(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        contentValues.put(COLUMN_USER_LAST_NAME, user.getLastName());
        contentValues.put(COLUMN_USER_EMAIL,user.getEmail());
        contentValues.put(COLUMN_USER_USERNAME, user.getUsername());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        database.update(TABLE_NAME, contentValues, COLUMN_USER_ID + " = ?", new String[]{String.valueOf((user.getId()))});
        database.close();
    }

    // This method deletes the data of a specific user
    public void deleteUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        database.close();
    }

    // This method check if the username already exist in the database
    public boolean checkUser(String username){
        SQLiteDatabase database = this.getWritableDatabase();
        // The SQL query is used to fetch all records from the database where the username is equal to the value passed in the argument.
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_USERNAME+" = ?", new String[]{username});
        // This counts the amount of rows with the same username as the value passed in the argument.
        int cursorCount = cursor.getCount();
        cursor.close();
        database.close();
        // If there is a match, return true; If not, return false
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    // This method check if the username and password input (pass through the arguments) matches any user's credential in the database.
    public boolean checkMatch(String username, String password){
        // Take the encrypted version of the String
        password = encryptMD5(password);
        SQLiteDatabase database = this.getWritableDatabase();
        // The SQL query is used to fetch all records that has both username and password equal to the value passed in the argument.
	    Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME+ " WHERE " + COLUMN_USER_USERNAME+ " = ? AND "
                + COLUMN_USER_PASSWORD+ " = ?", new String[]{username, password});
        // This counts the amount of rows with the same username as the value passed in the argument.
        int cursorCount = cursor.getCount();
        cursor.close();
        database.close();
        // If there is a match (meaning username and password matches), return true; If not, return false.
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
