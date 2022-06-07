package com.example.vietfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    private EditText firstNameEditText, lastNameEditText, usernameEditText;
    private EditText passwordEditText, emailEditText, repassEditText;
    private Button registerButton;
    private TextView loginTextView, errorTextView;
    private LoginRegisterDatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout of Register Activity to be "activity_register" 
        setContentView(R.layout.activity_register);
        
        // Initialize LoginRegisterDatabaseManager to access and edit database 
        databaseManager = new LoginRegisterDatabaseManager(RegisterActivity.this);
        
        /*
         * Initialize graphics aspect from the layout (specifically, the EditText, TextView, and Button)
         * based on the View class properties. The View class provides the basic building block for
         * user interface/graphics component as mentioned above. 
         */
        firstNameEditText= findViewById(R.id.firstNameEditText);
        lastNameEditText= findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.userNameEditText2);
        passwordEditText = findViewById(R.id.passwordEditText2);
        emailEditText = findViewById(R.id.emailEditText);
        repassEditText = findViewById(R.id.retypeEditText);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);
        errorTextView = findViewById(R.id.errorMessageTextView);

        // Set Error TextView to show nothing at first
        errorTextView.setText("");

        /*
         * This method is ran when the "Login" TextView is clicked. It's main job is to 
         * redirect the user from the RegisterActivity page to the LoginActivity page 
         * by using Intent (used to perform actions, such as starting activity and sending
         * messages between the activities. 
         */
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	/*
            	 * Here, an explicit intent is utilized, as the targeted component (the LoginActivity)
            	 * is specified as the second argument of the Intent intent. An explicit intent 
            	 * parameter is Intent(Context, Class). Context is an abstract class that is automatically
            	 * implemented by the Android system, and it allows access to application-specific classes
            	 * and resources. All activities inherits from the Context class. 
            	 */
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*
         * This method is ran when the "Register" Button is clicked. It checks whether users have:
         * 1. Filled in all information, 2. Provided a valid email, 3. Filled the same input for the 
         * passwords field, 4. Picked an unique username. If all conditions is true, then the users 
         * input will be added to the users.db SQLDatabase. 
         */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	// Convert the user's input from EditText to String.
                String firstName, lastName, userName, password, retypepassword, email;
                firstName = firstNameEditText.getText().toString();
                lastName = lastNameEditText.getText().toString();
                email = emailEditText.getText().toString();
                userName = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                retypepassword = repassEditText.getText().toString();

                // Create an User with the input
                User user = new User(firstName,lastName,email,userName,password, retypepassword);
                
                // This statement checks if all fields are filled. If any fields are empty, return a message prompting users to enter all fields.  
                if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() ||
                     password.isEmpty() || retypepassword.isEmpty() || email.isEmpty())
                        errorTextView.setText("Please enter all fields");
                // If all fields are entered: 
                else{
                	// This statement check if email includes "@" and common endings (separated by OR ||). If they are, check passwords. 
                    if(email.contains("@") && (email.contains(".com") || (email.contains(".net")) || (email.contains(".org")) || (email.contains(".mil")))) {
                    	// This statement check if the passwords are matching. If they are, check if username is unique. 
                    	if(password.equals(retypepassword)){
                    		/*
                    		 * This statement check if username is unique by running the checkUser method from the LoginRegisterDatabaseManager
                    		 * class. The argument is the user's input for username. The method returns true if the username already exist, 
                    		 * and false if the username is unique. 
                    		 */
                            Boolean checkUser = databaseManager.checkUser(userName);
                            /*
                             * If the username does not exist, move user to the Login Activity page. Pass a boolean value named registersuccess = true to
                             * Login Activity as well so that an appropriate message could be displayed.
                             */
                            if(!checkUser){
                                databaseManager.writeUser(user);
                                errorTextView.setText("");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("loginSuccess", true);
                                startActivity(intent);
                                finish();
                            }
                            // Else statement for third IF() of the nested statement; returns error message as username already exists
                            else errorTextView.setText("Registration failed: Username taken!");
                        }
                        // Else statement for second IF() of the nested statement; returns error message due not matching password
                        else errorTextView.setText("Registration failed: Passwords not match!");
                    }
                    // Else statement for first IF() of the nested statement; returns error message due to invalid email 
                    else errorTextView.setText("Registration failed: Invalid email!");
                    
                    // Clear all the EditText for users to reenter their credentials 
                    firstNameEditText.getText().clear();
                    lastNameEditText.getText().clear();
                    emailEditText.getText().clear();
                    usernameEditText.getText().clear();
                    passwordEditText.getText().clear();
                    repassEditText.getText().clear();
                }
            }
        });

    }
}

