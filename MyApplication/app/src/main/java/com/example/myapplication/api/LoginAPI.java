package com.example.myapplication.api;

import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.ChatRet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    private ChatAPI chatAPI;
    private String username;
    private String password;
    private String token;

    public LoginAPI(String username, String password) {
        this.username = username;
        this.password = password;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
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
                        token = jsonString;
                        Log.d("token", token);
                        getUser(user);
                        ArrayList<Chat> chats = new ArrayList<>();

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

    public void getUser(User user) {
        Call<User> call = webServiceAPI.getUser(username, "Bearer " + token);
        call.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userResponse = response.body();
                    Log.d("userResponse", userResponse.toString());
                    user.setUsername(userResponse.getUsername());
                    user.setToken(token);
                    user.setDisplayName(userResponse.getDisplayName());
                    user.setPassword(password);
                    user.setProfilePic(userResponse.getProfilePic());
                    List<ChatRet> chats = new ArrayList<>();
                    chatAPI.getChats(user, chats);
                    Log.d("CHATS:", chats.toString());
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


