package com.example.myapplication;

public class User {
    private String id;
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

    public void setId(String id) {
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

    public String getId() {
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
