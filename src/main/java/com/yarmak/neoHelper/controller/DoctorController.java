package com.yarmak.neoHelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yarmak.neoHelper.model.Doctor;
import com.yarmak.neoHelper.service.DoctorService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
	
	 private final DoctorService doctorService;

	   
	
	 @GetMapping("/profile")
	    public String showDoctorProfile(HttpSession session, Model model) {
	        Doctor doctor = (Doctor) session.getAttribute("currentDoctor");
	        
	        if (doctor == null) {
	            return "redirect:/login";
	        }
	        
	        model.addAttribute("sessionDoctor", doctor);
	        model.addAttribute("doctor", doctor); 
	        
	        return "profile";
	    }
	 
	 
}
