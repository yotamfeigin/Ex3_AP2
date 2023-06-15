package com.example.myapplication.api;

import com.example.myapplication.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @FormUrlEncoded
    @POST("Tokens/")
    Call<JsonObject> postLogin(@Field("username") String username, @Field("password") String password);

    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String username, @Header("Authorization") String token );

}

