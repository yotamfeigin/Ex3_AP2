package com.example.myapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewModels.ChatsViewModel;
import com.example.namespace.R;

public class AddContact extends AppCompatActivity {

    private ChatsViewModel chatsViewModel;
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Intent myIntent = getIntent();
        currentUser = (User) myIntent.getSerializableExtra("USER_OBJECT");
        chatsViewModel = new ChatsViewModel(currentUser);
        Button buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener( v -> {
            TextView userName = findViewById(R.id.addContact_userName);
            chatsViewModel.insert(userName.getText().toString());
            finish();
        });
        Button buttonExit = findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener( v -> {
            finish();
        });
    }
}