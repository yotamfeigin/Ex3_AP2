package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Daos.UserDao;
import com.example.myapplication.Entities.User;

@Database(entities = {User.class}, version = 2)

public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}