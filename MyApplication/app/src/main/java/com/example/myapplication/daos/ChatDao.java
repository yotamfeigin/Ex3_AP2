package com.example.myapplication.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat")
    List<Chat> index();

    @Query("SELECT * FROM chat WHERE id = :chatId")
    Chat getChat(int chatId);

    @Insert
    void insert(Chat... chats);

    @Delete
    void delete(Chat... chats);
}
