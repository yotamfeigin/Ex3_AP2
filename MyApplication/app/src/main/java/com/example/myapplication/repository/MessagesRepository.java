package com.example.myapplication.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.api.MessageAPI;
import com.example.myapplication.api.MyApplication;
import com.example.myapplication.daos.MessageDao;
import com.example.myapplication.db.MessageDB;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class MessagesRepository {

    private MessageDB dataBase;
    private MessageDao dao;
    private MessageListData messageListData;
    private MessageAPI api;
    private User userActive;
    private Chat chat;
    private String chatId;


    public MessagesRepository(User user, String stringId, ChatsRepository chatsRepository) {
        this.chatId = stringId;
        this.userActive = user;
        this.chat = chatsRepository.get(chatId);
        dataBase = Room.databaseBuilder(MyApplication.context, MessageDB.class, "MessagesDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        dao = dataBase.messageDao();
        messageListData = new MessageListData();
        api = new MessageAPI(messageListData, dao, chat.getId(),chat, userActive);
        api.get();
    }

    public void get(){
        api.get();
    }


    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive(){
            super.onActive();
            new Thread(()-> messageListData.postValue(dao.get(chat.getId()))).start();
        }
    }

    public MutableLiveData<List<Message>> getAll(){
        return messageListData;
    }

    public MutableLiveData<List<Message>> getContactMessages(){
        messageListData.postValue(dao.get(chat.getId()));
        return messageListData;
    }

    public void add (Message msg){
        api.post(msg);
    }

//    public void delete (final Message msg){
//        api.delete(msg);
//    }

    public void reload(){
        dao.index();
    }
}