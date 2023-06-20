package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.objects.UserRet;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;
    private String username;
    private String password;
    private String token;
    private String profilePic;
    private String displayName;

    public User(String username, String profilePic, String displayName) {
        this.username = username;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    public User(UserRet copy) {
        this.username = copy.getUsername();
        this.profilePic = copy.getProfilePic();
        this.displayName = copy.getDisplayName();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }
}
