package com.example.myapplication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.daos.MessageDao;
import com.example.myapplication.entities.Chat;
import com.example.myapplication.entities.Message;

@Database(entities = {Message.class, Chat.class}, version = 1)
public abstract class AppDB extends RoomDatabase{
    public abstract MessageDao messageDao();
    public abstract ChatDao ChatDao();
}
