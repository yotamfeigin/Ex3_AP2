package com.example.myapplication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.daos.MessageDao;
import com.example.myapplication.entities.Message;

@Database(entities = {Message.class,}, version = 1)
public abstract class MessageDB extends RoomDatabase{
    public abstract MessageDao messageDao();
}
