package com.springboot.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showLoginPage") // ("/showLoginPage") <-- match with the one declared in Security file
    public String showLoginPage() {
        return "login_page";
    }
}
