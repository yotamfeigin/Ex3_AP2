package com.example.myapplication.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.api.MyApplication;
import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.db.ChatDB;
import com.example.myapplication.objects.ChatRet;

import java.util.ArrayList;
import java.util.List;

public class ChatsRepository {
    private ChatDB chatDB;
    private ChatDao chatDao;
    private ChatsListData chatsListData;
    private ChatAPI chatApi;


    public ChatsRepository() {
        chatDB= Room.databaseBuilder(MyApplication.context, ChatDB .class, "chatDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        chatDao = chatDB.ChatDao();
        chatsListData = new ChatsListData();
        chatApi = new ChatAPI(///////);
    }

    class ChatsListData extends MutableLiveData<List<ChatRet>> {
        public ChatsListData() {
            super();
            setValue(new ArrayList<>());
        }
        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->
                    chatsListData.postValue((chatDao.getAllChatRet()))).start();
        }
    }

    public MutableLiveData<List<ChatRet>> getAll(){
        return chatsListData;
    }

    public ChatRet get (String id, String user) {
        return contactsDao.get(id, user);
    }



    public void add (final Contact contact){
        contactsDao.insert(contact);
        contactsApi.add(contact);
    }

    public void delete (final Contact contact){
        contactsDao.delete(contact);
    }

    public void update (final Contact contact) {
        contactsDao.update(contact);
    }



}
}
