package com.example.studentmvc.service;

import com.example.studentmvc.entity.Student;
import com.example.studentmvc.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    //constructor injection
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    @Override
    public void save(Student student) {
       studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudent(int id) {
        return studentRepository.findById(id);

    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

}
