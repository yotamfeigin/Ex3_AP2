package com.example.myapplication.api;

import android.util.Log;

import com.example.myapplication.entities.User;
import com.example.myapplication.objects.MessageRet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageAPI {
    private WebServiceAPI webServiceAPI;

    public MessageAPI(WebServiceAPI webServiceAPI) {
        this.webServiceAPI = webServiceAPI;
    }

    public void get(User user, String chatId) {
        Call<List<MessageRet>> call = webServiceAPI.getMessages(chatId, "Bearer " + user.getToken());
        call.enqueue(new Callback<List<MessageRet>>() {
            @Override
            public void onResponse(Call<List<MessageRet>> call, Response<List<MessageRet>> response) {
                if (response.isSuccessful()) {
                    List<MessageRet> userResponse = response.body();
                    Log.d("MessagesRet", userResponse.toString());

                } else {
                    // Handle error cases for GET request
                    String errorMessage = "Error: " + response.code();
                    // Display error message or handle accordingly
                }
            }

            @Override
            public void onFailure(Call<List<MessageRet>> call, Throwable t) {
                // Handle failure cases
                t.printStackTrace();
            }
        });

    }

    public void post(User user, String chatId, String msg) {
        Call<Void> call = webServiceAPI.sendMessage(msg, chatId, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                } else {
                    // Handle error cases for GET request
                    String errorMessage = "Error: " + response.code();
                    // Display error message or handle accordingly
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure cases
                t.printStackTrace();
            }
        });

    }
}

