package com.example.studentmvc.service;

import com.example.studentmvc.dao.RoleDao;
import com.example.studentmvc.entity.Role;
import com.example.studentmvc.entity.User;
import com.example.studentmvc.dao.UserDao;
import com.example.studentmvc.user.WebUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    //constructor injection
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        //check the database if user already exist
        return userDao.findByUserName(userName);
    }

    @Override
    public void save(WebUser webUser) {

        User user = new User();
        //assign webUser details to the user object
        user.setUserName(webUser.getUserName());
        //encode the plain password then set in user
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEmail(webUser.getEmail());
        user.setEnabled(true);

        //give user default role of employee
        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_STUDENT")));
        System.out.println("User to be saved" + user);

        //save user in the database
        userDao.saveUser(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);

        if(user == null)
             throw  new UsernameNotFoundException("Invalid username or password");

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),user.getPassword(),authorities);

    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for(Role role: roles){
//            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(role.getName());
//            authorities.add(tempAuthority);
//        }
//        return  authorities;
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
