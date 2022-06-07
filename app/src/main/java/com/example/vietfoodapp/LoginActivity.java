package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView signUpTextView, welcomeTextView, errorTextView;
    private LoginRegisterDatabaseManager databaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize LoginRegisterDatabaseManager to access and edit database
        databaseManager = new LoginRegisterDatabaseManager(LoginActivity.this);

        // Initialize graphics aspect from the layout
        usernameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.logInButton);
        signUpTextView = findViewById(R.id.clickTextView);
        welcomeTextView = findViewById(R.id.welcomeTextView);
        errorTextView = findViewById(R.id.errorMessageTextView);
        databaseManager = new LoginRegisterDatabaseManager(LoginActivity.this);

        /*
         * Check if the boolean value (registersuccess) was obtained from RegisterActivity. The second
         * argument of getBooleanExtra() is the default value if no message came from RegisterActivity.
         */
        boolean success = getIntent().getBooleanExtra("loginSuccess",false);
        if(success)
            welcomeTextView.setText("Sign up successful! Welcome");
        else welcomeTextView.setText("Welcome to VietFood!");

        // Set Error TextView to show nothing at first
        errorTextView.setText("");

        /*
         * This method is ran when the "Register" TextView is clicked. It's main job is to
         * redirect the user from the LoginActivity page to the RegisterActivity page.
         */
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*
         * This method is ran when the "Login" Button is clicked. It checks if the credentials the user
         * typed are correct or incorrect, and redirect them to the All Recipes page if it is correct.
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert the user's input from EditText to String.
                String username, password;
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                // This statement checks if all fields are filled. If any fields are empty, return a message prompting users to enter all fields.
                if(username.isEmpty() || password.isEmpty()) {
                    errorTextView.setText("Please enter all fields");
                    usernameEditText.getText().clear();
                    passwordEditText.getText().clear();
                }
                else{
                    /*
                     * This statement check if username and password are correct by running the checkMatch method from the LoginRegisterDatabaseManager
                     * class. The method returns true if there is an user with the credentials in the database, and false if not.
                     */
                    Boolean checkMatchingData = databaseManager.checkMatch(username,password);
                    if(checkMatchingData){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else  errorTextView.setText( "Invalid Credentials!");
                }
            }
        });
    }
}