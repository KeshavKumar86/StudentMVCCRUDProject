package com.example.studentmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginPage")
    public String loginPage(){
        return "students/fancy-login";
    }
    @GetMapping("/access-denied")
    public String showAccessDeniedPage()
    {

        return "students/access-denied";
    }
}
