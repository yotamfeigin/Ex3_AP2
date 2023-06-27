package com.example.myapplication.activites;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adaptes.MessagesAdapter;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewModels.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity {

    public static final String ACTION_UPDATE_MESSAGES = "com.example.myapplication.ACTION_UPDATE_MESSAGES";
    private MessageViewModel model;
    private List<Message> messages;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private User user;
    private User otherUser;
    private String chatId;

    private Intent myIntent;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massages);


        myIntent = getIntent();
        user = (User) myIntent.getSerializableExtra("USER_OBJECT1");
        otherUser = (User) myIntent.getSerializableExtra("USER_OBJECT2");
        chatId = myIntent.getStringExtra("chatId");
        messages = new ArrayList<>();

        recyclerView = findViewById(R.id.lstMsg);
        setAdapter();

        model = new MessageViewModel(user, chatId);
        model.getContactMessages().observe(this, contactEntities -> {
            messages = contactEntities;
            adapter.setMessages(contactEntities);
            recyclerView.scrollToPosition(messages.size() - 1);
        });

        ImageButton sendMessageBtn = findViewById(R.id.sendMessageBtn);
        sendMessageBtn.setOnClickListener(v -> {
            sendMessage();
        });


        setUserInfo();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                messages.clear();
                messages.addAll(model.get().getValue());
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size() - 1);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("1001"));



    }

    @Override
    protected void onResume() {
        super.onResume();
        messages.clear();
        messages.addAll(model.get().getValue());
        adapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        if (recyclerView != null) {
            adapter = new MessagesAdapter(this, messages, user);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("ChatsPage", "RecyclerView is null");
        }
    }

    private void setUserInfo () {
        TextView user_name_chat = findViewById(R.id.user_name_chat);
        user_name_chat.setText(otherUser.getDisplayName());


    }

    private void sendMessage(){
        TextView content = findViewById(R.id.editTextChat);
        model.add(content.getText().toString());
        content.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("back", "back"); // Put your data using appropriate key-value pairs
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}