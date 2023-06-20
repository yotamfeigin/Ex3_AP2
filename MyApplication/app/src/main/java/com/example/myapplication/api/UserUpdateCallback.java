package com.example.myapplication.api;

import com.example.myapplication.entities.User;

public interface UserUpdateCallback {
    void onUserUpdated(User user);

    void onUserUpdateFailure(Throwable throwable);
}
