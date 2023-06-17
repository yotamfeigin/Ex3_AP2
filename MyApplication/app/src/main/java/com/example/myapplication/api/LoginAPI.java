package com.example.myapplication.api;

import android.net.Uri;
import android.util.Log;

import com.example.myapplication.Daos.UserDao;
import com.example.myapplication.Entities.User;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
   WebServiceAPI webServiceApi;
    private UserDao userDao;
    private String userActive;
    public LoginAPI(UserDao userDao, String userActive) {
        this.userActive = userActive;
        this.userDao = userDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceApi = retrofit.create(WebServiceAPI.class);
    }

    public void getAll () {
        Call<List<User>> call = webServiceApi.index();
        call.enqueue(new Callback<List<User>>() {
            @Override
            //when the response is ready
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                new Thread(() -> {
                    List<User> users1 = response.body();
                    userDao.delete(userDao.index().toArray(new User[0]));
                    for (User user: users1) {
                        user.setProfilePicture(Uri.parse("android.resource://com.example.whatsapp_clone_test/drawable/image_name").getPath());
                        userDao.register(user);
                    }
                }).start();
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }

    public void createToken (String token) {
        Call<Void> call = webServiceApi.createToken(token, userActive);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }
    public void register (User user) {
        Call<Void> call = webServiceApi.register(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                userDao.register(user);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }
}
