package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activites.ChatsPage;
import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.callback.LoginCallback;
import com.example.myapplication.databinding.ActivityLoginPageBinding;
import com.example.myapplication.entities.User;

public class LoginPage extends AppCompatActivity {
    private ActivityLoginPageBinding binding;
    private EditText etUsername, etPassword;
    private String username, password;

    private String fireBaseToken;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        user = new User(etUsername.getText().toString(), null, null);
        Button btnLogin = binding.btnLogin;
        fireBaseToken = getIntent().getStringExtra("firebaseToken");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                LoginAPI loginApi = new LoginAPI(username, password, fireBaseToken);

                loginApi.postLogin(user, new LoginCallback() {
                    @Override
                    public void onLoginSuccess(User user) {
                        // User has been updated after the login
                        Intent intent = new Intent(LoginPage.this, ChatsPage.class);
                        intent.putExtra("USER_OBJECT", user);
                        startActivity(intent);
                    }

                    @Override
                    public void onLoginFailure(Throwable throwable) {
                        // Handle login failure
                        throwable.printStackTrace();
                        Toast.makeText(LoginPage.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TextView tvRegister = binding.tvRegister;
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear the EditText fields
        etUsername.setText("");
        etPassword.setText("");
    }
}
