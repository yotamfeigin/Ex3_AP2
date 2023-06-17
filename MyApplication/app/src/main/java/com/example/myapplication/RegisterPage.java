package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Entities.User;
import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.databinding.ActivityRegisterPageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Retrofit;

public class RegisterPage extends AppCompatActivity {

    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            TextView userName = findViewById(R.id.etUsername);
            TextView password = findViewById(R.id.etPassword);
            TextView confirmPassword = findViewById(R.id.etReEnterPassword);
            TextView nickName = findViewById(R.id.etDisplayName);

            userRepository = new UserRepository(userName.getText().toString());
            User user = new User(userName.getText().toString(), password.getText().toString(),
                    nickName.getText().toString());
            user.setProfilePicture(Uri.parse("android.resource://com.example.whatsapp_clone_test/drawable/image_name").getPath());


            if (userRepository.register(user, confirmPassword.getText().toString())) {
                Intent i = new Intent(this, LoginPage.class);
                startActivity(i);
            } else {
               // Intent i = new Intent(this, registerError.class);
                //startActivity(i);
            }
        });
    }

}
