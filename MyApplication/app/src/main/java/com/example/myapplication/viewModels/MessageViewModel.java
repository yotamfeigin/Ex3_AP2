package com.example.myapplication.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.User;
import com.example.myapplication.repository.ChatsRepository;
import com.example.myapplication.repository.MessagesRepository;

import java.util.List;

public class MessageViewModel extends ViewModel {

    private MessagesRepository messagesRepository;
    private ChatsRepository chatsRepository;
    private MutableLiveData<List<Message>> messages;
    private User userActive;

    String stringId;

    public MessageViewModel(User user, String stringId){
        super();
        this.stringId = stringId;
        this.userActive = user;
        chatsRepository = new ChatsRepository(userActive);
        messagesRepository = new MessagesRepository(userActive ,this.stringId, chatsRepository);
        messages = messagesRepository.getAll();
    }

    public MutableLiveData<List<Message>> get(){
        messagesRepository.getContactMessages();
        return messages;
    }


    public MutableLiveData<List<Message>> getContactMessages(){
        return messagesRepository.getContactMessages();
    }

    public void add(String msg){
        messagesRepository.add(msg);
    }

//    public void delete(Message msg){ messagesRepository.delete(msg);}

    public void reload() {messagesRepository.reload();}

    public void getAll(String last){
        messagesRepository.getContactMessages();
    }

}