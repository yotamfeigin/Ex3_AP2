package com.example.myapplication.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle incoming messages here
    }

    @Override
    public void onNewToken(String token) {
        Log.d("TOKEN", token);
    }
}
