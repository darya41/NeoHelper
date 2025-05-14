package com.yarmak.neoHelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yarmak.neoHelper.model.doctor.Doctor;
import com.yarmak.neoHelper.model.doctor.Specialization;
import com.yarmak.neoHelper.service.DoctorService;
import com.yarmak.neoHelper.service.MotherService;
import com.yarmak.neoHelper.service.SpecializationService;
import com.yarmak.neoHelper.util.CustomPasswordEncoder;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

	private final MotherService motherService;
	private final SpecializationService specializationService;
	private final DoctorService doctorService;
	private final CustomPasswordEncoder passwordEncoder;

	@GetMapping("/profile")
	public String showDoctorProfile(HttpSession session, Model model) {
		Doctor doctor = (Doctor) session.getAttribute("currentDoctor");

		if (doctor == null) {
			return "login";
		}

		model.addAttribute("sessionDoctor", doctor);
		model.addAttribute("doctor", doctor);

		return "profile";
	}

	@GetMapping("/back")
	public String backToMainPage(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("patients", motherService.getAllMothers());
		return "main";
	}

	@GetMapping("/edit")
	public String goToEditProfile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Doctor doctor = (Doctor) session.getAttribute("currentDoctor");

		if (doctor == null)
			return "login";

		model.addAttribute("sessionDoctor", doctor);
		model.addAttribute("doctor", doctor);
		model.addAttribute("specializations", specializationService.getAllSpecializations());
		return "editProfile";
	}

	@PostMapping("/update")
	public String updateProfile(@ModelAttribute("doctor") Doctor updatedDoctor,
			@RequestParam(required = false) String currentPassword, @RequestParam(required = false) String newPassword,
			HttpSession session, RedirectAttributes redirectAttributes, Model model) {

		try {
			Doctor currentDoctor = (Doctor) session.getAttribute("currentDoctor");
			if (currentDoctor == null) {
				return "redirect:/login";
			}

			updatedDoctor.setDoctorId(currentDoctor.getDoctorId());

			if ((currentPassword != null && !currentPassword.isEmpty())
					|| (newPassword != null && !newPassword.isEmpty())) {

				if (currentPassword == null || currentPassword.isEmpty()) {
					redirectAttributes.addFlashAttribute("currentPasswordError", "Введите текущий пароль");
				} else if (!passwordEncoder.matches(currentPassword, currentDoctor.getPassword())) {
					redirectAttributes.addFlashAttribute("currentPasswordError", "Текущий пароль неверен");
				}

				if (newPassword == null || newPassword.isEmpty()) {
					redirectAttributes.addFlashAttribute("newPasswordError", "Введите новый пароль");
				}

				if (redirectAttributes.getFlashAttributes().containsKey("currentPasswordError")
						|| redirectAttributes.getFlashAttributes().containsKey("newPasswordError")) {
					redirectAttributes.addFlashAttribute("doctor", updatedDoctor);
					return "redirect:/doctor/edit";
				}

				updatedDoctor.setPassword(passwordEncoder.encode(newPassword));
			} else {
				updatedDoctor.setPassword(currentDoctor.getPassword());
			}

			if (updatedDoctor.getSpecialization() != null && updatedDoctor.getSpecialization().getId() != 0) {
				Specialization spec = specializationService.findById(updatedDoctor.getSpecialization().getId())
						.orElseThrow(() -> new RuntimeException("Specialization not found"));
				updatedDoctor.setSpecialization(spec);
			} else {
				updatedDoctor.setSpecialization(currentDoctor.getSpecialization());
			}

			Doctor savedDoctor = doctorService.update(updatedDoctor);
			session.setAttribute("currentDoctor", savedDoctor);

			redirectAttributes.addFlashAttribute("success", "Профиль успешно обновлен");
			return "redirect:/doctor/profile";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении профиля: " + e.getMessage());
			return "redirect:/doctor/edit";
		}
	}

}
