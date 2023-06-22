package com.example.myapplication.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.MyApplication;
import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.db.ChatDB;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ChatsRepository {
    private ChatDB chatDB;
    private ChatDao chatDao;
    private ChatsListData chatsListData;
    private ChatAPI chatApi;


    public ChatsRepository(User currentUser) {
        chatDB= Room.databaseBuilder(MyApplication.context, ChatDB .class, "chatDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        chatDao = chatDB.ChatDao();
        chatsListData = new ChatsListData();
        chatApi = new ChatAPI(currentUser,chatsListData,chatDao);
        chatApi.getChats();
    }

    public Chat getByName(String contactUserName) {
        return chatDao.getChatUser(contactUserName);
    }

    class ChatsListData extends MutableLiveData<List<Chat>> {
        public ChatsListData() {
            super();
            setValue(new ArrayList<>());
        }
        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
                    chatsListData.postValue((chatDao.getAll()))).start();
        }
    }

    public MutableLiveData<List<Chat>> getAll(){
        chatsListData.postValue(chatDao.getAll());

        return chatsListData;
    }

    public Chat get (String id) {
        return chatDao.getChat(id);
    }



    public void add (String name){
        chatApi.add(name);
    }

    public void delete (final Chat chat){
        chatApi.delete(chat);
    }


}

