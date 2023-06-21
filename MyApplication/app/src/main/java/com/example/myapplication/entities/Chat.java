package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.objects.LastMessage;
import com.example.myapplication.objects.UserRet;

@Entity
public class Chat {

    @PrimaryKey @NonNull
    private String id;
    private String username;
    private String displayName;
    private String profilePic;

    private int last_id;
    private String created;
    private String content;

    public Chat(@NonNull String id, String username, String displayName, String profilePic, int last_id, String created, String content) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.last_id = last_id;
        this.created = created;
        this.content = content;
    }

    public Chat(@NonNull String id, UserRet user, LastMessage lastMessage) {
        this.id = id;
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.profilePic = user.getProfilePic();
        last_id = lastMessage.getId();
        created = lastMessage.getCreated();
        content = lastMessage.getContent();
    }
    public Chat(@NonNull String id, User user, LastMessage lastMessage) {
        this.id = id;
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.profilePic = user.getProfilePic();

        if (lastMessage == null){
            last_id = -1;
            created = null;
            content = null;
        }
        else {
        last_id =  lastMessage.getId() ;
        created = lastMessage.getCreated();
        content = lastMessage.getContent();
    }}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getLast_id() {
        return last_id;
    }

    public void setLast_id(int last_id) {
        this.last_id = last_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public User getUser() {
        User u = new User(username, profilePic, displayName);
        return u;
    }



    public LastMessage getLastMessage() {
        LastMessage l = new LastMessage(last_id, created, content);
        return l;
    }

        public void setLastMessage(LastMessage lastMessage) {
            last_id = lastMessage.getId();
            created = lastMessage.getCreated();
            content = lastMessage.getContent();
        }
}
