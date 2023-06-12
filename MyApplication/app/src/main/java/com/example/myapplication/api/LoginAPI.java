package com.example.myapplication.api;

import android.util.Log;

import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private String username;
    private String password;

    public LoginAPI(String username, String password) {
        this.username = username;
        this.password = password;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void postLogin() {
        try{

        Call<String> call = webServiceAPI.postLogin(username, password);
            String url = call.request().url().toString(); // Get the URL from the request
            Log.d("LoginAPI", "Request URL: " + url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String res = response.body();
                    // Handle successful response
                    Log.d("LoginAPI", "Response: " + res);
                } else {
                    // Handle unsuccessful response
                    Log.e("LoginAPI", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle network failure
                Log.e("LoginAPI", "Network error: " + t.getMessage());
            }
        });
    }
        catch (Exception e) {
            e.printStackTrace();
        }
}}
