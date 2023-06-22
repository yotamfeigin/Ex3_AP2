package com.example.myapplication.api;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.ChatRet;
import com.google.gson.JsonObject;

import java.io.IOException;
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
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<ChatRet>> call = api.getChats("Bearer " + user.getToken());
                try {
                    Response<List<ChatRet>> response = call.execute();
                    if (response.isSuccessful()) {
                        List<ChatRet> userResponse = response.body();
                        Log.d("ChatRet", userResponse.toString());
                        dao.deleteAll();
                        for (ChatRet chat : userResponse) {
                            Chat c = new Chat(chat.getId(), chat.getUser(), chat.getLastMessage());
                            dao.insert(c);
                        }
                        chats.postValue(dao.getAll());
                    } else {
                        // Handle error cases for GET request
                        String errorMessage = "Error: " + response.code();
                        // Display error message or handle accordingly
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle failure cases
                }
            }
        });
    }


    public void add(String username) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("username", username);

                Call<Void> call = api.newChat("Bearer " + user.getToken(), requestBody);
                try {
                    Response<Void> response = call.execute();
                    if (response.isSuccessful()) {
                        getChats();
                    } else {
                        // Handle error cases for GET request
                        String errorMessage = "Error: " + response.code();
                        // Display error message or handle accordingly
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle failure cases
                }
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
