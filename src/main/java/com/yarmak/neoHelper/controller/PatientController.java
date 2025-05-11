package com.yarmak.neoHelper.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yarmak.neoHelper.model.exam.MedParamInExam;
import com.yarmak.neoHelper.model.patient.Patient;
import com.yarmak.neoHelper.service.MedParamInExamService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

	private final MedParamInExamService service;

	@GetMapping("/addForm")
	public String showNewbornForm(@RequestParam int motherId, Model model) {
		List<MedParamInExam> params = service.getParamsByExam(1);
		model.addAttribute("params", params);
		model.addAttribute("motherId", motherId);
		model.addAttribute("newborn", new Patient());
		return "addPatient";
	}

	@PostMapping("/save")
	public String saveNewborn(@RequestParam int motherId, @ModelAttribute Patient patient) {
	
		return "";
	}

}
