package com.example.myapplication.objects;

public class ChatRet {
    private String id;
    private UserRet user;
    private LastMessage lastMessage;

    public ChatRet(String id, UserRet user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserRet getUser() {
        return user;
    }

    public void setUser(UserRet user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}
