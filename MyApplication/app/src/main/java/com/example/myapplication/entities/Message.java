package com.example.myapplication.entities;

import androidx.room.Entity;

import java.util.Date;

@Entity(primaryKeys = {"chatId", "messageId"})
public class Message {
    private String chatId;
    private int messageId;
    private Date created;
    private String sender_username;
    private String content;

    public Message(String chatId, int messageId, Date created, String sender_username, String content) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
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
