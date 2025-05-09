package com.yarmak.neoHelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yarmak.neoHelper.model.Doctor;
import com.yarmak.neoHelper.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	
	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@GetMapping("/login")
	public String showLoginForm(@RequestParam(required = false) String error, Model model) {
			if (error != null) {
				model.addAttribute("error", "Неверный логин или пароль");
			}
			return "login";
		}
	    
	 @PostMapping("/login")
	    public String login(
	            @RequestParam String login,
	            @RequestParam String password,
	            HttpSession session,
	            Model model) {
	        
	        try {
	            Doctor doctor = authService.authenticate(login, password);
	            session.setAttribute("currentDoctor", doctor);
	            return "redirect:/dashboard";
	        } catch (RuntimeException e) {
	            model.addAttribute("error", e.getMessage());
	            return "login";
	        }
	 }
}
