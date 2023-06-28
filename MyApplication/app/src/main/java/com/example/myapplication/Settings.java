package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.MyApplication;

public class Settings extends AppCompatActivity {



    private EditText etServerUrl;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etServerUrl = findViewById(R.id.etServerUrl);
        sharedPreferences = getSharedPreferences(MyApplication.context.getString(R.string.SharedPrefs), MODE_PRIVATE);

        String baseUrl = getBaseUrl();
        etServerUrl.setText(baseUrl);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUrl = etServerUrl.getText().toString();
                saveBaseUrl(newUrl);

                // Finish the activity and return to the previous screen
                finish();
            }
        });
    }

    private void saveBaseUrl(String baseUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BaseUrl", baseUrl);
        editor.apply();
    }


    private String getBaseUrl() {
        return sharedPreferences.getString("BaseUrl", getResources().getString(R.string.BaseUrl));
    }
}