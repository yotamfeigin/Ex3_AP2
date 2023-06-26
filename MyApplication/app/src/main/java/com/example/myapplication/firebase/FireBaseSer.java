package com.example.myapplication.firebase;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.api.WebServiceAPI;
import com.example.myapplication.entities.User;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseSer extends FirebaseMessagingService {
    private WebServiceAPI api;
    private User user;
    private String token;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent("1001").putExtra("Messge", "message");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



    @Override
    public void onMessageSent(@NonNull String msgId) {
        super.onMessageSent(msgId);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "onNewToken: " + token);

    }
}
