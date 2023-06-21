package com.example.myapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

    private MessageViewModel model;
    private List<Message> messages;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private User user;
    private User otherUser;
    private String chatId;

    private Intent myIntent;
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
        });

        setUserInfo();

    }

    private void setAdapter() {
        if (recyclerView != null) {
            adapter = new MessagesAdapter(this, messages);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("ChatsPage", "RecyclerView is null");
        }
    }

    private void setUserInfo () {
        messages = model.getContactMessages().getValue();
        TextView user_name_chat = findViewById(R.id.user_name_chat);
        user_name_chat.setText(otherUser.getDisplayName());

    }
}