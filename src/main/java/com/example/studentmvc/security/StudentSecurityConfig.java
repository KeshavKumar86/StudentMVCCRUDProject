package com.example.studentmvc.security;

import com.example.studentmvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class StudentSecurityConfig {

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //authentication provider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder bcrypt
        auth.setUserDetailsService(userService); //set the custom user details service
        return auth;
    }
//    @Bean
//    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
////        UserDetails user1 = User.builder().username("Ritesh")
////                .password("{noop}ritesh").roles("STUDENT").build();
////        UserDetails user2 = User.builder().username("Ankur")
////                .password("{noop}ankur").roles("STUDENT","FACULTY").build();
////        UserDetails user3 = User.builder().username("Keshav")
////                .password("{noop}keshav").roles("STUDENT","FACULTY","HOD").build();
//        //return new InMemoryUserDetailsManager(user1, user2, user3);
//
//       return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer->
                configurer.requestMatchers("/api/list").hasAnyRole("STUDENT","FACULTY","HOD")
                        .requestMatchers("/api/showFormForAdd").hasAnyRole("FACULTY","HOD")
                        .requestMatchers("/api/showFormForUpdate**").hasRole("HOD")
                        .requestMatchers("/api/delete**").hasRole("HOD")
                        .requestMatchers("/register/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->form.loginPage("/loginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll())
                .logout(logout->logout.permitAll())
                .exceptionHandling(configurer->configurer
                        .accessDeniedPage("/access-denied"));
        return httpSecurity.build();
    }
}
