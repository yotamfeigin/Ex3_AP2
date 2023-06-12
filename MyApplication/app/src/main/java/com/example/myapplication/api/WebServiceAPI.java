package com.example.myapplication.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @FormUrlEncoded
    @POST("Tokens")
    Call<String> postLogin(@Field("username") String username, @Field("password") String password);}
