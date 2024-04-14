package com.example.studentmvc.dao;

import com.example.studentmvc.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements  UserDao {
    private EntityManager entityManager;

    //constructor Injection
    public UserDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public User findByUserName(String userName) {
        TypedQuery<User> typedQuery = entityManager.createQuery("from User where userName=:userName and enabled=true",User.class);
        typedQuery.setParameter("userName",userName);
        User user = null;
        try{
            user = typedQuery.getSingleResult();
        } catch (Exception e) {
            user = null;
        }
        return user;
    }
    @Transactional
    @Override
    public void saveUser(User theUser) {
        entityManager.merge(theUser);
    }
}
