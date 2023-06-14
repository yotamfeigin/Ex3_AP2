package com.example.myapplication.api;

import android.util.Log;

import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        regsiterServiceAPI = retrofit.create(RegsiterServiceAPI.class);
    }

    public void register() {
        Call<JsonObject> call = regsiterServiceAPI.register(username, password, displayName, profilePic);
        String url = call.request().url().toString(); // Get the URL from the request
        Log.d("LoginAPI", "Request URL: " + url);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject responseObject = response.body(); // Get the response body as a JSON object

                    // Convert the JSON object to a string using Gson
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(responseObject);
                    Log.d("Response", jsonString);

                    // Handle the JSON object response
                    // ...
                } else {
                    // Handle unsuccessful response
                    Log.e("RegisterAPI", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle network failure
                Log.e("RegisterAPI", "Network error: " + t.getMessage());
            }
        });
    }
}
