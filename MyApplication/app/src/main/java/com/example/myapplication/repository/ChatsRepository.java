package com.example.myapplication.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.MyApplication;
import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.db.ChatDB;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.objects.ChatRet;

import java.util.ArrayList;
import java.util.List;

public class ChatsRepository {
    private ChatDB chatDB;
    private ChatDao chatDao;
    private User currentUser;
    private ChatsListData chatsListData;
    private ChatAPI chatApi;


    public ChatsRepository(User currentUser) {
        chatDB= Room.databaseBuilder(MyApplication.context, ChatDB .class, "chatDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        chatDao = chatDB.ChatDao();
        chatsListData = new ChatsListData();
        currentUser = currentUser;
        chatApi = new ChatAPI(currentUser,chatDao,chatsListData);
        chatApi.getChats();
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
        return chatsListData;
    }

    public Chat get (String id) {
        return chatDao.getChat(id);
    }



    public void add (final Chat chat){
        chatDao.insert(chat);
        chatApi.add(chat);
    }

    public void delete (final Chat chat){
        chatDao.delete(chat);
        chatApi.delete(chat);
    }




}
}
