package com.example.vietfoodapp;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String retypePassword;

    // Overloaded constructors for User
    public User(){ }

    public User(String firstName, String lastName, String email, String username, String password, String retypePassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.retypePassword = retypePassword;
    }

    // All the accessors and mutators.
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRetypePassword() { return retypePassword;}
    public void setRetypePassword(String retypePassword) { this.retypePassword = retypePassword; }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
