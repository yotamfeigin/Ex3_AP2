package com.example.myapplication.api;

import com.example.myapplication.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
 Retrofit retrofit;
 WebServiceAPI webServiceAPI;
String username;
String password;
         public LoginAPI(String username,String password) {
        this.password=password;
        this.username=username;
         retrofit = new Retrofit.Builder()
         .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
         .addConverterFactory(GsonConverterFactory.create())
         .build();
         webServiceAPI = retrofit.create(WebServiceAPI.class);
         }

         public void postLogin() {
             String call = webServiceAPI.postLogin(this.username, this.password).toString();
         }
         }