package com.example.studentmvc.controller;

import com.example.studentmvc.entity.User;
import com.example.studentmvc.service.UserService;
import com.example.studentmvc.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;

    //constructor injection
    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
    }


    //endpoint to show registration form
    @GetMapping("/showRegistrationForm")
    public  String showForm(Model model){
        WebUser user = new WebUser();
        //bind with model
        model.addAttribute("webUser",user);
        return "students/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processForm(
            @Valid @ModelAttribute("webUser") WebUser webUser,
            BindingResult bindingResult, HttpSession session, Model model) {
        String userName = webUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        //form validation
        if (bindingResult.hasErrors()) {
            return "students/registration-form";
        }

        //check the database if user already exist
        User userExisting = userService.findByUserName(userName);
        if(userExisting != null){
            model.addAttribute("webUser", new WebUser());
            model.addAttribute("registrationError","User name already exists.");
            logger.warning("User name already exists.");
            return "students/registration-form";

        }
        //create user account and store in the database
        userService.save(webUser);
        logger.info("Successfully created user: " + userName);

        //place user in the web http session for the later use
        session.setAttribute("user", webUser);


        return "students/registration-confirmation";
    }
}
