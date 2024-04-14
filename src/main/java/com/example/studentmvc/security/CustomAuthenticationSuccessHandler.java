package com.example.studentmvc.security;

import com.example.studentmvc.entity.User;
import com.example.studentmvc.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    //constructor injection
    public CustomAuthenticationSuccessHandler(UserService userService){
        this.userService = userService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("In CustomAuthenticationSuccessHandler");
        String userName = authentication.getName();
        System.out.println("User name: " + userName);
        User user = userService.findByUserName(userName);

        //Now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user",user);

        //forward to home page
        response.sendRedirect(request.getContextPath()+ "/");
    }
}
