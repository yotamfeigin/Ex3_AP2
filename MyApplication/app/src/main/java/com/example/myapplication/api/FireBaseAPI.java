package com.example.myapplication.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.R;
import com.example.myapplication.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FireBaseAPI {


    private Retrofit retrofit;

    private WebServiceAPI api;
    private User user;

    public FireBaseAPI(User user) {
        this.user = user;
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





    public void delete() {
        Call<Void> call = api.deleteFireBase(user.getUsername(), "Bearer " + user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //
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
