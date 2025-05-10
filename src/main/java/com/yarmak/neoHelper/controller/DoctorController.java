package com.yarmak.neoHelper.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yarmak.neoHelper.model.Doctor;
import com.yarmak.neoHelper.model.Mother;
import com.yarmak.neoHelper.model.Specialization;
import com.yarmak.neoHelper.service.MotherService;
import com.yarmak.neoHelper.service.SpecializationService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

	private final MotherService motherService;
	private final SpecializationService specializationService; 
	
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
	 
	 @GetMapping("/back")
	 public String backToMainPage(Model model,
		        RedirectAttributes redirectAttributes) {
		 List<Mother> mothers = motherService.getAllMothers();
	     redirectAttributes.addFlashAttribute("patients", mothers);
		 return "main";
	 }
	 
	 @GetMapping("/edit")
	 public String goToEditProfile(HttpSession session,Model model,
		        RedirectAttributes redirectAttributes) {
		 Doctor doctor = (Doctor) session.getAttribute("currentDoctor");
	        
	        if (doctor == null) {
	            return "redirect:/login";
	        }

	        model.addAttribute("sessionDoctor", doctor);
	        model.addAttribute("doctor", doctor); 
	        
	        List<Specialization>  list = specializationService.getAllSpecializations();	     
		     model.addAttribute("specializations", list);
		 return "editProfile";
	 }
	 
	 
}
