package com.example.myapplication;

import androidx.room.Room;

import com.example.myapplication.Daos.UserDao;
import com.example.myapplication.Entities.User;
import com.example.myapplication.api.LoginAPI;
import com.example.myapplication.api.MyApplication;

public class UserRepository {
    private UserDataBase userDB;
    private UserDao userDao;
    private LoginAPI userApi;
    private String userActive;

    public UserRepository(String userActive) {
        userDB = Room.databaseBuilder(MyApplication.context, UserDataBase.class, "userDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = userDB.userDao();
        this.userActive = userActive;
        userApi = new LoginAPI(userDao, userActive);
        userApi.getAll();
    }

    public boolean register (final User user, String confirmPassword){
        if (userDao.checkUser(user.getUsername()) == null) {
            boolean checkResult = checkValidPassword(user.getPassword(), confirmPassword);
            if(checkResult) {
                userApi.register(user);
                userDao.register(user);
                return true;
            }
        }
        return false;
    }

    public User login (String id, String password){
        return userDao.login(id, password);
    }

    public void createToken(String token) {
        userApi.createToken(token);
    }

    public User getUser (String id) {
        return userDao.checkUser(id);
    }

    public void update (final User user) {
        userDao.update(user);
    }

    //to change!!!!!!!!!!!!!!!
    public boolean checkValidPassword(String password, String confirmPassword) {
        int passwordSize = password.length();
        if (passwordSize < 2 || confirmPassword.length() < 2) {
            return false;
        }
        if(!password.equals(confirmPassword)) {
            return false;
        }
        int lettersNum = 0;
        int numbersNum = 0;
        for (int i=0; i < passwordSize; i++) {
            if ((!Character.isLetterOrDigit(password.charAt(i)))) {
                return false;
            } else if (Character.isDigit(password.charAt(i))){
                numbersNum ++;
            } else {
                lettersNum ++;
            }
        }
        if (numbersNum > 0 && lettersNum > 0) {
            return true;
        }
        return false;
    }
}

