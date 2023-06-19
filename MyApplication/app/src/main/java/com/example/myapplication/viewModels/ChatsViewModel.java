package com.example.myapplication.viewModels;

import android.arch.lifecycle.LiveData;

import com.example.myapplication.objects.ChatRet;
import com.example.myapplication.repository.ChatsRepository;

import java.util.List;

public class ChatsViewModel {

    private LiveData<List<ChatRet>> chats;
    private ChatsRepository chatsRepository;

    public ContactViewModel() {
        super();
        contactsRepository = new ChatsRepository();
        contacts = contactsRepository.getAll();
    }

    public LiveData<List<ChatRet>> getChats() {
        contactsRepository.getAll();
        return chats;
    }

    public void insert (ChatRet c) {
        chatsRepository.add(c);
        chats = chatsRepository.getAll();
    }

    public ChatRet getContact(String id) {
        return chatsRepository.get(id, userActive);
    }

}
}
