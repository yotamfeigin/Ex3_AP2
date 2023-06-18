package com.example.myapplication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.daos.ChatDao;
import com.example.myapplication.entities.Chat;

@Database(entities = {Chat.class,}, version = 1)
public abstract class ChatDB extends RoomDatabase {
    public abstract ChatDao ChatDao();
}
