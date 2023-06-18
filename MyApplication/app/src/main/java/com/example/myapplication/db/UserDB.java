package com.example.myapplication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.daos.UserDao;
import com.example.myapplication.entities.User;

@Database(entities = {User.class,}, version = 2)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao UserDao();
}


