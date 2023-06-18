package com.example.myapplication.interfaces;

import com.example.myapplication.entities.Chat;

import java.util.List;

public interface ChatCallback {
    void onChatsFetched(List<Chat> fetchedChats);
    void onChatsFetchFailure(Throwable t);
}
