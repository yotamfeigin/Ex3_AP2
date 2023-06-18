package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.databinding.ActivityLoginPageBinding;
import com.example.myapplication.entities.User;

public class LoginPage extends AppCompatActivity {
    private ActivityLoginPageBinding binding;
    private EditText etUsername, etPassword;
    private String Username, Password;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        user = new User("1","username", "password");
        Button btnLogin = binding.btnLogin;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();

                LoginAPI loginApi = new LoginAPI(Username,Password);
                loginApi.postLogin(user);

            }
        });
    }
}
