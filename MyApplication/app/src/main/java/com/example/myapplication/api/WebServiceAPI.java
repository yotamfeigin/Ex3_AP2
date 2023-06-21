package com.example.myapplication.api;

import com.example.myapplication.entities.User;
import com.example.myapplication.objects.ChatRet;
import com.example.myapplication.objects.MessageRet;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // Users
    @FormUrlEncoded
    @POST("Tokens/")
    Call<JsonObject> postLogin(@Field("username") String username, @Field("password") String password);

    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String username, @Header("Authorization") String token );


    // Chats
    @GET("Chats")
    Call<List<ChatRet>> getChats(@Header("Authorization") String token );

    @POST("Chats")
    Call<Void> newChat(@Header("Authorization") String token, @Body() String username);

    @DELETE("Chats/{chatId}")
    Call<Void> deleteChat(@Header("Authorization") String token, @Path("chatId") String chatId);


    //Messages
    @GET("Chats/{chatId}/Messages")
    Call<List<MessageRet>> getMessages(@Path("chatId") String chatId, @Header("Authorization") String token );

    @POST("Chats/{chatId}/Messages")
    Call<Void> sendMessage(@Body JsonObject body, @Path("chatId") String chatId, @Header("Authorization") String token);

}

