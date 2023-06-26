package com.example.myapplication.api;

import android.util.Log;

import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.callback.LoginCallback;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.concurrent.Executors;

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
    private UserDB userDB;
    private String fireBaseToken;

    public LoginAPI(String username, String password, String fireBaseToken) {
        this.username = username;
        this.password = password;
        this.fireBaseToken = fireBaseToken;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        userDB = Room.databaseBuilder(MyApplication.context, UserDB.class, "user-db")
                .fallbackToDestructiveMigration()
                .build();
    }

    public void postLogin(User user, LoginCallback callback) {
        try {
            Call<JsonObject> call = webServiceAPI.postLogin(username, password, fireBaseToken);
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
                        String token = jsonString;
                        Log.d("token", token);
                        user.setToken(token);
                        getUser(user, token, callback);
                        // Handle the JSON object response
                        // ...

                    } else {
                        callback.onLoginFailure(new Throwable("Login failed"));
                        Log.e("LoginAPI", "Response error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // Handle network failure
                    Log.e("LoginAPI", "Network error: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUser(User user, String token, LoginCallback callback) {
        Call<User> call = webServiceAPI.getUser(username, "Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    Log.d("userResponse", userResponse.toString());
                    user.setUsername(userResponse.getUsername());
                    user.setDisplayName(userResponse.getDisplayName());
                    user.setProfilePic(userResponse.getProfilePic());
                    // Save the user to Room
                    Executors.newSingleThreadExecutor().execute(() -> {
                        userDB.UserDao().insert(user);
                        callback.onLoginSuccess(user); // Invoke the callback method
                    });

                } else {
                    // Handle error cases for GET request
                    String errorMessage = "Error: " + response.code();
                    // Display error message or handle accordingly
                    callback.onLoginFailure(new Throwable(errorMessage)); // Invoke the callback method
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure cases
                t.printStackTrace();
                callback.onLoginFailure(t); // Invoke the callback method
            }
        });
    }
}
