package com.example.myapplication.api;

import com.example.myapplication.objects.ChatRet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ChatApiService {
    @GET("/api/chats/{currentUser}")
    Call<List<ChatRet>> getChats(@Header("Authorization") String token);
}
