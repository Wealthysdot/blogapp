package com.blogapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {


        @GetMapping("/home")
        public @ResponseBody String showWelcome(){
            return "Welcome to Pentax!!!";
        }

}
