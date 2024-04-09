package com.example.studentmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class StudentSecurityConfig {
    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
//        UserDetails user1 = User.builder().username("Ritesh")
//                .password("{noop}ritesh").roles("STUDENT").build();
//        UserDetails user2 = User.builder().username("Ankur")
//                .password("{noop}ankur").roles("STUDENT","FACULTY").build();
//        UserDetails user3 = User.builder().username("Keshav")
//                .password("{noop}keshav").roles("STUDENT","FACULTY","HOD").build();
        //return new InMemoryUserDetailsManager(user1, user2, user3);

       return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer->
                configurer.requestMatchers("/api/list").hasRole("STUDENT")
                        .requestMatchers("/api/showFormForAdd").hasRole("FACULTY")
                        .requestMatchers("/api/showFormForUpdate**").hasRole("HOD")
                        .requestMatchers("/api/delete**").hasRole("HOD")
                        .anyRequest().authenticated())
                .formLogin(form->form.loginPage("/loginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll())
                .logout(logout->logout.permitAll())
                .exceptionHandling(configurer->configurer
                        .accessDeniedPage("/access-denied"));
        return httpSecurity.build();
    }
}
