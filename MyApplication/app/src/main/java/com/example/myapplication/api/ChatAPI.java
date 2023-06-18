package com.example.myapplication.api;

import android.util.Log;

import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.ChatRet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAPI {
    private WebServiceAPI webServiceAPI;

    public ChatAPI(WebServiceAPI webServiceAPI) {
        this.webServiceAPI = webServiceAPI;
    }

    public void getChats(User user, List<Chat> chats) {


        Call<List<ChatRet>> call = webServiceAPI.getChats("Bearer " + user.getToken());
        call.enqueue(new Callback<List<ChatRet>>() {
            @Override
            public void onResponse(Call<List<ChatRet>> call, Response<List<ChatRet>> response) {
                if (response.isSuccessful()) {
                    List<ChatRet> userResponse = response.body();
                    Log.d("ChatRet", userResponse.toString());

                } else {
                    // Handle error cases for GET request
                    String errorMessage = "Error: " + response.code();
                    // Display error message or handle accordingly
                }
            }

            @Override
            public void onFailure(Call<List<ChatRet>> call, Throwable t) {
                // Handle failure cases
                t.printStackTrace();
            }
        });
    }
}
