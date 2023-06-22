package com.example.myapplication.activites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adaptes.ChatsAdapter;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewModels.ChatsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsPage extends AppCompatActivity {
    private static final int REQUEST_CODE = 1; // Choose any unique value as the request code

    private ChatsViewModel model;
    private List<Chat> chats;
    private RecyclerView recyclerView;
    private ChatsAdapter adapter;
    private User user;
    private ChatsAdapter.RecycleViewClickListener listener;
    private Intent myIntent;
    private Bundle savedInstanceState;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_chats_page);
        myIntent = getIntent();
        user = (User) myIntent.getSerializableExtra("USER_OBJECT");
        chats = new ArrayList<>();
        recyclerView = findViewById(R.id.rvChats);
        setAdapter();

        model = new ChatsViewModel(user);
        model.getChats().observe(this, contactEntities -> {
            chats = contactEntities;
            adapter.setContacts(contactEntities);
        });

        setUserInfo();

        Button btnAddContact = findViewById(R.id.btnAddChat);
       btnAddContact.setOnClickListener( v -> {
            Intent i = new Intent(this, AddContact.class);
            i.putExtra("USER_OBJECT",user);
           startActivityForResult(i, REQUEST_CODE);
        });

//        Button settings = findViewById(R.id.settings);
//        settings.setOnClickListener( v -> {
//            Intent i = new Intent(this, Settings.class);
//            i.putExtra("userName",userActive);
//            startActivity(i);
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        chats.clear();
        chats.addAll(model.getChats().getValue());
        adapter.notifyDataSetChanged();
    }

    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String value = data.getStringExtra("username"); // Retrieve the data using the key
                Log.d("CONTACT", value);
                if(value != null) {
                    model.insert(value);
                }
            }
        }
        if(requestCode == 2) {
            model.getChats();
            adapter.notifyDataSetChanged();
        }
    }


    private void setAdapter() {
        if (recyclerView != null) {
            setOnClickListener();
            adapter = new ChatsAdapter(chats, this, listener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("ChatsPage", "RecyclerView is null");
        }
    }



    private void setOnClickListener() {
        listener = contact -> {
            Intent i = new Intent(this, Messages.class);
            i.putExtra("USER_OBJECT1", user);
            i.putExtra("USER_OBJECT2", contact.getUser());
            i.putExtra("chatId", contact.getId());
            startActivityForResult(i, 2);

        };
    }

    private void setUserInfo () {
        chats = model.getChats().getValue();
        TextView tvMyName = findViewById(R.id.tvMyName);
        ImageView ivMyPic = findViewById(R.id.ivMyPic);
        tvMyName.setText(user.getDisplayName());
        // Set the image from Base64 string
        String base64Image =user.getProfilePic();
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ivMyPic.setImageBitmap(decodedBitmap);
    }
}