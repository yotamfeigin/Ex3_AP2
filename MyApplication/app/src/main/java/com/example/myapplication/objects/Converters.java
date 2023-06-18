package com.example.myapplication.objects;

import androidx.room.TypeConverter;

import com.example.myapplication.entities.User;
import com.google.gson.Gson;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static User fromJsonString(String value) {
        return gson.fromJson(value, User.class);
    }

    @TypeConverter
    public static String toJsonString(User user) {
        return gson.toJson(user);
    }

    @TypeConverter
    public static LastMessage fromJsonStringLastMessage(String value) {
        return gson.fromJson(value, LastMessage.class);
    }

    @TypeConverter
    public static String toJsonStringLastMessage(LastMessage lastMessage) {
        return gson.toJson(lastMessage);
    }
}
