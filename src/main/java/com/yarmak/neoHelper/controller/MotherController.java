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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yarmak.neoHelper.model.patient.Address;
import com.yarmak.neoHelper.model.patient.Mother;
import com.yarmak.neoHelper.model.patient.Patient;
import com.yarmak.neoHelper.service.AddressService;
import com.yarmak.neoHelper.service.MotherService;
import com.yarmak.neoHelper.service.PatientService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mother")
public class MotherController {

	private final MotherService motherService;
	private final AddressService addressService;
	private final PatientService patientService;
	private static final Logger logger = LoggerFactory.getLogger(MotherController.class);

	@GetMapping("/addPage")
	public String showAddForm(Model model) {
		Mother mother = new Mother();
		mother.setAddress(new Address());
		model.addAttribute("mother", mother);
		return "addMother";
	}

	@PostMapping("/add")
	public String addMother(Model model, @ModelAttribute("mother") Mother mother, BindingResult result,
			RedirectAttributes redirectAttributes) {

		logger.debug("Начало обработки запроса на добавление роженицы: {}", mother);

		if (result.hasErrors()) {
			logger.warn("Ошибки валидации при добавлении роженицы: {}", result.getAllErrors());
			model.addAttribute("mother", mother);
			return "addMother";
		}

		try {
			logger.info("Сохранение адреса роженицы: {}", mother.getAddress());
			Address savedAddress = addressService.save(mother.getAddress());
			mother.setAddress(savedAddress);

			logger.debug("Сохранение данных роженицы в БД");
			Mother savedMother = motherService.save(mother);

			String successMessage = "Роженица " + savedMother.getLastName() + " успешно добавлена";
			logger.info(successMessage);

			redirectAttributes.addFlashAttribute("success", successMessage);

			logger.debug("Получение списка всех рожениц для отображения");
			List<Mother> mothers = motherService.getAllMothers();
			model.addAttribute("patients", mothers);

			return "main";

		} catch (Exception e) {
			String errorMessage = "Ошибка при добавлении роженицы: " + e.getMessage();
			logger.error(errorMessage, e);
			model.addAttribute("mother", mother);

			redirectAttributes.addFlashAttribute("error", errorMessage);
			return "addMother";
		}
	}

	@GetMapping("/details/{id}")
	public String getPatientDetails(@PathVariable int id, Model model) {
		
		Optional<Mother> motherOptional = motherService.getById(id);
		Mother mother = motherOptional .get();

		model.addAttribute("mother", mother);

		List<Patient> patients = patientService.findByMotherId(id);
		model.addAttribute("patients", patients);
		
		return "patientMother";
	}

}
