package com.example.studentmvc.controller;

import com.example.studentmvc.entity.Student;
import com.example.studentmvc.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class StudentController {
    private final StudentService studentService;
    //constructor injection
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    //method to list all students
    @GetMapping("/list")
    public String listStudents(Model model){
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("studentList",students);
        return "students/student-list";
    }
    //method to map /student-form endpoint
    @GetMapping("/showFormForAdd")
    public String studentForm(Model model){
        //bind model with the form
        Student student = new Student();
        model.addAttribute("student",student);
        return "students/student-form";
    }
    //method to map /save endpoint
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student){
        //save the student
        studentService.save(student);
        return "redirect:/api/list";
    }
    //method to map /showFormForUpdate endpoint
    @GetMapping("/showFormForUpdate")
    public String updateStudent(@RequestParam("studentId") int studentId, Model model){
        //fetch the student with id
        Optional<Student> student = studentService.getStudent(studentId);
        System.out.println("*************"+student);
        if(student.isPresent())
         model.addAttribute("student", student);
        return "students/student-form";


    }
    @GetMapping("/delete")
    public String deleteStudent(@RequestParam("studentId") int studentId){
       //fetch the student with id
        Optional<Student> student = studentService.getStudent(studentId);
        System.out.println("*************"+student);
        if(student.isPresent())
            studentService.delete(student.get());
        return "redirect:/api/list";
    }
}
