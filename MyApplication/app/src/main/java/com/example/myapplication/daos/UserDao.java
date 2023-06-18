package com.example.myapplication.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE id = :id")
    User get(int id);

    @Insert
    void insert(User... users);
}
