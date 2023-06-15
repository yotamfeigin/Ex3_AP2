package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.databinding.ActivityLoginPageBinding;
import com.example.myapplication.databinding.ActivityRegisterPageBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
