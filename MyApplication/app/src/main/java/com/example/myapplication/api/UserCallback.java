package com.example.myapplication.api;

import com.example.myapplication.entities.User;

public interface UserCallback {
    void onUserSuccess(User user);
    void onUserFailure(Throwable throwable);
}
