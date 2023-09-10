package com.springboot.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// With this class and its config, SpringBoot will NOT use 'default' username and password.
// Instead, it will refer the usernames and passwords from this class

@Configuration
public class DemoSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        configurer -> configurer
                                // Allow those who has "EMPLOYEE" role to access"/"
                                .requestMatchers("/").hasRole("EMPLOYEE")
                                // Allow those who has "MANAGER" role to access"/leaders/**"
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                // Allow those who has "ADMIN" role to access"/systems/**"
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .anyRequest().authenticated() // Any request must be authenticated
                    )
                    .formLogin(
                            form -> form
                                    .loginPage("/showLoginPage")  // Customize the form
                                    .loginProcessingUrl("/authenticateTheUser") // Login form should POST data to this URL for processing
                                    .permitAll() // Allow anyone to see login page
                    )
                    .logout(logout -> logout.permitAll())
//                    If access denied, redirect to 'access-denied' page
                    .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
        return httpSecurity.build();
//        Need to create a controller for this request mapping '/showLoginPage/' (GET)
//        No controller request mapping required for '/authenticateTheUser'. BUT this link will be use in login_page.html
    }
}
