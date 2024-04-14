package com.example.studentmvc.dao;

import com.example.studentmvc.entity.Role;
import com.example.studentmvc.entity.User;

public interface RoleDao {
    Role findRoleByName(String name);
}
