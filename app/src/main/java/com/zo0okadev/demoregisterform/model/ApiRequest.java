package com.zo0okadev.demoregisterform.model;

public class ApiRequest {

    private User user;

    public ApiRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
