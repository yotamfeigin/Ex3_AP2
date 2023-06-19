package com.example.myapplication.viewModels;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Chat;
import com.example.myapplication.objects.ChatRet;
import com.example.myapplication.repository.ChatsRepository;

import java.util.List;

public class ChatsViewModel {

    private MutableLiveData<List<Chat>> chats;
    private ChatsRepository chatsRepository;

    public ChatsViewModel() {
        super();
        chatsRepository = new ChatsRepository();
        chats = chatsRepository.getAll();
    }

    public MutableLiveData<List<Chat>> getChats() {
        chatsRepository.getAll();
        return chats;
    }

    public void insert (Chat c) {
        chatsRepository.add(c);
        chats = chatsRepository.getAll();
    }

    public Chat getChat(String id) {
        return chatsRepository.get(id);
    }

}
}
