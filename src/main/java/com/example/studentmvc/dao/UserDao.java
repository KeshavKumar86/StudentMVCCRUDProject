package com.example.studentmvc.dao;

import com.example.studentmvc.entity.User;

public interface UserDao {
    User findByUserName(String userName);
    void saveUser(User theUser);
}
