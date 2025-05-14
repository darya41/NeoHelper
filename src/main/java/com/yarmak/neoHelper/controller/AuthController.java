package com.yarmak.neoHelper.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yarmak.neoHelper.model.doctor.Doctor;
import com.yarmak.neoHelper.model.doctor.Specialization;
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

    @GetMapping("/")
    public String showLoginForm(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password,
                      HttpSession session, Model model) {
        try {
            Doctor doctor = authService.authenticate(login, password);
            session.setAttribute("currentDoctor", doctor);
            model.addAttribute("patients", motherService.getAllMothers());
            return "main";
        } catch (AuthenticationException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("specializations", specializationService.getAllSpecializations());
        return "register";
    }

    @PostMapping("/registerNewDoctor")
    public String registerDoctor(@ModelAttribute("doctor") Doctor doctor,
                               @RequestParam String confirmPassword,
                               @RequestParam Long specializationId,
                               Model model) {
        try {
            authService.validateDoctorRegistration(doctor, confirmPassword);
            Specialization specialization = specializationService.findById(specializationId).get();
            doctor.setSpecialization(specialization);
            
            authService.registerDoctor(doctor);
            return "login";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("specializations", specializationService.getAllSpecializations());
            return "register";
        }
    }
}