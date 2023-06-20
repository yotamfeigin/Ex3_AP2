package com.example.myapplication.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.daos.MessageDao;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.MessageRet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private Retrofit retrofit;

    private WebServiceAPI api;
    private MessageDao dao;
    private String chatId;
    private Chat chat;
    private User userActive;
    private MutableLiveData<List<Message>> messages;

    public MessageAPI(MutableLiveData<List<Message>> messages, MessageDao dao, String chatId, Chat chat, User userActive) {
        this.messages = messages;
        this.dao = dao;
        this.chatId = chatId;
        this.chat = chat;
        this.userActive = userActive;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(WebServiceAPI.class);
    }


    public void get() {
        Call<List<MessageRet>> call = api.getMessages(chatId, "Bearer " + userActive.getToken());
        call.enqueue(new Callback<List<MessageRet>>() {
            @Override
            public void onResponse(Call<List<MessageRet>> call, Response<List<MessageRet>> response) {
                if (response.isSuccessful()) {
                    List<MessageRet> userResponse = response.body();
                    Log.d("MessagesRet", userResponse.toString());
                    dao.deleteAll();
                    for(MessageRet m : userResponse){
                        Message n = new Message(chatId,m.getId(), m.getCreated(), m.getSender().getUsername(), m.getContent());
                        dao.insert(n);
                    }

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

    public void post(Message msg) {
        Call<Void> call = api.sendMessage(msg.getContent(), chatId, userActive.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    dao.insert(msg);
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

