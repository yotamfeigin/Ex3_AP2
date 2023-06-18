package com.example.myapplication.api;

import android.util.Log;

import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.db.ChatDB;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

public class LoginAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    private ChatAPI chatAPI;
    private String username;
    private String password;
    private UserDB userDB;
    private ChatDB chatDB;


    public LoginAPI(String username, String password) {
        this.username = username;
        this.password = password;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        userDB = Room.databaseBuilder(MyApplication.context, UserDB.class, "user-db").fallbackToDestructiveMigration()
                .build();
        chatDB = Room.databaseBuilder(MyApplication.context, ChatDB.class, "chat-db").fallbackToDestructiveMigration()
                .build();
        chatAPI = new ChatAPI(webServiceAPI);
    }

    public void postLogin(User user) {
        try {
            Call<JsonObject> call = webServiceAPI.postLogin(username, password);
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
                        getUser(user, token);
                        // Handle the JSON object response
                        // ...

                    } else {
                        // Handle unsuccessful response
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

    public void getUser(User user, String token) {
        Call<User> call = webServiceAPI.getUser(username, "Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    Log.d("userResponse", userResponse.toString());
                    user.setUsername(userResponse.getUsername());
                    user.setToken(token);
                    user.setDisplayName(userResponse.getDisplayName());
                    user.setProfilePic(userResponse.getProfilePic());

                    // Save the user to Room
                    Executors.newSingleThreadExecutor().execute(() -> {
                        userDB.UserDao().insert(user);

                    });
                    List<Chat> chats = new ArrayList<>();
                    chatAPI.getChats(user, chats);
                } else {
                    // Handle error cases for GET request
                    String errorMessage = "Error: " + response.code();
                    // Display error message or handle accordingly
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure cases
                t.printStackTrace();
            }
        });


    }


}


