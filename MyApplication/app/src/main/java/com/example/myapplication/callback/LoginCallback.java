package com.example.myapplication.callback;

import com.example.myapplication.entities.User;

public interface LoginCallback {
    void onLoginSuccess(User user);
    void onLoginFailure(Throwable throwable);
}
