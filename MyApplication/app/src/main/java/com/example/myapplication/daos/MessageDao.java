package com.example.myapplication.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE chatId = :id")
    List<Message> get(String id);

    @Insert
    void insert(Message... messages);

    @Query("DELETE FROM message")
    void deleteAll();
}
