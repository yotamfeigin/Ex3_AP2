package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Entities.User;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginPage extends AppCompatActivity {
    private UserRepository userRepository;
    private String token;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_page);
            Button btnLogin = findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener( v -> {
                TextView userName = findViewById(R.id.etUsername);
                TextView password = findViewById(R.id.etPassword);
                userRepository = new UserRepository(userName.getText().toString());
                User u = userRepository.login(userName.getText().toString(), password.getText().toString());

                if (u != null) {

                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginPage.this, instanceIdResult -> {
                        String NewToken = instanceIdResult.getToken();
                        userRepository.createToken(NewToken);

                    });
                    //   Intent i = new Intent(this, ContactsList.class);
                   // i.putExtra("userName",userName.getText().toString());
                   // startActivity(i);
                } else {
                    Toast.makeText(LoginPage.this, "Error logging in", Toast.LENGTH_SHORT).show();

                    //  Intent i = new Intent(this, loginError.class);
                   // startActivity(i);
                }

            });
        }


}
