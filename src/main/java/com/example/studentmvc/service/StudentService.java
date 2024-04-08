package com.example.studentmvc.service;

import com.example.studentmvc.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    void save(Student student);
    List<Student> getAllStudents();
    Optional<Student> getStudent(int id);
    void delete(Student student);

}
