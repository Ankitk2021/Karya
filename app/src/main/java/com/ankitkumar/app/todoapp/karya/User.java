package com.ankitkumar.app.todoapp.karya;

public class User {
    String username;
    String password;
    String phone_number;
    String email;
    boolean active = false;

    public User(String username,String password,String phone_number,String email) {
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
