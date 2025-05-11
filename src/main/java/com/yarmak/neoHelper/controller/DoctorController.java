package com.yarmak.neoHelper.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yarmak.neoHelper.model.doctor.Doctor;
import com.yarmak.neoHelper.model.doctor.Specialization;
import com.yarmak.neoHelper.model.patient.Mother;
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
			return "redirect:/login";
		}

		model.addAttribute("sessionDoctor", doctor);
		model.addAttribute("doctor", doctor);

		return "profile";
	}

	@GetMapping("/back")
	public String backToMainPage(Model model, RedirectAttributes redirectAttributes) {
		List<Mother> mothers = motherService.getAllMothers();
		model.addAttribute("patients", mothers);
		return "main";
	}

	@GetMapping("/edit")
	public String goToEditProfile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Doctor doctor = (Doctor) session.getAttribute("currentDoctor");

		if (doctor == null) {
			return "redirect:/login";
		}

		model.addAttribute("sessionDoctor", doctor);
		model.addAttribute("doctor", doctor);

		List<Specialization> list = specializationService.getAllSpecializations();
		model.addAttribute("specializations", list);
		return "editProfile";
	}

	@PostMapping("/update")
	public String updateProfile(Model model, @ModelAttribute("doctor") Doctor updatedDoctor,
			@RequestParam(required = false) String currentPassword, @RequestParam(required = false) String newPassword,
			HttpSession session, RedirectAttributes redirectAttributes) {

		try {
			Doctor currentDoctor = (Doctor) session.getAttribute("currentDoctor");
			if (currentDoctor == null) {
				return "login";
			}

			updatedDoctor.setDoctorId(currentDoctor.getDoctorId());

			if (currentPassword != null && !currentPassword.isEmpty() && newPassword != null
					&& !newPassword.isEmpty()) {

				if (!passwordEncoder.matches(currentPassword, currentDoctor.getPassword())) {
					redirectAttributes.addFlashAttribute("error", "Текущий пароль неверен");
					return "redirect:/doctor/profile/edit";
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

			model.addAttribute("sessionDoctor", updatedDoctor);
			model.addAttribute("doctor", updatedDoctor);

			return "profile";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении профиля: " + e.getMessage());
			return "editProfile";
		}
	}

}
