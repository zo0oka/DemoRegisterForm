package com.zo0okadev.demoregisterform.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User {

    private String name;
    private String email;
    private String password;
    private String phone;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // a method to validate User data before sending it to the server
    public boolean isValid() {
        return !TextUtils.isEmpty(name) && name.length() > 3
                && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password) && password.length() > 8
                && !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }
}
