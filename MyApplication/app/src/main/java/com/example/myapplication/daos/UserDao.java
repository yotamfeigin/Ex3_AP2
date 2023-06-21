package com.example.myapplication.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.entities.User;
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    User getCurrentUser();

    @Insert
    void insert(User... users);
}
