package com.yarmak.neoHelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yarmak.neoHelper.dao.PatientRepository;
import com.yarmak.neoHelper.model.patient.Patient;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

	private PatientRepository patientRepository;

	@Autowired
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional
	public List<Patient> findByMotherId(int id) {
		return patientRepository.findByMotherId(id);
	}

}
