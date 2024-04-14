package com.example.studentmvc.dao;

import com.example.studentmvc.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao{

    private final EntityManager entityManager;

    //constructor injection
    public RoleDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public Role findRoleByName(String name) {
        TypedQuery<Role> typedQuery = entityManager.createQuery("from Role where name = :name",Role.class);
        typedQuery.setParameter("name",name);
        Role role = null;
        try{
            role = typedQuery.getSingleResult();
        } catch (Exception e) {
            role = null;
        }
        return  role;
    }
}
