package com.yarmak.neoHelper.controller;

import java.time.LocalDate;

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
	public String addMother( @ModelAttribute("mother") Mother mother, 
	                       BindingResult result,
	                       RedirectAttributes redirectAttributes) {

		 if (mother.getDateOfBirth() != null) {
		        LocalDate currentDate = LocalDate.now();
		        
		        if (mother.getDateOfBirth().isAfter(currentDate)) {
		            result.rejectValue("dateOfBirth", "error.mother", "Дата рождения не может быть в будущем");
		        }
		        
		        else if (mother.getDateOfBirth().isAfter(currentDate.minusYears(14))) {
		            result.rejectValue("dateOfBirth", "error.mother", "Роженица должна быть не младше 14 лет");
		        }
		    }
	    if (result.hasErrors()) {
	        logger.warn("Ошибки валидации при добавлении роженицы: {}", result.getAllErrors());
	        return "addMother";
	    }

	    try {
	        Address savedAddress = addressService.save(mother.getAddress());
	        mother.setAddress(savedAddress);

	        motherService.save(mother);
	        redirectAttributes.addFlashAttribute("success", "Роженица успешно добавлена");
	        return "redirect:/mothers";

	    } catch (Exception e) {
	        logger.error("Ошибка при добавлении роженицы: " + e.getMessage(), e);
	        redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении роженицы: " + e.getMessage());
	        return "addMother";
	    }
	}

	@GetMapping("/details/{id}")
	public String getPatientDetails(@PathVariable int id, Model model) {

		model.addAttribute("mother",motherService.getById(id).get());
		model.addAttribute("patients", patientService.findByMotherId(id));
		
		return "patientMother";
	}

}
