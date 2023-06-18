package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chat {
    public Chat(@NonNull String id, String username, String chatWith) {
        this.id = id;
        this.username = username;
        this.chatWith = chatWith;
    }

    @PrimaryKey
    @NonNull
    private String id;
    private String username;

    private String lastMessage;
    private String lastMessageTime;


    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChatWith(String chatWith) {
        this.chatWith = chatWith;
    }

    private String chatWith;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getChatWith() {
        return chatWith;
    }
}
