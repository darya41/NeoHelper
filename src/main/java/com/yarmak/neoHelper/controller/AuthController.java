package com.yarmak.neoHelper.controller;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yarmak.neoHelper.dao.DaoException;
import com.yarmak.neoHelper.model.Doctor;
import com.yarmak.neoHelper.model.Mother;
import com.yarmak.neoHelper.model.Specialization;
import com.yarmak.neoHelper.service.AuthService;
import com.yarmak.neoHelper.service.MotherService;
import com.yarmak.neoHelper.service.SpecializationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	private final SpecializationService specializationService; 
	private final MotherService motherService;
	
	 private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping("/")
	public String showLoginForm(@RequestParam(required = false) String error, Model model) {
			if (error != null) {
				model.addAttribute("error", "Неверный логин или пароль");
			}
			return "login";
		}
	    
	@PostMapping("/login")
	public String login(
			@RequestParam(required = false) String login,
	        @RequestParam String password,
	        HttpSession session,
	        Model model,
	        RedirectAttributes redirectAttributes) {	   
	    
	    try {
	        Doctor doctor = authService.authenticate(login, password);	        
	        session.setAttribute("currentDoctor", doctor);
	        
	        List<Mother> mothers = motherService.getAllMothers();
	        redirectAttributes.addFlashAttribute("patients", mothers);
	        return "main";
	        
	    } catch (Exception e) {
	        log.error("Unexpected error during login for user {}: {}", login, e.getMessage(), e);
	        model.addAttribute("error", "Произошла техническая ошибка. Пожалуйста, попробуйте позже.");
	        return "login";
	    }
	}
	 
	 @GetMapping("/register")
	 public String showRegistrationForm(Model model) {
	     model.addAttribute("doctor", new Doctor());
	     List<Specialization>  list = specializationService.getAllSpecializations();	     
	     model.addAttribute("specializations", list);
	     return "register";
	 }
	 
	 

	 @PostMapping("/registerNewDoctor")
	 public String registerDoctor(@ModelAttribute("doctor") Doctor doctor,
	                            BindingResult bindingResult,
	                            @RequestParam String confirmPassword,
	                            @RequestParam Long specializationId,
	                            Model model) {
	     
	     Logger logger = LoggerFactory.getLogger(AuthController.class);
	     logger.info("Начало процесса регистрации доктора: " + doctor.getLogin());

	     Optional<Specialization> specialization = null;
		try {
			specialization = specializationService.findById(specializationId);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     if (specialization.isEmpty()) {
	         bindingResult.rejectValue("specialization", "error.doctor", "Выберите корректную специальность");
	     } else {
	         doctor.setSpecialization(specialization.get());
	     }

	     if (!doctor.isTermsAccepted()) {
	         bindingResult.rejectValue("termsAccepted", "error.doctor", "Необходимо согласиться с условиями использования");
	         logger.warn("Доктор не принял условия использования");
	     }

	     if (!doctor.getPassword().equals(confirmPassword)) {
	         bindingResult.rejectValue("password", "error.doctor", "Пароли не совпадают");
	         logger.warn("Пароли не совпадают для пользователя: " + doctor.getLogin());
	     }

	     if (authService.emailExists(doctor.getWorkEmail())) {
	         bindingResult.rejectValue("workEmail", "error.doctor", "Email уже используется");
	         logger.warn("Email уже используется: " + doctor.getWorkEmail());
	     }

	     if (authService.loginExists(doctor.getLogin())) {
	         bindingResult.rejectValue("login", "error.doctor", "Логин уже занят");
	         logger.warn("Логин уже занят: " + doctor.getLogin());
	     }

	     if (bindingResult.hasErrors()) {
	         bindingResult.getAllErrors().forEach(error -> 
	             logger.warn("Ошибка валидации: " + error.getDefaultMessage()));
	         model.addAttribute("specializations", specializationService.getAllSpecializations());
	         return "register";
	     }

	     authService.registerDoctor(doctor);

	     return "login";
	 }
}
