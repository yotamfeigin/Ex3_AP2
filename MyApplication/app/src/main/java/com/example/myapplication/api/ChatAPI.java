package com.example.myapplication.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.ChatRet;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChatAPI {


    private Retrofit retrofit;

    private WebServiceAPI api;
    private MutableLiveData<List<Chat>> chats;

    private ChatDao dao;

    private User  user;

    public ChatAPI(User user, MutableLiveData<List<Chat>> chats, ChatDao dao) {
        this.chats = chats;
        this.dao = dao;
        this.user = user;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(WebServiceAPI.class);
    }

    public void getChats() {
        Call<List<ChatRet>> call = api.getChats("Bearer " + user.getToken());
        call.enqueue(new Callback<List<ChatRet>>() {
            @Override
            public void onResponse(Call<List<ChatRet>> call, Response<List<ChatRet>> response) {
                if (response.isSuccessful()) {
                    List<ChatRet> userResponse = response.body();
                    Log.d("ChatRet", userResponse.toString());
                    dao.deleteAll();
                    for (ChatRet chat : userResponse) {
                        Chat c = new Chat(chat.getId(), chat.getUser(), chat.getLastMessage());
                        dao.insert(c);
                    }
                    // Notify observers that the chat list has been updated
                    chats.setValue(dao.getAll());
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
    public void add(String username) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("username", username);

        Call<Void> call = api.newChat("Bearer "  +user.getToken(), requestBody);        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                   getChats();
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

    public void delete(Chat chat) {
        Call<Void> call = api.deleteChat(user.getToken(), chat.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    dao.delete(chat);
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
