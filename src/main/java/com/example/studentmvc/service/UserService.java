package com.example.studentmvc.service;

import com.example.studentmvc.entity.User;
import com.example.studentmvc.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save(WebUser webUser);
}
