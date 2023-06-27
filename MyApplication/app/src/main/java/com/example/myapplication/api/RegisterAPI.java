package com.example.myapplication.api;

import static com.example.myapplication.api.MyApplication.context;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPI {
    private Retrofit retrofit;

    private RegsiterServiceAPI regsiterServiceAPI;

    private String username;

    private String password;

    private String displayName;

    private String profilePic;


    public RegisterAPI(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;


        SharedPreferences SharedPreferences = context.getSharedPreferences(String.valueOf(R.string.SharedPrefs), Context.MODE_PRIVATE);
        String BaseUrl = SharedPreferences.getString("BaseUrl","");
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        regsiterServiceAPI = retrofit.create(RegsiterServiceAPI.class);

    }

    public void register() {
        Call<Void> call = regsiterServiceAPI.register(username, password, displayName, profilePic);
        String url = call.request().url().toString(); // Get the URL from the request
        Log.d("LoginAPI", "Request URL: " + url);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                } else {
                    // Handle unsuccessful response
                    Log.e("RegisterAPI", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failure
                Log.e("RegisterAPI", "Network error: " + t.getMessage());
            }
        });

    }
}
