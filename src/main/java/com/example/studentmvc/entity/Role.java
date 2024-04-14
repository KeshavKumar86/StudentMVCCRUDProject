package com.example.studentmvc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    //define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    //define constructors
    public Role(){

    }

    public Role(String name) {
        this.name = name;
    }
    //define getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //define toString

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //define mapping with db table
}
