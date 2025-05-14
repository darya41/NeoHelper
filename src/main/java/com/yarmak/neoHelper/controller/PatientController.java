package com.yarmak.neoHelper.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yarmak.neoHelper.model.doctor.Doctor;
import com.yarmak.neoHelper.model.exam.MedParamInExam;
import com.yarmak.neoHelper.model.exam.PatientsExam;
import com.yarmak.neoHelper.model.patient.Mother;
import com.yarmak.neoHelper.model.patient.Patient;
import com.yarmak.neoHelper.service.MedParamInExamService;
import com.yarmak.neoHelper.service.MedParamInPatientExamService;
import com.yarmak.neoHelper.service.MotherService;
import com.yarmak.neoHelper.service.PatientService;
import com.yarmak.neoHelper.service.PatientsExamService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

	private final MedParamInExamService service;
	private final MotherService motherService;
	private final PatientService patientService;
	private final PatientsExamService patientsExamService;
	private final MedParamInPatientExamService medParamInPatientExamService;

	@GetMapping("/addForm")
	public String showNewbornForm(@RequestParam int motherId, Model model) {
		List<MedParamInExam> params = service.getParamsByExam(1);
		model.addAttribute("params", params);
		model.addAttribute("motherId", motherId);
		model.addAttribute("newborn", new Patient());
		return "addPatient";
	}

	@PostMapping("/save")
	@Transactional
	public String saveNewborn(HttpSession session, 
			@RequestParam int motherId, 
			@ModelAttribute Patient patient,
            @RequestParam List<Long> paramIds,
            @RequestParam List<String> values,
            @RequestParam String dateOfBirthString,
            Model model) {
	
		Optional<Mother> mother = motherService.getById(motherId);
		motherService.editWithNewBaby(mother);
	    patient.setMother(mother.get());
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	    LocalDateTime dateOfBirth = LocalDateTime.parse(dateOfBirthString, formatter);
	    
	    patient.setDateOfBirth(dateOfBirth);
	    
	    patientService.save(patient);
	    
	    
	    Doctor doctor = (Doctor) session.getAttribute("currentDoctor");
	    PatientsExam patientExam = new PatientsExam();
	    
	    patientExam.setDoctor(doctor);
	    patientExam.setPatient(patient);
	    patientExam.setDateTime(LocalDateTime.now());
	    
	    patientsExamService.save(patientExam);
	    
	    medParamInPatientExamService.saveParamsForPatient(paramIds, values, patientExam);
	    
	    model.addAttribute("mother",motherService.getById(motherId).get());
		model.addAttribute("patients", patientService.findByMotherId(motherId));
		
		return "patientMother";
	}
	
	

}
