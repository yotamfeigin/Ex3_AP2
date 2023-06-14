package com.example.myapplication.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegsiterServiceAPI {
    @FormUrlEncoded
    @POST("Users")
    Call<JsonObject> register(@Field("username") String username, @Field("password") String password, @Field("displayName") String displayName, @Field("profilePic") String profilePic);
}
