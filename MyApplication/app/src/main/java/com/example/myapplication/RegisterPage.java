package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.RegisterAPI;
import com.example.myapplication.api.RegsiterServiceAPI;
import com.example.myapplication.databinding.ActivityRegisterPageBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegisterPage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityRegisterPageBinding binding;
    private ImageView myImageView;
    private String username, password, displayName, profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myImageView = binding.myImageView;
        Button selectImageButton = binding.selectImageButton;
        Button btnRegister = binding.btnRegister;
        profilePic = convertImageToBase64();

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.etUsername.getText().toString();
                password = binding.etPassword.getText().toString();
                displayName = binding.etDisplayName.getText().toString();
                String passwordValidation = binding.etReEnterPassword.getText().toString();

                boolean hasError = false;

                if (username.isEmpty()) {
                    setErrorBackground(binding.etUsername);
                    hasError = true;
                } else {
                    clearErrorBackground(binding.etUsername);
                }

                if (password.isEmpty()) {
                    setErrorBackground(binding.etPassword);
                    hasError = true;
                } else if (password.length() < 8 || !isPasswordValid(password)) {
                    setErrorBackground(binding.etPassword);
                    hasError = true;
                } else {
                    clearErrorBackground(binding.etPassword);
                }

                if (!password.equals(passwordValidation)) {
                    setErrorBackground(binding.etReEnterPassword);
                    hasError = true;
                } else {
                    clearErrorBackground(binding.etReEnterPassword);
                }

                if (displayName.isEmpty()) {
                    setErrorBackground(binding.etDisplayName);
                    hasError = true;
                } else {
                    clearErrorBackground(binding.etDisplayName);
                }

                if (hasError) {
                    return;
                }

                RegisterAPI registerAPI = new RegisterAPI(username, password, displayName, profilePic);
                registerAPI.register();
                finish();
            }
        });
    }

    private void setErrorBackground(View view) {
        view.setBackgroundResource(R.drawable.edit_text_error_background);
    }

    private void clearErrorBackground(View view) {
        view.setBackgroundResource(R.drawable.edit_text_background);
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                byte[] imageData = getBytesFromInputStream(inputStream);
                String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
                // Use the base64Image as needed

                // Update the ImageView with the selected image
                myImageView.setImageURI(imageUri);
                profilePic = base64Image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPasswordValid(String password) {
        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check if password contains at least one uppercase letter
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        if (!hasUpperCase) {
            return false;
        }

        // Check if password contains at least one lowercase letter
        boolean hasLowerCase = password.matches(".*[a-z].*");
        if (!hasLowerCase) {
            return false;
        }

        // Check if password contains at least one digit
        boolean hasDigit = password.matches(".*\\d.*");
        if (!hasDigit) {
            return false;
        }

        // Check if password contains at least one special character
        boolean hasSpecialCharacter = password.matches(".*[-!@#$%^&*()_+|\\[\\]{};:/<>,.?].*");
        if (!hasSpecialCharacter) {
            return false;
        }

        // If password passes all checks, return true
        return true;
    }

    private String convertImageToBase64() {
        try {
            InputStream inputStream = getResources().openRawResource(R.drawable.logo);
            byte[] imageData = getBytesFromInputStream(inputStream);
            return Base64.encodeToString(imageData, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
