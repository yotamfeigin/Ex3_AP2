package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Entities.User;

public class RegisterPage extends AppCompatActivity {

    private UserRepository userRepository;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Button btnRegister = findViewById(R.id.btnRegister);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etReEnterPassword);

        btnRegister.setOnClickListener(v -> {
            EditText userName = findViewById(R.id.etUsername);
            EditText nickName = findViewById(R.id.etDisplayName);

            userRepository = new UserRepository(userName.getText().toString());
            User user = new User(userName.getText().toString(), etPassword.getText().toString(),
                    nickName.getText().toString());

            if (isPasswordValid()) {
                if (userRepository.register(user, etConfirmPassword.getText().toString())) {
                    Intent i = new Intent(this, LoginPage.class);
                    startActivity(i);
                } else {
                    // Registration failed
                    // Handle registration failure
                }
            } else {
                // Passwords don't match or not in the required format
                               setEditTextBorderColor(etPassword, Color.RED);
                setEditTextBorderColor(etConfirmPassword, Color.RED);

                if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    Toast.makeText(RegisterPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterPage.this, "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isPasswordValid() {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        return password.equals(confirmPassword) && validatePassword(password);
    }

    private void setEditTextBorderColor(EditText editText, int color) {
        ShapeDrawable borderDrawable = new ShapeDrawable(new RectShape());
        borderDrawable.getPaint().setColor(color);
        borderDrawable.getPaint().setStyle(Paint.Style.STROKE);
        borderDrawable.getPaint().setStrokeWidth(10);

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{editText.getBackground(), borderDrawable});
        editText.setBackground(layerDrawable);
    }

    private boolean validatePassword(String password) {
        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check if password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check if password contains at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check if password contains at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Check if password contains at least one special character
        if (!password.matches(".*[-!@#$%^&*()_+|\\[\\]{};:/<>,.?].*")) {
            return false;
        }

        // Password meets all the criteria
        return true;
    }
}
