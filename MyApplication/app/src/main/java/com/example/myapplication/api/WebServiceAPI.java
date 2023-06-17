package com.example.myapplication.api;

import com.example.myapplication.Entities.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServiceAPI {

    @POST("Users/{username}")
    Call<User> login(@Path("username") String id, @Body User user);

    @POST("Users")
    Call<Void> register(@Body User user);

    @GET("Users")
    Call<List<User>> index();

    @POST("api/Tokens")
    Call<Void> createToken (@Body String token, @Query("user") String user);
}

