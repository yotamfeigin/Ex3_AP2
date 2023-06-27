package com.example.myapplication.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.activites.Messages;

public class NewMessageBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the received broadcast message here
        // Update the messages list in the Messages activity
        Intent updateIntent = new Intent(context, Messages.class);
        updateIntent.setAction(Messages.ACTION_UPDATE_MESSAGES);
        context.startActivity(updateIntent);
    }
}
