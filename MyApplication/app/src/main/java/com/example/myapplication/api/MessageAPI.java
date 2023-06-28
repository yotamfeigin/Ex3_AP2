package com.example.myapplication.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.daos.MessageDao;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.MessageRet;
import com.google.gson.JsonObject;

import java.io.IOException;
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
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences(
                MyApplication.context.getString(R.string.SharedPrefs),
                Context.MODE_PRIVATE
        );
        String baseUrl = sharedPreferences.getString("BaseUrl", MyApplication.context.getString(R.string.BaseUrl));
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(WebServiceAPI.class);
    }


    public void get() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<MessageRet>> call = api.getMessages(chatId, "Bearer " + userActive.getToken());
                try {
                    Response<List<MessageRet>> response = call.execute();
                    if (response.isSuccessful()) {
                        List<MessageRet> userResponse = response.body();
                        Log.d("MessagesRet", userResponse.toString());
                        dao.deleteAll();
                        for(MessageRet m : userResponse){
                            Message n = new Message(chatId, m.getId(), m.getCreated(), m.getSender().getUsername(), m.getContent());
                            dao.insert(n);

                        }
                        messages.postValue(dao.get(chatId));
                    } else {
                        // Handle error cases for GET request
                        String errorMessage = "Error: " + response.code();
                        // Display error message or handle accordingly
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle IO exception
                }
            }
        });
    }


    public void post(String msg) {
        JsonObject body = new JsonObject();
        body.addProperty("msg", msg);
        Call<Void> call = api.sendMessage(body, chatId, "Bearer " + userActive.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    get();
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

