package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
    public class MainController {

        @GetMapping("/")
        public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
            return "index";
        }
    @GetMapping("/contact")
    public String contact(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        return "contact";
    }

        @GetMapping("/foglalas")
        public String foglal(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
            model.addAttribute("name", name);
            return "foglalas";
        }

    }


