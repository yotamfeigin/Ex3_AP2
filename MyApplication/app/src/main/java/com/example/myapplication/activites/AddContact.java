package com.example.myapplication.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewModels.ChatsViewModel;

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
            EditText userName = findViewById(R.id.addContact_userName);
            Intent intent = new Intent();
            intent.putExtra("username", userName.getText().toString()); // Put your data using appropriate key-value pairs
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        Button buttonExit = findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener( v -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TextView userName = findViewById(R.id.addContact_userName);
        userName.setText("");
    }
}