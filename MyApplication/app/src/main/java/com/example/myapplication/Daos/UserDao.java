package com.example.myapplication.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Insert
    void register(User... Users);

    @Query("SELECT * FROM User WHERE username = :username")
    User checkUser(String username);

    @Update
    void update(User... Users);

    @Delete
    void delete(User... users);

}