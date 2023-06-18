package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"chatId", "messageId"})
public class Message {
@NonNull
    private String chatId;
@NonNull
    private int messageId;
    private String created;
    private String sender_username;
    private String content;

    public Message(String chatId, int messageId, String created, String sender_username, String content) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.created = created;
        this.sender_username = sender_username;
        this.content = content;
    }

    public String getChatId() {
        return chatId;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSender_username() {
        return sender_username;
    }

    public void setSender_username(String sender_username) {
        this.sender_username = sender_username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
