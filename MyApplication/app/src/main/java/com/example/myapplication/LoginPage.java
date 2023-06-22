package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activites.ChatsPage;
import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.callback.LoginCallback;
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
        user = new User(etUsername.getText().toString(),null
                , null);
        Button btnLogin = binding.btnLogin;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();

                LoginAPI loginApi = new LoginAPI(Username,Password);
                loginApi.postLogin(user, new LoginCallback() {
                    @Override
                    public void onLoginSuccess(User user) {
                        // User has been updated after the login
                        Log.d("User", user.getUsername());
                        Log.d("User", user.getUsername());
                        Intent intent = new Intent(LoginPage.this, ChatsPage.class);
                        User test = new User("tal1", "logo.jpg", "Tal");
                        intent.putExtra("USER_OBJECT", user);
                        Log.d("User", user.getUsername());

                        startActivity(intent);
                    }

                    @Override
                    public void onLoginFailure(Throwable throwable) {
                        // Handle login failure
                        throwable.printStackTrace();
                    }
                });

            }
        });
    }
}
