package com.example.SecurirtySpring.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/public/hello")
    public String publicHello() { 
    	return "Hello Public"; 
    	}

    @GetMapping("/user/dashboard")
    public String userDashboard() { 
    	return "USER dashboard";
    	}

    @GetMapping("/admin/dashboard")
    public String adminDashboard() { 
    	return "ADMIN dashboard";
    	}
}
