package com.springboot.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

//    All EMPLOYEEs link
    @GetMapping("/")
    public String showHome() {
        return "home";
    }

//    All MANAGERs link
    @GetMapping("/leaders")
    public String showLeaders() {
        return "leaders";
    }

//    All ADMINs link
    @GetMapping("/systems")
    public String showSystems() {
        return "systems";
    }
}
