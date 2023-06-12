package com.example.myapplication.api;

import retrofit2.Call;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @POST("Tokens")
    Call<String> postLogin(String username,String password);
}
