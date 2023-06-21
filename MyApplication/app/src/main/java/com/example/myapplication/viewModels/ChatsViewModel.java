package com.example.myapplication.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.ChatAPI;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.User;
import com.example.myapplication.repository.ChatsRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {

    private MutableLiveData<List<Chat>> chats;
    private ChatsRepository chatsRepository;

    public ChatsViewModel(User currentUser) {
        super();
        chatsRepository = new ChatsRepository(currentUser);
        chats = chatsRepository.getAll();
    }

    public MutableLiveData<List<Chat>> getChats() {
        chatsRepository.getAll();
        return chats;
    }


    public void insert (String name) {
        chatsRepository.add(name);

        chats = chatsRepository.getAll();
    }

    public Chat getChat(String id) {
        return chatsRepository.get(id);
    }

    public Chat getContact(String contactUserName) {
        return chatsRepository.getByName(contactUserName);
    }
}

